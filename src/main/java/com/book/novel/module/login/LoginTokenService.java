package com.book.novel.module.login;

import com.book.novel.common.constant.RedisExpireTimeConstant;
import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.UserService;
import com.book.novel.module.user.constant.UserStatusEnum;
import com.book.novel.util.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description: TOKEN加密解密
 */

@Slf4j
@Service
public class LoginTokenService {

    /**
     * jwt加密字段
     */
    private static final String CLAIM_ID_KEY = "id";

    private String jwtKey = "jwtKey";

    public static final String TOKEN_NAME = "x-access-token";

    @Autowired
    private UserService userService;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    /**
     * 生成JWT
     * @param loginDetailDTO
     * @return
     */
    public String generateToken(LoginDetailDTO loginDetailDTO) {
        Integer id = loginDetailDTO.getId();
        /**将token设置为jwt格式*/
        String baseToken = UUID.randomUUID().toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeExpire = localDateTimeNow.plus(RedisExpireTimeConstant.OND_DAY, ChronoUnit.SECONDS);
        Date from = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        Date expire = Date.from(localDateTimeExpire.atZone(ZoneId.systemDefault()).toInstant());

        Claims jwtClaims = Jwts.claims().setSubject(baseToken);
        jwtClaims.put(CLAIM_ID_KEY, id);
        String compactJws = Jwts.builder()
                .setClaims(jwtClaims)
                .setNotBefore(from)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();

        return compactJws;
    }

    /**
     *解析JWT
     * @param token
     * @return
     */
    public RequestTokenBO getUserTokenInfo(String token) {
        Integer userId = -1;
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
            String idStr = claims.get(CLAIM_ID_KEY).toString();
            userId = Integer.valueOf(idStr);
        } catch (Exception e) {
            log.error("getUserTokenInfo error:{}", e);
            return null;
        }

        // 从redis中获取user信息
        String userBOJson = redisValueOperations.get(RedisKeyConstant.USER_INFO_PREFIX + userId);
        UserBO userBO = (UserBO) JsonUtil.toObject(userBOJson, UserBO.class);
        if (userBO == null) {
            // redis中没有则从db中查出并缓存到redis
            userBO = userService.getUserBOById(userId);
            if (userBO == null) {
                return null;
            }
            redisValueOperations.set(RedisKeyConstant.USER_INFO_PREFIX+userId, JsonUtil.toJson(userBO), RedisExpireTimeConstant.OND_DAY, TimeUnit.SECONDS);
        }

        if (UserStatusEnum.NOT_ACTIVE.getValue().equals(userBO.getStatus())) {
            return null;
        }

        if (UserStatusEnum.DISABLED.getValue().equals(userBO.getStatus())) {
            return null;
        }

        return new RequestTokenBO(userBO);
    }

    public String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader(TOKEN_NAME);
        String requestToken = request.getParameter(TOKEN_NAME);
        String xAccessToken = headerToken != null ? headerToken : requestToken;
        return xAccessToken;
    }

}
