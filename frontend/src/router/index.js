import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import StudentsView from "../views/StudentsView.vue";
import TeachersView from "../views/TeachersView.vue";
import CoursesView from "../views/CoursesView.vue";
import EnrollmentsView from "../views/EnrollmentsView.vue";

const routes = [
  { path: "/", redirect: "/students" },
  { path: "/login", name: "login", component: LoginView },
  { path: "/students", name: "students", component: StudentsView, meta: { requiresAuth: true } },
  { path: "/teachers", name: "teachers", component: TeachersView, meta: { requiresAuth: true } },
  { path: "/courses", name: "courses", component: CoursesView, meta: { requiresAuth: true } }, 
  { path: "/enrollments", name: "enrollments", component: EnrollmentsView, meta: { requiresAuth: true } }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// 🔒 Navigation Guard
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  // login olmuşsa tekrar login sayfasına gitmesin
  if (to.path === "/login" && token) {
    return next("/students");
  }

  // login olmadan protected sayfaya gitmeye çalışırsa login'e yönlendir
  if (to.meta.requiresAuth && !token) {
    return next("/login");
  }

  next();
});

export default router;

