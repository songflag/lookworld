package com.sinker.lookworld.common;


import com.sinker.lookworld.util.JsonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Set;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.common
 * Author: sinker
 * CreateTime: 2025-03-05  19:48
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@ControllerAdvice
public class ExceptionIntercept {

    //sin 模型类校验异常
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<JsonResult> handlerException(MethodArgumentNotValidException e){
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.stream().findFirst().get();
        return ResponseEntity.ok(JsonResult.fail(objectError.getDefaultMessage()));
    }
}
