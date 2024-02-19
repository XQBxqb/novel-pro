package com.novel.core.res;


import com.novel.core.enums.ErrorStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-02 22:07
 * @explain
 */
@Data
@AllArgsConstructor
public class RestRes<T> {
    private String message;

    private String code;

    private T data;

    private RestRes() {
        message= ErrorStatusEnums.RES_OK.message;
        code=ErrorStatusEnums.RES_OK.code;
    }

    private RestRes(T data) {
        this();
        this.data = data;
    }

    private RestRes(String code,T data){
        this.code=code;
        this.data=data;
    }

    private RestRes(ErrorStatusEnums errorStatusEnums){
        message= errorStatusEnums.message;
        code=errorStatusEnums.code;
    }

    public RestRes(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public static RestRes ok(){
        return new RestRes<Void>();
    }

    public static <T> RestRes<T> ok(T data){
        return new RestRes<T>(data);
    }

    public static RestRes<Void> error(String message,String code){
        return new RestRes<>(message,code);
    }

    public static <T> RestRes<T> errors(String message,String code,T data){
        return new RestRes<T>(message,code,data);
    }

    public static <T> RestRes<T> fileResp(String code,T data){
        return new RestRes<T>(code,data);
    }

    public static RestRes errorEnum(ErrorStatusEnums errorStatusEnums){
        return new RestRes<Void>(errorStatusEnums);
    }

}
