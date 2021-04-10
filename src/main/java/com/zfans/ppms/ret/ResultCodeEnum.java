package com.zfans.ppms.ret;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Zfans
 */
@Getter
@ToString
public enum ResultCodeEnum {

    SUCCESS(true, 20000, "成功！"),
    UNKNOWN_REASON(false, 20001, "未知错误！"),

    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误！"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常！"),
    PARAM_ERROR(false, 21003, "参数不正确！"),

    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误！"),
    FILE_DELETE_ERROR(false, 21005, "文件刪除错误！"),


    URL_ENCODE_ERROR(false, 23001, "URL编码失败！"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(false, 23002, "非法回调请求！"),
    FETCH_ACCESSTOKEN_FAILD(false, 23003, "获取accessToken失败！"),
    FETCH_USERINFO_ERROR(false, 23004, "获取用户信息失败！"),
    LOGIN_ERROR(false, 23005, "登录失败！"),


    GATEWAY_ERROR(false, 26000, "服务不能访问！"),


    LOGIN_AUTH(false, 28004, "需要登录！"),
    LOGIN_ACL(false, 28005, "没有权限！"),
    DISEASE_EXIST_ACRE(false, 22221, "病害表中占用了该苗木！"),
    PESTS_EXIST_ACRE(false, 22222, "虫害表中占用了该苗木！"),
    REPEAT_ACRE(false, 22223, "重复地块！");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
