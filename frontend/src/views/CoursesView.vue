<template>
  <div class="courses-view container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1>Courses</h1>

      <!-- 🔍 Search Bar (sağ üstte) -->
      <div style="width: 250px;">
        <input
          v-model="searchTerm"
          type="text"
          class="form-control"
          placeholder="Search by course name..."
        />
      </div>
    </div>

    <!-- Add Course -->
    <button class="btn btn-success mb-3" @click="openCreateModal">+ Add Course</button>

    <!-- Courses Table -->
    <table class="table table-bordered">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Course Code</th>
          <th>Name</th>
          <th>Credit</th>
          <th>Department</th>
          <th>Start Date</th> <!-- ✅ yeni -->
          <th>End Date</th>   <!-- ✅ yeni -->
          <th>Avg Grade</th>
          <th>Failures</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="c in filteredCourses" :key="c.id">
          <td>{{ c.id }}</td>
          <td>{{ c.courseCode }}</td>
          <td>{{ c.name }}</td>
          <td>{{ c.credit }}</td>
          <td>{{ c.department }}</td>
          <td>{{ formatDate(c.startDate) }}</td> <!-- ✅ -->
          <td>{{ formatDate(c.endDate) }}</td>   <!-- ✅ -->
          <td>{{ c.avgGrade ?? '-' }}</td>
          <td>{{ c.failureCount ?? 0 }}</td>
          <td>
            <button class="btn btn-info btn-sm me-1" @click="viewDetails(c.id)">Details</button>
            <button class="btn btn-warning btn-sm me-1" @click="editCourse(c)">Edit</button>
            <button class="btn btn-danger btn-sm" @click="deleteCourse(c.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Create / Edit Modal -->
    <div class="modal fade" id="courseModal" tabindex="-1" aria-hidden="true" ref="courseModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ editMode ? 'Edit Course' : 'Create Course' }}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="saveCourse">
              <div class="mb-3">
                <label class="form-label">Course Code</label>
                <input v-model="form.courseCode" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Name</label>
                <input v-model="form.name" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Credit</label>
                <input v-model="form.credit" type="number" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Department</label>
                <input v-model="form.department" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Start Date</label> <!-- ✅ yeni -->
                <input v-model="form.startDate" type="date" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">End Date</label> <!-- ✅ yeni -->
                <input v-model="form.endDate" type="date" class="form-control" />
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

    <!-- Course Details Modal -->
    <div v-if="showDetail" class="modal fade show d-block" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Course Details</h5>
            <button type="button" class="btn-close" @click="closeDetail"></button>
          </div>
          <div class="modal-body">
            <p><strong>ID:</strong> {{ selectedCourse?.id }}</p>
            <p><strong>Course Code:</strong> {{ selectedCourse?.courseCode }}</p>
            <p><strong>Name:</strong> {{ selectedCourse?.name }}</p>
            <p><strong>Credit:</strong> {{ selectedCourse?.credit }}</p>
            <p><strong>Department:</strong> {{ selectedCourse?.department }}</p>
            <p><strong>Start Date:</strong> {{ formatDate(selectedCourse?.startDate) }}</p> <!-- ✅ -->
            <p><strong>End Date:</strong> {{ formatDate(selectedCourse?.endDate) }}</p>     <!-- ✅ -->

            <!-- Enrollments -->
            <h5 class="mt-4">Enrollments</h5>
            <table class="table table-sm">
              <thead>
                <tr>
                  <th>Student ID</th>
                  <th>Grade</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="e in selectedCourse?.enrollments || []" :key="e.id">
                  <td>{{ e.studentId }}</td>
                  <td>{{ e.grade }}</td>
                  <td>{{ e.status }}</td>
                </tr>
              </tbody>
            </table>

            <!-- Assigned Teachers -->
            <h5 class="mt-4">Assigned Teachers</h5>
            <div class="mb-2 d-flex">
              <select v-model="assignment.teacherId" class="form-select me-2">
                <option value="" disabled>Select Teacher</option>
                <option v-for="t in teachers" :key="t.id" :value="t.id">
                  {{ t.firstName }} {{ t.lastName }}
                </option>
              </select>
              <input v-model="assignment.role" class="form-control me-2" placeholder="Role (e.g. Lecturer)" />
              <button class="btn btn-primary" @click="addAssignment">Assign</button>
            </div>

            <table class="table table-sm">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Teacher</th>
                  <th>Role</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="a in selectedCourse?.assignments || []" :key="a.id">
                  <td>{{ a.id }}</td>
                  <td>{{ teacherName(a.teacherId) }}</td>
                  <td>{{ a.role }}</td>
                  <td>
                    <button class="btn btn-danger btn-sm" @click="removeAssignment(a.id)">Delete</button>
                  </td>
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
import { getCourses, getCourse, createCourse, updateCourse, deleteCourse, getCourseAvgGrades } from "@/api/courses";
import { getEnrollments } from "@/api/enrollments";
import { getTeachers } from "@/api/teachers";
import { getAssignments, createAssignment, deleteAssignment } from "@/api/assignments";
import { Modal } from "bootstrap";

export default {
  name: "CoursesView",
  data() {
    return {
      courses: [],
      avgGrades: [],
      searchTerm: "",
      editMode: false,
      form: { id: null, courseCode: "", name: "", credit: 0, department: "", startDate: "", endDate: "" }, // ✅
      selectedCourse: null,
      showDetail: false,
      modal: null,
      teachers: [],
      assignment: { teacherId: "", role: "" }
    };
  },
  computed: {
    filteredCourses() {
      if (!this.searchTerm) return this.courses;
      return this.courses.filter(c =>
        c.name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
  },
  methods: {
    async loadCourses() {
      try {
        const [res, avgRes] = await Promise.all([
          getCourses(),
          getCourseAvgGrades()
        ]);
        const baseCourses = res.data;
        const avgData = avgRes.data;

        this.courses = baseCourses.map(c => {
          const avg = avgData.find(a => a.courseId === c.id);
          return {
            ...c,
            avgGrade: avg ? avg.avgGrade : null,
            failureCount: avg ? avg.failureCount : 0
          };
        });
      } catch (err) {
        console.error("Failed to load courses:", err);
      }
    },
    openCreateModal() {
      this.editMode = false;
      this.form = { courseCode: "", name: "", credit: 0, department: "", startDate: "", endDate: "" }; // ✅
      const modalEl = this.$refs.courseModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    editCourse(course) {
      this.editMode = true;
      this.form = { ...course };
      const modalEl = this.$refs.courseModal;
      this.modal = new Modal(modalEl);
      this.modal.show();
    },
    async saveCourse() {
      try {
        if (this.editMode) {
          await updateCourse(this.form.id, this.form);
        } else {
          const payload = { ...this.form };
          delete payload.id;
          await createCourse(payload);
        }
        this.modal.hide();
        this.loadCourses();
      } catch (err) {
        console.error("Failed to save course:", err);
        alert("Save failed: " + (err.response?.data?.message || err.message));
      }
    },
    async deleteCourse(id) {
      if (confirm("Are you sure?")) {
        try {
          await deleteCourse(id);
          this.loadCourses();
        } catch (err) {
          console.error("Failed to delete course:", err);
        }
      }
    },
    async viewDetails(id) {
      try {
        const res = await getCourse(id);
        const course = res.data;

        const enrRes = await getEnrollments();
        course.enrollments = enrRes.data.filter(e => e.courseId === id);

        const asgRes = await getAssignments();
        course.assignments = asgRes.data.filter(a => a.courseId === id);

        const tRes = await getTeachers();
        this.teachers = tRes.data;

        this.selectedCourse = course;
        this.showDetail = true;
      } catch (err) {
        console.error("Failed to load course details:", err);
      }
    },
    async addAssignment() {
      if (!this.assignment.teacherId || !this.assignment.role) {
        alert("Please select a teacher and enter role");
        return;
      }
      try {
        await createAssignment({
          teacherId: this.assignment.teacherId,
          courseId: this.selectedCourse.id,
          role: this.assignment.role
        });
        this.assignment = { teacherId: "", role: "" };
        this.viewDetails(this.selectedCourse.id);
      } catch (err) {
        console.error("Failed to add assignment:", err);
      }
    },
    async removeAssignment(id) {
      try {
        await deleteAssignment(id);
        this.viewDetails(this.selectedCourse.id);
      } catch (err) {
        console.error("Failed to remove assignment:", err);
      }
    },
    teacherName(id) {
      const t = this.teachers.find(tt => tt.id === id);
      return t ? `${t.firstName} ${t.lastName}` : id;
    },
    closeDetail() {
      this.showDetail = false;
      this.selectedCourse = null;
    },
    formatDate(date) { // ✅ date formatter
      if (!date) return "-";
      return new Date(date).toLocaleDateString();
    }
  },
  mounted() {
    this.loadCourses();
  }
};
</script>

