package com.sinker.lookworld.util;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Stack;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.util
 * Author: sinker
 * CreateTime: 2025-03-04  17:12
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Getter
public class JsonResult implements Serializable {
    private final int code;
    private final boolean success;
    private final Object data;
    private String msg;
    @Setter
    private int count;

    public JsonResult(int code, boolean success,String msg, Object data) {
        this.code = code;
        this.success = success;
        this.msg=msg;
        this.data = data;
    }

    public static JsonResult success(String msg, Object data){
        return new JsonResult(1,true,msg,data);
    }
    public static JsonResult success(String msg){
        return success(msg,null);
    }
    public static JsonResult success(Object data){
        return success(null,data);
    }

    public static JsonResult fail(String msg,Object data){
        return new JsonResult(-1,false,msg,data);
    }

    public static JsonResult fail(String msg){
        return fail(msg,null);
    }
    public static JsonResult fail(){
        return fail(null);
    }

}
