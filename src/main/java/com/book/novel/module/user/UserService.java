package com.book.novel.module.user;

import com.book.novel.common.constant.RedisKeyConstant;
import com.book.novel.common.constant.ResponseCodeConst;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.common.service.ImgFileService;
import com.book.novel.module.login.LoginService;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.login.dto.LoginDetailDTO;
import com.book.novel.module.novel.NovelMapper;
import com.book.novel.module.novel.NovelUserMapper;
import com.book.novel.module.novel.constant.NovelResponseCodeConstant;
import com.book.novel.module.novel.dto.NovelDTO;
import com.book.novel.module.novel.dto.NovelDetailDTO;
import com.book.novel.module.user.bo.UserBO;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import com.book.novel.module.user.constant.UserSexEnum;
import com.book.novel.module.user.constant.UserStatusEnum;
import com.book.novel.module.user.entity.UserEntity;
import com.book.novel.module.user.vo.UserInfoVO;
import com.book.novel.module.user.vo.UserRegisterFormVO;
import com.book.novel.util.JsonUtil;
import com.book.novel.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 用户相关业务
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NovelUserMapper novelUserMapper;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private ImgFileService imgFileService;

    @Autowired
    private ValueOperations<String, String> redisValueOperations;

    public UserBO getUserBOById(Integer id) {
        UserEntity userEntity = userMapper.getUserById(id);
        UserBO userBO = new UserBO(userEntity);
        return userBO;
    }

    public UserEntity getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public void updateUserLoginInfo(UserEntity userEntity) {
        userMapper.updateUserLoginInfo(userEntity);
    }

    public UserEntity getUserByUsernameAndPassword(String loginName, String loginPwd) {
        return userMapper.getUserByUsernameAndPassword(loginName, loginPwd);
    }

    public ResponseDTO<ResponseCodeConst> testUsername(String username) {
        if (getIdByUsername(username) != null) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
        } else return ResponseDTO.succ();
    }

    public Integer getIdByUsername(String username) {
        return userMapper.getIdByUsername(username);
    }

    public Integer getIdByEmail(String email) {
        return userMapper.getIdByEmail(email);
    }


    public UserEntity getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public ResponseDTO<ResponseCodeConst> active(String mailUuid) {
        String activeMail = redisValueOperations.get(RedisKeyConstant.WAIT_ACTIVE_USER_PREFIX+mailUuid);
        if (StringUtils.isEmpty(activeMail)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        String json = redisValueOperations.get(RedisKeyConstant.WAIT_ACTIVE_USER_PREFIX + mailUuid);
        if (StringUtils.isEmpty(json)) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }
        UserRegisterFormVO userRegisterFormVO = (UserRegisterFormVO) JsonUtil.toObject(json, UserRegisterFormVO.class);
        if (userRegisterFormVO == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.ACTIVE_CODE_INVALID);
        }

        UserEntity saveUser = new UserEntity();
        saveUser.setEmail(userRegisterFormVO.getEmail());
        saveUser.setUsername(userRegisterFormVO.getUsername());
        saveUser.setPassword(Md5Util.encryptPassword(userRegisterFormVO.getPassword(), userRegisterFormVO.getEmail()));
        saveUser.setStatus(UserStatusEnum.NORMAL.getValue());
        saveUser.setCreateTime(new Date());

        // 设置性别
        if (UserSexEnum.UNKNOWN.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.UNKNOWN.getValue());
        } else if (UserSexEnum.MALE.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.MALE.getValue());
        } else if (UserSexEnum.FEMALE.getDesc().equals(userRegisterFormVO.getSex())) {
            saveUser.setSex(UserSexEnum.FEMALE.getValue());
        }

        userMapper.saveUser(saveUser);

        return ResponseDTO.succ();
    }

    public ResponseDTO<LoginDetailDTO> updateUserInfo(UserInfoVO userInfoVO) {
        UserEntity userEntity = userMapper.getUserById(userInfoVO.getId());
        if (userEntity == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.ERROR_PARAM);
        }

        // 设置用户名
        String paramUsername = userInfoVO.getUsername();
        userEntity.setUsername(paramUsername);

        // 设置性别
        String paramSex = userInfoVO.getSex();
        if (UserSexEnum.MALE.getDesc().equals(paramSex)) {
            userEntity.setSex(UserSexEnum.MALE.getValue());
        } else if (UserSexEnum.FEMALE.getDesc().equals(paramSex)) {
            userEntity.setSex(UserSexEnum.FEMALE.getValue());
        } else {
            userEntity.setSex(UserSexEnum.UNKNOWN.getValue());
        }

        // 设置头像
        String paramHeadImgUrl = userInfoVO.getHeadImgUrl();
        if (! StringUtils.isEmpty(paramHeadImgUrl)) {
            // 原头像不是默认头像 删除原头像
            String dbHeadImgUrl = userEntity.getHeadImgUrl();
            if (! imgFileService.IMG_DEFAULT.equals(dbHeadImgUrl))  {
                imgFileService.deleteUserHeadImg(dbHeadImgUrl);
            }
            userEntity.setHeadImgUrl(paramHeadImgUrl);
        }

        // 设置简介
        String paramIntroduce = userInfoVO.getIntroduce();
        if (! StringUtils.isEmpty(paramIntroduce)) {
            userEntity.setIntroduce(paramIntroduce);
        }

        userMapper.updateUserInfo(userEntity);

        LoginDetailDTO loginDetailDTO = loginService.getLoginDetailDTO(userEntity);

        return ResponseDTO.succData(loginDetailDTO);
    }

    public String getEmailByUsername(String loginName) {
        return userMapper.getEmailByUsername(loginName);
    }

    public ResponseDTO uploadHeadImg(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }

        String contentType = multipartFile.getContentType();
        List<String> imageFormat = imgFileService.getImageFormat();
        if (! imageFormat.contains(contentType)) {
            return ResponseDTO.wrap(UserResponseCodeConst.IMG_FORMAT_ERROR);
        }

        String fileName = imgFileService.saveUserHeadImg(multipartFile);
        if (StringUtils.isEmpty(fileName)) {
            return ResponseDTO.wrap(UserResponseCodeConst.SYSTEM_BUSY);
        }

        return ResponseDTO.succData(fileName);
    }

    public ResponseDTO<List<NovelDTO>> listFavoritesNovel(HttpServletRequest request) {
        // 从token中拿到用户id
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        // 查找当前用户已收藏小说
        List<NovelDTO> favoritesNovel = novelUserMapper.listFavoritesNovel(requestTokenBO.getRequestUserId());

        return ResponseDTO.succData(favoritesNovel);
    }

    public ResponseDTO updateUserStatus2one(HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);
        if (requestTokenBO.getUserBO().getStatus() == 1) {
            ResponseDTO.wrap(UserResponseCodeConst.APPLIED);
        }
        if (userMapper.updateStatusById(1, requestTokenBO.getRequestUserId()) < 1) {
            return ResponseDTO.wrap(UserResponseCodeConst.USER_NOT_EXISTS);
        }
        return ResponseDTO.succ();
    }

    public ResponseDTO favorites(Integer novelId, HttpServletRequest request) {
        NovelDetailDTO novelDetail = novelUserMapper.getNovelDetailById(novelId);
        if (novelDetail == null) {
            return ResponseDTO.wrap(NovelResponseCodeConstant.ERROR_PARAM);
        }

        String token = loginTokenService.getToken(request);
        RequestTokenBO userTokenBO = loginTokenService.getUserTokenInfo(token);

        // 尝试删除记录，如果存在记录则为取消收藏，返回取消成功
        if((novelUserMapper.removeUNFavorites(novelId, userTokenBO.getRequestUserId())) > 0) {
            novelMapper.updateFavorites(novelDetail.getFavorites() - 1, novelId);
            return ResponseDTO.succData(NovelResponseCodeConstant.UN_FAVORITES_SUCCESS);
        }

        novelUserMapper.saveUNFavorites(novelId, userTokenBO.getRequestUserId());
        novelMapper.updateFavorites(novelDetail.getFavorites() + 1, novelId);

        return ResponseDTO.succData(NovelResponseCodeConstant.FAVORITES_SUCCESS);
    }

    @Transactional
    public ResponseDTO recommend(Integer novelId, HttpServletRequest request) {
        String token = loginTokenService.getToken(request);
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(token);

        Integer recommendUser = userMapper.getTicketsById(requestTokenBO.getRequestUserId());
        if (recommendUser == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.TOKEN_INVALID);
        }
        if (recommendUser < 1) {
            return ResponseDTO.wrap(UserResponseCodeConst.RECOMMEND_ERROR);
        }

        userMapper.updateSubRecommend(requestTokenBO.getRequestUserId());
        novelMapper.updateAddRecommend(novelId);

        return ResponseDTO.succ();
    }
}
