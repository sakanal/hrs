import {Message} from "element-ui";
export default function ({ $axios, redirect }) {
  $axios.defaults.baseURL = 'http://localhost:9527/'
  $axios.interceptors.request.use(request => {
    return request
  }, error => {
    Message.error(error.message)
  })

  $axios.interceptors.response.use(response => {
    if(response.data.success === false){
      console.log("请求失败");
      return;
    }
    return response.data
  }, error => {
    if (error && error.response) {
      switch (error.response.status) {
        case 400: error.message = '请求错误(400)'; break;
        case 401: return history.push('/login');
        case 403: error.message = '拒绝访问(403)'; break;
        case 404: error.message = '请求出错(404)'; break;
        case 408: error.message = '请求超时(408)'; break;
        case 500: error.message = '服务器错误(500)'; break;
        case 501: error.message = '服务未实现(501)'; break;
        case 502: error.message = '网络错误(502)'; break;
        case 503: error.message = '服务不可用(503)'; break;
        case 504: error.message = '网络超时(504)'; break;
        case 505: error.message = 'HTTP版本不受支持(505)'; break;
        default: error.message = `连接出错(${error.response.status})!`;
      }
    } else {
      error.message = '连接服务器失败!'
    }
    Message.error(error.message)
    return Promise.reject(error);
  })
}
