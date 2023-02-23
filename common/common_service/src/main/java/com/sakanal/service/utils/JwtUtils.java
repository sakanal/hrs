package com.sakanal.service.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
public class JwtUtils {

    // 过期时间
    public static final int EXPIRE_HOUR = 24;
    // 密钥
    public static final String APP_SECRET = "sakanal-hrs";

    /**
     * 生成Token
     *
     * @param id 用户id
     * @return 根据id生成的token
     */
    public static String getUserToken(Object id) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("userId", id);
                put(JWTPayload.EXPIRES_AT, DateUtil.offsetHour(new Date(),EXPIRE_HOUR));
            }
        };
        return JWTUtil.createToken(map, APP_SECRET.getBytes());
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken 待判断的token
     * @return jwtToken是否存在与有效
     */
    public static boolean checkUserToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            JWTValidator.of(jwtToken).validateDate();
            return JWTUtil.verify(jwtToken, APP_SECRET.getBytes());
        }
        return false;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request 请求头
     * @return jwtToken是否存在与有效
     */
    public static boolean checkUserToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        return checkUserToken(jwtToken);
    }

    /**
     * 根据jwtToken获取会员id
     *
     * @param jwtToken 待解析的jwtToken
     * @return jwtToken中解析出的会员id
     */
    public static String getUserIdByToken(String jwtToken) {
        if (checkUserToken(jwtToken)){
            return String.valueOf(JWTUtil.parseToken(jwtToken).getPayload("userId"));
        }else {
            return null;
        }
    }

    /**
     * 根据token获取会员id
     *
     * @param request 请求头
     * @return jwtToken中解析出的会员id
     */
    public static String getUserIdByToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        return getUserIdByToken(jwtToken);
    }
}
