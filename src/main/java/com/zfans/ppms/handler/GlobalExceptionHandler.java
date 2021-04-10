package com.zfans.ppms.handler;


import com.zfans.ppms.exception.PPMSException;
import com.zfans.ppms.ret.R;
import com.zfans.ppms.ret.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Author Zfans
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        log.error(e.getMessage(), e.getCause());
        return R.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e) {
        log.error(e.getMessage(), e.getCause());
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e.getCause());
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(PPMSException.class)
    @ResponseBody
    public R error(PPMSException e) {
        log.error(String.valueOf(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
