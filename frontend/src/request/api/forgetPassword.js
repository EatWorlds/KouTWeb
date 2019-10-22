/**
 * 忘记密码
 */

 import base from './base'
 import axios from '@/request/http.js'
 import qs from 'qs';  


 const forgetPasswords = {
    // 获取验证码
    getEmailCode (params) {
        return axios.get(`${base.koutu}/v1/VerifiedCode`,{
            params: params  
        })
    },
    
    // 验证验证码
    verifiedCode (params) {
        return axios.post(`${base.koutu}/v1/`, qs.stringify(params))
    },

    // 忘记密码
    forgetPassword (params){
        return axios.post(`${base.koutu}/v1/`, qs.stringify(params))
    }

 }
