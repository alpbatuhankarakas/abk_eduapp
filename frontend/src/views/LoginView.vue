<template>
  <!-- Show login form if no token exists -->
  <div class="container mt-5" v-if="!isLoggedIn">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input v-model="username" id="username" type="text" class="form-control" required />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input v-model="password" id="password" type="password" class="form-control" required />
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>

  <!-- If already logged in, show message -->
  <div v-else class="container mt-5">
    <h4>You are already logged in ✅</h4>
    <button class="btn btn-danger mt-3" @click="logout">Logout</button>
  </div>
</template>

<script>
import axios from "axios";
import { API_BASE } from "../config";

export default {
  data() {
    return {
      username: "",
      password: ""
    };
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem("token");
    }
  },
  methods: {
    async login() {
      try {
        const res = await axios.post(`${API_BASE}/auth/login`, {
          username: this.username,
          password: this.password
        });

        // handle token from backend response
        const token = res.data.token || res.data.accessToken || Object.values(res.data)[0];

        if (!token) {
          alert("Login response does not include a token!");
          return;
        }

        // save token in localStorage
        localStorage.setItem("token", token);

        // trigger global state update if needed
        window.dispatchEvent(new Event("storage"));

        // redirect to students page
        this.$router.push("/students");
      } catch (err) {
        console.error("Login failed", err);
        alert("Invalid username or password");
      }
    },
    logout() {
      // remove token from localStorage
      localStorage.removeItem("token");

      // redirect back to login page
      this.$router.push("/login");
    }
  }
};
</script>

