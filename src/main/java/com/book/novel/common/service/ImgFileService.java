package com.book.novel.common.service;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @Author: liu
 * @Date: 2020/8/12
 * @Description: 图片文件处理业务
 */

@Service
public class ImgFileService {

    // 支持的文件格式
    public static final String JPG = "image/jpg";

    public static final String JPEG = "image/jpeg";

    public static final String PGN = "image/png";

    public static final String IMG_DEFAULT = "default.jpg";

    @Value("${img.user-head-img.path-prefix}")
    private String userHeadImgPathPrefix;

    @Value("${img.novel-cover-img.path-prefix}")
    private String novelCoverImgPathPrefix;

    public static List<String> getImageFormat() {
        List<String> imageFormat = new ArrayList<>();
        imageFormat.add(JPG);
        imageFormat.add(JPEG);
        imageFormat.add(PGN);
        return imageFormat;
    }

    public String saveUserHeadImg(MultipartFile multipartFile) {
        return saveImg(multipartFile, userHeadImgPathPrefix);
    }

    public String saveNovelCoverImg(MultipartFile multipartFile) {
        return saveImg(multipartFile, novelCoverImgPathPrefix);
    }

    public String getUserHeadImg(String fileName) {
        return getBase64Img(userHeadImgPathPrefix, fileName);
    }

    public String getNovelCoverImg(String fileName) {
        return getBase64Img(novelCoverImgPathPrefix, fileName);
    }

    public void deleteUserHeadImg(String fileName) {
        deleteImgFile(userHeadImgPathPrefix, fileName);
    }

    public void deleteNovelCoverImg(String fileName) {
        deleteImgFile(novelCoverImgPathPrefix, fileName);
    }

    private void deleteImgFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private String saveImg(MultipartFile multipartFile, String dir) {
        if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return null;
        }

        String contentType = multipartFile.getContentType();
        List<String> imageFormat = getImageFormat();
        if (! imageFormat.contains(contentType)) {
            return null;
        }

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileInputStream fis = null;
        BufferedOutputStream bos = null;
        try {
            fis = (FileInputStream) multipartFile.getInputStream();
            String fileName = UUID.randomUUID().toString() + ".jpg";
            bos = new BufferedOutputStream(new FileOutputStream(dir + File.separator + fileName));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }

            return fileName;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getBase64Img(String path, String fileName) {
        File img = new File(path + File.separator + fileName);
        if (! img.exists()) {
            img = new File(path + File.separator + IMG_DEFAULT);
        }

        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(new FileInputStream(img));
        } catch (IOException e) {
            return null;
        }
        return "data:image/jpg;base64, " + Base64.getEncoder().encodeToString(bytes);
    }
}
