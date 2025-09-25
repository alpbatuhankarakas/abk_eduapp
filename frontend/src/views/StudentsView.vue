<template>
  <div class="students-view container mt-4">
    <h1>Students</h1>

    <!-- Add Student -->
    <button class="btn btn-success mb-3" @click="openCreateModal">+ Add Student</button>

    <!-- Students Table -->
    <table class="table table-bordered">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Student Number</th>
          <th>Name</th>
          <th>Email</th>
          <th>Status</th>
          <th>Average Grade</th> <!-- 🆕 -->
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="s in students" :key="s.id">
          <td>{{ s.id }}</td>
          <td>{{ s.studentNumber }}</td>
          <td>{{ s.firstName }} {{ s.lastName }}</td>
          <td>{{ s.email }}</td>
          <td>{{ s.status }}</td>
          <td>
            {{ avgGradeForStudent(s.id) }}
          </td>
          <td>
            <button class="btn btn-info btn-sm me-1" @click="viewDetails(s.id)">Details</button>
            <button class="btn btn-warning btn-sm me-1" @click="editStudent(s)">Edit</button>
            <button class="btn btn-danger btn-sm" @click="deleteStudent(s.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Create / Edit Modal -->
    <div class="modal fade" id="studentModal" tabindex="-1" aria-hidden="true" ref="studentModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editMode ? 'Edit Student' : 'Create Student' }}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="saveStudent">
              <div class="row g-2">
                <div class="col-md-6">
                  <label class="form-label">Student Number</label>
                  <input v-model="form.studentNumber" class="form-control" required />
                </div>
                <div class="col-md-6">
                  <label class="form-label">First Name</label>
                  <input v-model="form.firstName" class="form-control" required />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Last Name</label>
                  <input v-model="form.lastName" class="form-control" required />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Email</label>
                  <input v-model="form.email" type="email" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Date of Birth</label>
                  <input v-model="form.dateOfBirth" type="date" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Gender</label>
                  <select v-model="form.gender" class="form-select">
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                    <option value="OTHER">Other</option>
                  </select>
                </div>
                <div class="col-md-6">
                  <label class="form-label">Phone</label>
                  <input v-model="form.phone" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Address</label>
                  <input v-model="form.address" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Enrollment Year</label>
                  <input v-model="form.enrollmentYear" type="number" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Department</label>
                  <input v-model="form.department" class="form-control" />
                </div>
                <div class="col-md-6">
                  <label class="form-label">Status</label>
                  <select v-model="form.status" class="form-select">
                    <option value="ACTIVE">Active</option>
                    <option value="GRADUATED">Graduated</option>
                    <option value="SUSPENDED">Suspended</option>
                    <option value="DROPPED">Dropped</option>
                  </select>
                </div>
              </div>

              <div class="modal-footer mt-3">
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Student Detail Modal -->
    <div v-if="showDetail" class="modal fade show d-block" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Student Details</h5>
            <button type="button" class="btn-close" @click="closeDetail"></button>
          </div>
          <div class="modal-body">
            <p><strong>ID:</strong> {{ selectedStudent?.id }}</p>
            <p><strong>Name:</strong> {{ selectedStudent?.firstName }} {{ selectedStudent?.lastName }}</p>
            <p><strong>Email:</strong> {{ selectedStudent?.email }}</p>

            <h5>Enrolled Courses</h5>
            <table class="table table-sm">
              <thead>
                <tr>
                  <th>Course ID</th>
                  <th>Course Name</th>
                  <th>Grade</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="en in selectedStudent?.enrollments || []" :key="en.id">
                  <td>{{ en.courseId }}</td>
                  <td>{{ courseName(en.courseId) }}</td>
                  <td>{{ en.grade }}</td>
                  <td>{{ en.status }}</td>
                </tr>
              </tbody>
            </table>

            <!-- 🆕 Backend’den gelen ortalama -->
            <p><strong>Average Grade:</strong> {{ avgGradeForStudent(selectedStudent?.id) }}</p>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="closeDetail">Close</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { getStudents, getStudent, createStudent, updateStudent, deleteStudent } from "@/api/students";
import { getEnrollments } from "@/api/enrollments";
import { getCourses } from "@/api/courses";
import { Modal } from "bootstrap";
import api from "@/api/axios"; // 🆕 axios instance

export default {
  name: "StudentsView",
  data() {
    return {
      students: [],
      editMode: false,
      form: {
        id: null,
        studentNumber: "",
        firstName: "",
        lastName: "",
        email: "",
        dateOfBirth: "",
        gender: "OTHER",
        phone: "",
        address: "",
        enrollmentYear: new Date().getFullYear(),
        department: "",
        status: "ACTIVE"
      },
      selectedStudent: null,
      showDetail: false,
      modal: null,
      courses: [],
      avgGrades: {} // 🆕 { studentId: avgGrade }
    };
  },
  methods: {
    async loadStudents() {
      try {
        const [stuRes, avgRes] = await Promise.all([
          getStudents(),
          api.get("/students/avg-grades") // 🆕 backend çağrısı
        ]);
        this.students = stuRes.data;
        this.avgGrades = {};
        avgRes.data.forEach(a => {
          this.avgGrades[a.studentId] = a.avgGrade;
        });
      } catch (err) {
        console.error("Failed to load students:", err);
      }
    },
    avgGradeForStudent(studentId) {
      const g = this.avgGrades[studentId];
      return g != null ? g.toFixed(2) : "-";
    },
    openCreateModal() {
      this.editMode = false;
      this.form = {
        studentNumber: "",
        firstName: "",
        lastName: "",
        email: "",
        dateOfBirth: "",
        gender: "OTHER",
        phone: "",
        address: "",
        enrollmentYear: new Date().getFullYear(),
        department: "",
        status: "ACTIVE"
      };
      const modalEl = this.$refs.studentModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    editStudent(student) {
      this.editMode = true;
      this.form = { ...student };
      const modalEl = this.$refs.studentModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    async saveStudent() {
      try {
        if (this.editMode) {
          await updateStudent(this.form.id, this.form);
        } else {
          const payload = { ...this.form };
          delete payload.id;
          await createStudent(payload);
        }
        this.modal.hide();
        this.loadStudents();
      } catch (err) {
        console.error("Failed to save student:", err);
        alert("Save failed: " + (err.response?.data?.message || err.message));
      }
    },
    async deleteStudent(id) {
      if (confirm("Are you sure?")) {
        try {
          await deleteStudent(id);
          this.loadStudents();
        } catch (err) {
          console.error("Failed to delete student:", err);
        }
      }
    },
    async viewDetails(id) {
      try {
        const [sRes, eRes, cRes] = await Promise.all([getStudent(id), getEnrollments(), getCourses()]);
        const student = sRes.data;
        const allEnrollments = eRes.data;
        student.enrollments = allEnrollments.filter(e => e.studentId === id);
        this.courses = cRes.data;
        this.selectedStudent = student;
        this.showDetail = true;
      } catch (err) {
        console.error("Failed to load student details:", err);
      }
    },
    courseName(courseId) {
      const c = this.courses.find(cc => cc.id === courseId);
      return c ? c.name : courseId;
    },
    closeDetail() {
      this.showDetail = false;
      this.selectedStudent = null;
    }
  },
  mounted() {
    this.loadStudents();
  }
};
</script>

