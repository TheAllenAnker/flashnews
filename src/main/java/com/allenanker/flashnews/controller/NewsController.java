package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(path = "/uploadImage/", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if (fileUrl == null) {
                return FlashNewsUtil.getJSONString(1, "Upload Failed");
            }

            return fileUrl;
        } catch (Exception e) {
            logger.error("Upload Image Failed: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Upload Failed");
        }
    }

    @RequestMapping(path = "/image", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) {
        response.setContentType("image");
        try {
            StreamUtils.copy(new FileInputStream(new File(FlashNewsUtil.UPLOAD_IMAGE_DIR + imageName)),
                    response.getOutputStream());
        } catch (Exception e) {
            logger.error("Image Loading Error: " + e.getMessage());

        }
    }
}
