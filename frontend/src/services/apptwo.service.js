import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://40.121.137.58:31000/';

class AppTwoService {
  getAppTwoGreetings() {
    return axios.get(API_URL + 'hello', { headers: authHeader() });
  }
}

export default new AppTwoService();