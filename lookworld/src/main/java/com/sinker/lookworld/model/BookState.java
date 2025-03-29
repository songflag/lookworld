package com.sinker.lookworld.model;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BookState {
    /**
     * 已确认：预约已确认，等待服务
     */
    CONFIRMED("已预约"),
    /**
     * 已完成：预约服务已完成
     */
    COMPLETED("已完成"),
    /**
     * 已取消：预约已被取消
     */
    CANCELLED("已取消"),
    /**
     * 已过期：预约时间已过期且未完成
     */
    EXPIRED("已过期");


    private String name;

    BookState(String name){this.name=name;}

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static String fromName(String name) {
        if (name==null){
            return null;
        }else if(name.equals("已预约")){
            return "CONFIRMED";
        }else if(name.equals("已完成")){
            return "COMPLETED";
        }else if(name.equals("已取消")){
            return "CANCELLED";
        }else if (name.equals("已过期")){
            return "EXPIRED";
        }
        return null; // 或者抛出异常，视具体需求而定
    }
}
