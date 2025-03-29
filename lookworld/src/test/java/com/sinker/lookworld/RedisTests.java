package com.sinker.lookworld;


import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.EventArgUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.sinker.lookworld.model.search.UserSearch;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld
 * Author: sinker
 * CreateTime: 2025-03-06  19:16
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@SpringBootTest
public class RedisTests {

    @Autowired
     private RedisTemplate<String,Object> redisTemplate;

    @Test
    @DisplayName("字符串测试")
    public void test1(){
        redisTemplate.opsForValue().set("a2","qqq", Duration.of(20,ChronoUnit.SECONDS));

        Assertions.assertEquals("qqq",redisTemplate.opsForValue().get("a2"));
    }

    private static String secretKey="aaaa";
    private static Integer expiresAt=7200;

    @Test
    public void testJwt(){
        Map<String,String> map =new HashMap<>();

        map.put("userId","12");
        map.put("username","sinker");
        System.out.println(getToken(map));

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        DecodedJWT verify = verifier.verify("eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJ7XCJpZFwiOjEyLFwibmFtZVwiOlwic2lua2VyXCIsXCJhY2NvdW50XCI6XCJzaW5rZXIxMjNcIixcInBob25lXCI6XCIxOTAyXCJ9IiwiZXhwIjoxNzQxNTg2MDkxfQ.G86gh85kBxrbxNAk8rCapPngelaeYMJKGRDaGQOlFlE");
        System.out.println(verify.getClaim("userId"));
        System.out.println(verify.getClaim("username"));
        UserSearch userSearch  = (UserSearch) verify.getAudience();
        System.out.println(userSearch.getAccount());
    }



    public static String getToken(Map<String,String> payload){
        UserSearch userSearch = new UserSearch();
        userSearch.setId(12);
        userSearch.setName("sinker");
        userSearch.setPhone("1902");
        userSearch.setAccount("sinker123");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,expiresAt);
        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(getHeader());
        builder.withAudience(JSONUtil.toJsonStr(userSearch));
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(secretKey));
    }



    public static Map<String,Object> getHeader(){
        Map<String, Object> map = new HashMap<>();
        map.put("type","jwt");
        map.put("alg","HMAC256");
        return map;
    }

}
