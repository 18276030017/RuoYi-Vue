package com.ruoyi.devices.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lfx
 * @program ruoyi
 * @description 访问令牌生成工具
 * @date 2024/06/12
 */
public class DeviceAuthUtil {

    /**
     * 生成访问令牌
     * @param deviceId 设备唯一标识符
     * @param deviceType 设备类型
     * @param productKey 产品秘钥
     * @param deviceSecret 设备秘钥
     * @return 访问令牌字符串
     */
    public static String generateAccessToken(String deviceId, String deviceType, String deviceModel, String productKey, String deviceSecret) {
        try {
            // 构建Payload，不设置过期时间
            Map<String, Object> claims = new HashMap<>();
            claims.put("deviceId", deviceId);
            claims.put("deviceType", deviceType);
            claims.put("deviceModel", deviceModel);

            // 获取HMAC-SHA256算法的Mac实例
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(deviceSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

            // 初始化Mac实例
            sha256Hmac.init(secretKey);

            // 使用HMAC SHA-256算法和设备秘钥生成JWT
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date()) // 设置发行时间
                    // 注意：不设置过期时间，或设置一个非常远的未来时间
                    // .setExpiration(new Date(System.currentTimeMillis() + Long.MAX_VALUE)) // 如果需要设置一个非常远的过期时间
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            // 执行消息认证码计算
            byte[] hash = sha256Hmac.doFinal(token.getBytes(StandardCharsets.UTF_8));

            // 对结果进行Base64编码，得到最终的访问令牌
            String accessToken = Base64.getEncoder().encodeToString(hash);

            // 返回访问令牌
            System.out.println("访问令牌：" + accessToken);

            return accessToken;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate access token", e);
        }
    }
}
