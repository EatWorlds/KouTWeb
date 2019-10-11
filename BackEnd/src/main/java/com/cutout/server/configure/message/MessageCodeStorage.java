package com.cutout.server.configure.message;

import org.springframework.stereotype.Component;

@Component
public class MessageCodeStorage {

    /**
     *messagecode :100000  messagevalue :请求成功
     */
    public final String success_code="success_code|100000|请求成功";

    /**
     *messagecode :100001  messagevalue :邮箱或者密码为空
     */
    public static final String user_email_password_empty="user_email_password_empty|100001|邮箱或者密码为空";

    /**
     * messagecode :100002  messagevalue :用户信息不存在
     */
    public static final String user_not_exists_error="user_not_exists_error|100002|用户信息不存在";

    /**
     * messagecode :100003  messagevalue :用户信息已存在
     */
    public static final String user_login_exists_error="user_login_exists_error|100003|用户信息已存在";

    /**
     *messagecode :100004  messagevalue :注册失败
     */
    public final String user_register_failed ="user_register_failed|100004|注册失败";

    /**
     *messagecode :100005  messagevalue :邮箱格式不正确
     */
    public final String user_email_invalid ="user_email_invalid|100005|邮箱格式不正确";

    /**
     * messagecode :100006  messagevalue :邮箱或者密码不正确
     */
    public static final String user_login_email_password_error="user_email_name_password_error|100006|邮箱或者密码不正确";

    /**
     * messagecode :100007  messagevalue :链接验证码已失效
     */
    public static final String user_login_check_code_error="user_login_check_code_error|100007|链接验证码已失效";

    /**
     * messagecode :100008  messagevalue :请选择一个文件上传
     */
    public static final String user_upload_file_empty="user_upload_file_empty|100008|请选择一个文件上传";

    /**
     * messagecode :100009  messagevalue :用户未登录
     */
    public static final String user_not_login_error="user_not_login_error|100009|用户未登录";

    /**
     * messagecode :100010  messagevalue :暂无产品信息
     */
    public static final String product_info_empty="product_info_empty|100010|暂无产品信息";

    /**
     * messagecode :100011  messagevalue :请求过于频繁
     */
    public static final String request_busy="request_busy|100011|请求过于频繁";

    /**
     * messagecode :100012  messagevalue :验证码为空
     */
    public static final String user_verity_code_empty="user_verity_code_empty|100012|验证码为空";

    /**
     * messagecode :100013  messagevalue :验证码无效
     */
    public static final String user_verity_code_invalid="user_verity_code_invalid|100013|验证码无效";

    /**
     * messagecode :100014  messagevalue :验证码过期
     */
    public static final String user_verity_code_out_time="user_verity_code_out_time|100014|验证码过期";

    /**
     * messagecode :100015  messagevalue :验证码不正确
     */
    public static final String user_verity_code_error="user_verity_code_error|100015|验证码不正确";

}
