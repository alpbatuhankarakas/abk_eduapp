<template>
  <div class="teachers-view container mt-4">
    <h1>Teachers</h1>

    <!-- Add Teacher -->
    <button class="btn btn-success mb-3" @click="openCreateModal">+ Add Teacher</button>

    <!-- Teachers Table -->
    <table class="table table-bordered">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Teacher Number</th>
          <th>Name</th>
          <th>Email</th>
          <th>Department</th>
          <th>Title</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="t in teachers" :key="t.id">
          <td>{{ t.id }}</td>
          <td>{{ t.teacherNumber }}</td>
          <td>{{ t.firstName }} {{ t.lastName }}</td>
          <td>{{ t.email }}</td>
          <td>{{ t.department }}</td>
          <td>{{ t.title }}</td>
          <td>{{ t.status }}</td>
          <td>
            <button class="btn btn-info btn-sm me-1" @click="viewDetails(t.id)">Details</button>
            <button class="btn btn-warning btn-sm me-1" @click="editTeacher(t)">Edit</button>
            <button class="btn btn-danger btn-sm" @click="deleteTeacher(t.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Create / Edit Modal -->
    <div class="modal fade" id="teacherModal" tabindex="-1" aria-hidden="true" ref="teacherModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editMode ? 'Edit Teacher' : 'Create Teacher' }}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="saveTeacher">
              <div class="mb-3">
                <label class="form-label">Teacher Number</label>
                <input v-model="form.teacherNumber" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">First Name</label>
                <input v-model="form.firstName" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Last Name</label>
                <input v-model="form.lastName" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Email</label>
                <input v-model="form.email" type="email" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Phone</label>
                <input v-model="form.phone" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Department</label>
                <input v-model="form.department" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Title</label>
                <input v-model="form.title" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Status</label>
                <select v-model="form.status" class="form-select">
                  <option value="ACTIVE">Active</option>
                  <option value="INACTIVE">Inactive</option>
                  <option value="ON_LEAVE">On Leave</option>
                </select>
              </div>
              <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <!-- Teacher Details Modal -->
    <div v-if="showDetail" class="modal fade show d-block" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Teacher Details</h5>
            <button type="button" class="btn-close" @click="closeDetail"></button>
          </div>
          <div class="modal-body">
            <p><strong>ID:</strong> {{ selectedTeacher?.id }}</p>
            <p><strong>Name:</strong> {{ selectedTeacher?.firstName }} {{ selectedTeacher?.lastName }}</p>
            <p><strong>Email:</strong> {{ selectedTeacher?.email }}</p>
            <p><strong>Department:</strong> {{ selectedTeacher?.department }}</p>
            <p><strong>Title:</strong> {{ selectedTeacher?.title }}</p>
            <p><strong>Status:</strong> {{ selectedTeacher?.status }}</p>

            <h5>Courses Assigned</h5>
            <table class="table table-sm">
              <thead>
                <tr>
                  <th>Course ID</th>
                  <th>Course Name</th>
                  <th>Average Grade</th>
                  <th>Role</th>
                  <th>Assigned Date</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="a in selectedTeacher?.assignments || []" :key="a.id">
                  <td>{{ a.courseId }}</td>
                  <td>{{ courseName(a.courseId) }}</td>
                  <td>{{ avgGradeForCourse(a.courseId) }}</td>
                  <td>{{ a.role }}</td>
                  <td>{{ a.assignedDate }}</td>
                </tr>
              </tbody>
            </table>
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
import { getTeachers, getTeacher, createTeacher, updateTeacher, deleteTeacher, getAvgGrades } from "@/api/teachers";
import { getAssignments } from "@/api/assignments";
import { Modal } from "bootstrap";

export default {
  name: "TeachersView",
  data() {
    return {
      teachers: [],
      avgGrades: [],
      editMode: false,
      form: {
        id: null,
        teacherNumber: "",
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        department: "",
        title: "",
        status: "ACTIVE"
      },
      selectedTeacher: null,
      showDetail: false,
      modal: null
    };
  },
  methods: {
    async loadTeachers() {
      try {
        const [teacherRes, avgRes] = await Promise.all([
          getTeachers(),
          getAvgGrades()
        ]);
        this.teachers = teacherRes.data;
        this.avgGrades = avgRes.data;
      } catch (err) {
        console.error("Failed to load teachers:", err);
      }
    },
    openCreateModal() {
      this.editMode = false;
      this.form = {
        teacherNumber: "",
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        department: "",
        title: "",
        status: "ACTIVE"
      };
      const modalEl = this.$refs.teacherModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    editTeacher(teacher) {
      this.editMode = true;
      this.form = {
        id: teacher.id,
        teacherNumber: teacher.teacherNumber,
        firstName: teacher.firstName,
        lastName: teacher.lastName,
        email: teacher.email,
        phone: teacher.phone,
        department: teacher.department,
        title: teacher.title,
        status: teacher.status
      };
      const modalEl = this.$refs.teacherModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    async saveTeacher() {
      try {
        if (this.editMode) {
          await updateTeacher(this.form.id, this.form);
        } else {
          const payload = { ...this.form };
          delete payload.id;
          await createTeacher(payload);
        }
        this.modal.hide();
        this.loadTeachers();
      } catch (err) {
        console.error("Failed to save teacher:", err);
        alert("Save failed: " + (err.response?.data?.message || err.message));
      }
    },
    async deleteTeacher(id) {
      if (confirm("Are you sure?")) {
        try {
          await deleteTeacher(id);
          this.loadTeachers();
        } catch (err) {
          console.error("Failed to delete teacher:", err);
        }
      }
    },
    async viewDetails(id) {
      try {
        const res = await getTeacher(id);
        const teacher = res.data;
        const assRes = await getAssignments();
        const allAssignments = assRes.data;
        teacher.assignments = allAssignments.filter(a => a.teacherId === id);
        this.selectedTeacher = teacher;
        this.showDetail = true;
      } catch (err) {
        console.error("Failed to load teacher details:", err);
      }
    },
    courseName(courseId) {
      const match = this.avgGrades.find(ag => ag.courseId === courseId);
      return match ? match.courseName : courseId;
    },
    avgGradeForCourse(courseId) {
      const match = this.avgGrades.find(ag => ag.courseId === courseId);
      return match ? match.avgGrade : "-";
    },
    closeDetail() {
      this.showDetail = false;
      this.selectedTeacher = null;
    }
  },
  mounted() {
    this.loadTeachers();
  }
};
</script>

