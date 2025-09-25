import api from "./axios";

const ENROLLMENTS_URL = "/enrollments";

export const getEnrollments = () => api.get(ENROLLMENTS_URL);
export const getEnrollment = (id) => api.get(`${ENROLLMENTS_URL}/${id}`);
export const createEnrollment = (data) => api.post(ENROLLMENTS_URL, data);
export const updateEnrollment = (id, data) => api.put(`${ENROLLMENTS_URL}/${id}`, data);
export const deleteEnrollment = (id) => api.delete(`${ENROLLMENTS_URL}/${id}`);

