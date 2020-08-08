package com.book.novel.module.login;

import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.UserService;
import com.book.novel.module.user.constant.UserStatusEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: liu
 * @Date: 2020/8/8
 * @Description:
 */

@Slf4j
@Service
public class LoginTokenService {

    /**
     * 过期时间一天
     */
    private static final int EXPIRE_SECONDS = 1 * 24 * 3600;

    /**
     * jwt加密字段
     */
    private static final String CLAIM_ID_KEY = "id";

    private String jwtKey = "jwtKey";

    @Autowired
    private UserService userService;

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
        LocalDateTime localDateTimeExpire = localDateTimeNow.plus(EXPIRE_SECONDS, ChronoUnit.SECONDS);
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

        UserBO userBO = userService.getUserBOById(id);
//        RequestTokenBO tokenBO = new RequestTokenBO(userBO);
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

        UserBO userBO = userService.getUserBOById(userId);
        if (userBO == null) {
            return null;
        }

        if (UserStatusEnum.NOT_ACTIVE.getValue().equals(userBO.getStatus())) {
            return null;
        }

        if (UserStatusEnum.DISABLED.getValue().equals(userBO.getStatus())) {
            return null;
        }

        return new RequestTokenBO(userBO);
    }

}
