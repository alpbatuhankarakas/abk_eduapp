import api from "./axios";

const ASSIGNMENTS_URL = "/assignments";

export const getAssignments = () => api.get(ASSIGNMENTS_URL);
export const getAssignment = (id) => api.get(`${ASSIGNMENTS_URL}/${id}`);
export const createAssignment = (data) => api.post(ASSIGNMENTS_URL, data);
export const updateAssignment = (id, data) => api.put(`${ASSIGNMENTS_URL}/${id}`, data);
export const deleteAssignment = (id) => api.delete(`${ASSIGNMENTS_URL}/${id}`);

