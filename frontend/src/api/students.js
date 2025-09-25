import api from "./axios";

const STUDENTS_URL = "/students";

export const getAvgGrades = () => api.get("/students/avg-grades");

export const getStudents = () => api.get(STUDENTS_URL);
export const getStudent = (id) => api.get(`${STUDENTS_URL}/${id}`);
export const createStudent = (data) => api.post(STUDENTS_URL, data);
export const updateStudent = (id, data) => api.put(`${STUDENTS_URL}/${id}`, data);
export const deleteStudent = (id) => api.delete(`${STUDENTS_URL}/${id}`);

