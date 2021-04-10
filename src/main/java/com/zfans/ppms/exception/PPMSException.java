package com.zfans.ppms.exception;

import com.zfans.ppms.ret.ResultCodeEnum;
import lombok.Data;

/**
 * @Author Zfans
 */
@Data
public class PPMSException extends RuntimeException {


    //状态码
    private Integer code;

    /**
     * 接受状态码和消息
     *
     * @param code
     * @param message
     */
    public PPMSException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     *
     * @param resultCodeEnum
     */
    public PPMSException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "ShenlanException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
