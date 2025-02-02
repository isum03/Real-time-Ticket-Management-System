import axios from 'axios';

const instance = axios.create({
  baseURL: '/api', // Proxy will handle the base URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export default instance;