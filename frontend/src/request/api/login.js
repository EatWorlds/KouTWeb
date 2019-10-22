/**
 * 登录模块接口列表
 */

import base from './base';          // 导入接口域名列表
import axios from '@/request/http'; // 导入http中创建的axios实例
import qs from 'qs';                // 根据需求是否导入qs模块

const logins = { 

    // 登录 
    login (params) {
        return axios.post(`${base.koutu}/v1/user/login`, qs.stringify(params))
    },

    // 退出登录
    logout () {
        
    },

    // 找回密码
    retrievePassword () {

    }
}

export default logins;