<template>
  <div class="enrollments-view container mt-4">
    <h1>Course Enrollments</h1>

    <!-- 🔔 Error Message -->
    <div
      v-if="errorMessage"
      class="alert alert-danger alert-dismissible fade show"
      role="alert"
    >
      {{ errorMessage }}
      <button type="button" class="btn-close" @click="errorMessage = null"></button>
    </div>

    <!-- Enrollment Form -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title">Enroll a Student</h5>
        <form @submit.prevent="enrollStudent" class="row g-2">
          <div class="col-md-3">
            <label class="form-label">Student</label>
            <select v-model="form.studentId" class="form-select" required>
              <option value="" disabled>Select Student</option>
              <option v-for="s in students" :key="s.id" :value="s.id">
                {{ s.firstName }} {{ s.lastName }}
              </option>
            </select>
          </div>

          <div class="col-md-3">
            <label class="form-label">Course</label>
            <select v-model="form.courseId" class="form-select" required>
              <option value="" disabled>Select Course</option>
              <option v-for="c in courses" :key="c.id" :value="c.id">
                {{ c.courseCode }} - {{ c.name }}
              </option>
            </select>
          </div>

          <div class="col-md-2 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">Enroll</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Enrollments Table -->
    <h5>All Enrollments</h5>
    <table class="table table-bordered">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Student</th>
          <th>Course</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Grade</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="e in enrollments" :key="e.id">
          <td>{{ e.id }}</td>
          <td>{{ studentName(e.studentId) }}</td>
          <td>{{ courseName(e.courseId) }}</td>
          <td>{{ formatDate(e.startDate) }}</td>
          <td>{{ formatDate(e.endDate) }}</td>
          <td>
            <div v-if="editEnrollmentId === e.id">
              <input
                v-model="editGrade"
                type="text"
                class="form-control form-control-sm"
                style="width:80px; display:inline;"
              />
            </div>
            <div v-else>
              {{ e.grade || "-" }}
            </div>
          </td>
          <td>{{ e.status }}</td>
          <td>
            <button
              v-if="editEnrollmentId !== e.id"
              class="btn btn-warning btn-sm me-1"
              @click="startEdit(e)"
            >
              Edit
            </button>
            <button
              v-else
              class="btn btn-success btn-sm me-1"
              @click="saveGrade(e)"
            >
              Save
            </button>
            <button
              v-if="editEnrollmentId === e.id"
              class="btn btn-secondary btn-sm me-1"
              @click="cancelEdit"
            >
              Cancel
            </button>
            <button
              class="btn btn-danger btn-sm"
              @click="deleteEnrollment(e.id)"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getStudents } from "@/api/students";
import { getCourses } from "@/api/courses";
import {
  getEnrollments,
  createEnrollment,
  deleteEnrollment,
  updateEnrollment,
} from "@/api/enrollments";

export default {
  name: "EnrollmentsView",
  data() {
    return {
      students: [],
      courses: [],
      enrollments: [],
      form: {
        studentId: "",
        courseId: "",
      },
      editEnrollmentId: null,
      editGrade: "",
      errorMessage: null, // ✅ eklendi
    };
  },
  methods: {
    async loadData() {
      try {
        const [stuRes, couRes, enrRes] = await Promise.all([
          getStudents(),
          getCourses(),
          getEnrollments(),
        ]);
        this.students = stuRes.data;
        this.courses = couRes.data;
        this.enrollments = enrRes.data;
      } catch (err) {
        console.error("Failed to load data:", err);
        this.errorMessage = "Failed to load enrollments. Please try again.";
      }
    },
    async enrollStudent() {
      try {
        await createEnrollment(this.form);
        this.loadData();
        this.form = { studentId: "", courseId: "" };
        this.errorMessage = null;
      } catch (err) {
        console.error("Enrollment failed:", err);
        this.errorMessage = err.response?.data?.message || "Enrollment failed.";
      }
    },
    async deleteEnrollment(id) {
      if (confirm("Are you sure?")) {
        try {
          await deleteEnrollment(id);
          this.loadData();
        } catch (err) {
          console.error("Failed to delete enrollment:", err);
          this.errorMessage = "Failed to delete enrollment.";
        }
      }
    },
    startEdit(enrollment) {
      this.editEnrollmentId = enrollment.id;
      this.editGrade = enrollment.grade || "";
    },
    async saveGrade(enrollment) {
      try {
        const payload = { ...enrollment, grade: this.editGrade };
        await updateEnrollment(enrollment.id, payload);
        enrollment.grade = this.editGrade;
        this.editEnrollmentId = null;
        this.editGrade = "";
        this.errorMessage = null;
      } catch (err) {
        console.error("Failed to update grade:", err);
        this.errorMessage = "Failed to update grade.";
      }
    },
    cancelEdit() {
      this.editEnrollmentId = null;
      this.editGrade = "";
    },
    studentName(id) {
      const s = this.students.find((st) => st.id === id);
      return s ? `${s.firstName} ${s.lastName}` : id;
    },
    courseName(id) {
      const c = this.courses.find((co) => co.id === id);
      return c ? `${c.courseCode} - ${c.name}` : id;
    },
    formatDate(date) {
      if (!date) return "-";
      return new Date(date).toLocaleDateString();
    },
  },
  mounted() {
    this.loadData();
  },
};
</script>

