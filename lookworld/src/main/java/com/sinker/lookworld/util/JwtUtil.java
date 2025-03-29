package com.sinker.lookworld.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.util
 * Author: sinker
 * CreateTime: 2025-03-10  10:22
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public class JwtUtil {
    private static final String secretKey="!#lookworld";
    private static final Integer expiresAt=120;


    //生成jwt
    public static String getToken(Map<String,String> payload){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,expiresAt);

        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(getHeader());
        payload.forEach(builder::withClaim);
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(secretKey));
    }

    //获取jwt头部
    private static   Map<String,Object> getHeader(){
        Map<String, Object> map = new HashMap<>();
        map.put("type","jwt");
        map.put("alg","HMAC256");
        return map;
    }

    public static DecodedJWT getJwt(String auth){
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(auth);
    }
}
