package com.novel.api.exception;

import com.novel.core.enums.ErrorStatusEnums;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-05 22:27
 * @explain
 */
@Data
public class BusinessException extends RuntimeException{
    private ErrorStatusEnums errorStatusEnums;

    public BusinessException(ErrorStatusEnums errorStatusEnums){
        super(errorStatusEnums.message,null,false,false);
        this.errorStatusEnums=errorStatusEnums;
    }
}
