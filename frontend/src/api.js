import axios from 'axios';
import store from './store';

const api = axios.create({
  baseURL: store.state.apiBase,
  headers: { 'Content-Type': 'application/json' }
});

export default api;
