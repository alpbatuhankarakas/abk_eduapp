<template>
  <div>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">EduApp</a>

        <!-- Hamburger Button (mobile) -->
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Collapsible menu -->
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto" v-if="loggedIn">
            <li class="nav-item">
              <router-link to="/students" class="nav-link">Students</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/teachers" class="nav-link">Teachers</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/courses" class="nav-link">Courses</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/enrollments" class="nav-link">Enrollments</router-link>
            </li>
          </ul>

          <ul class="navbar-nav ms-auto">
            <li class="nav-item" v-if="loggedIn">
              <button class="btn btn-danger btn-sm" @click="logout">Logout</button>
            </li>
            <li class="nav-item" v-else>
              <router-link to="/login" class="btn btn-primary btn-sm">Login</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Page Content -->
    <router-view />
  </div>
</template>

<script>
export default {
  data() {
    return {
      loggedIn: !!localStorage.getItem("token") // reactive state
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("token");
      this.loggedIn = false;
      this.$router.push("/login");
    }
  },
  mounted() {
    // başka tablardan login/logout olursa yakalamak için
    window.addEventListener("storage", () => {
      this.loggedIn = !!localStorage.getItem("token");
    });
  }
};
</script>

