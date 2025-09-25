import api from "./axios";

const COURSES_URL = "/courses";

export const getCourseAvgGrades = () => api.get(`${COURSES_URL}/avg-grades`);

export const getCourses = () => api.get(COURSES_URL);
export const getCourse = (id) => api.get(`${COURSES_URL}/${id}`);
export const createCourse = (data) => api.post(COURSES_URL, data);
export const updateCourse = (id, data) => api.put(`${COURSES_URL}/${id}`, data);
export const deleteCourse = (id) => api.delete(`${COURSES_URL}/${id}`);

