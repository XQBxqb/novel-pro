package com.novel.api.config;

import com.novel.api.exception.BusinessException;
import com.novel.core.consts.HttpStatusCode;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @author 昴星
 * @date 2023-09-03 16:27
 * @explain
 */

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {

    @ExceptionHandler(BusinessException.class)
    public RestRes<Void> handleBusinessException(BusinessException e){
        log.error(e.getClass().getSimpleName()+"-"+e.getMessage(),e);
        return RestRes.errorEnum(e.getErrorStatusEnums());
    }

    @ExceptionHandler(RuntimeException.class)
    public RestRes<Void> handlerRuntimeException(RuntimeException runtimeException){
        log.error(runtimeException.getClass().getSimpleName()+"-"+runtimeException.getMessage(),runtimeException);
        return RestRes.errorEnum(ErrorStatusEnums.RES_SYSTEM_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public RestRes<Void> handlerJwtException(JwtException jwtException){
        log.info(jwtException.getClass().getSimpleName()+"-"+jwtException.getMessage(),jwtException);
        return RestRes.errorEnum(ErrorStatusEnums.RES_TOKEN_ERR);
    }

    @ExceptionHandler(DataAccessException.class)
    public RestRes<Void> handlerDataAccessException(DataAccessException dataAccessException){
        log.error(dataAccessException.getClass().getSimpleName()+"-"+dataAccessException.getMessage(),dataAccessException);
        return RestRes.errorEnum(ErrorStatusEnums.RES_DATABASE_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public RestRes<Void> handlerConstraintViolationException(ConstraintViolationException constraintViolationException){
        StringBuilder errors = new StringBuilder();
        constraintViolationException.getConstraintViolations().stream().forEach(error-> errors.append(error.getPropertyPath().toString()+"-"+error.getMessage()+";"));
        log.error(constraintViolationException.getClass().getSimpleName()+"-"+errors.toString(),constraintViolationException);
        return RestRes.error(HttpStatusCode.ILLEAGE_PARAM.msg,HttpStatusCode.ILLEAGE_PARAM.code);
    }
}
