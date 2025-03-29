package com.sinker.lookworld.common;


import cn.dev33.satoken.stp.StpUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sinker.lookworld.util.JsonResult;
import com.sinker.lookworld.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.common
 * Author: sinker
 * CreateTime: 2025-03-07  10:00
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
//    private  RedisTemplate<Object, Object> redisTemplate;
//    @Autowired
//    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        }

    //sin 登录认证拦截，判断请求是否携带token,以及token是否正确
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization!=null&&!authorization.isEmpty()) {
            try {
                if (StpUtil.isLogin()) {
                    DecodedJWT jwt = JwtUtil.getJwt(authorization);
                    String account = jwt.getClaim("account").asString();
                    if (account != null) {
                        return true;
                    }
                }
            } catch (Exception e) {
                response.setStatus(401);
                return false;
            }
        }
        response.setStatus(401);
        return false;
    }
}
