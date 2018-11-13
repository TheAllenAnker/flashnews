package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.NewsDAO;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLastestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public String saveImage(MultipartFile file) throws Exception {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String ext = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!FlashNewsUtil.isImageFileAllowed(ext)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        Files.copy(file.getInputStream(), new File(FlashNewsUtil.UPLOAD_IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        return FlashNewsUtil.FLASHNEWS_DOMAIN + "image?name=" + fileName;
    }
}
