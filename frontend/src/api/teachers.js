import api from "./axios";

const TEACHERS_URL = "/teachers";

export const getAvgGrades = () => api.get(`${TEACHERS_URL}/course-avg-grades`);

export const getTeachers = () => api.get(TEACHERS_URL);
export const getTeacher = (id) => api.get(`${TEACHERS_URL}/${id}`);
export const createTeacher = (data) => api.post(TEACHERS_URL, data);
export const updateTeacher = (id, data) => api.put(`${TEACHERS_URL}/${id}`, data);
export const deleteTeacher = (id) => api.delete(`${TEACHERS_URL}/${id}`);

