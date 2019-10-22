/**
 * 注册模块接口列表
 */

import base from './base'; // 导入接口域名列表
import axios from '@/request/http'; // 导入http中创建的axios实例
import qs from 'qs'; // 根据需求是否导入qs模块

const registered = {    
    // 获取验证码
    getEmailCode (params) {
        return axios.get(`${base.koutu}/v1/VerifiedCode`,{
            params: params  
        })
    },

    // 注册账号
    postRegistered (params) {
        return axios.post(`${base.koutu}/v1/user`, qs.stringify(params))
    }
}

export default registered;
