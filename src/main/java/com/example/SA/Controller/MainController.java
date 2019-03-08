package com.example.SA.Controller;

import com.example.SA.Service.DescriptionGenerator;
import com.example.SA.domain.Servey.Servey;
import com.example.SA.domain.User.User;
import com.example.SA.repos.MessageRepoTODELETE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private MessageRepoTODELETE messageRepo;

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }

    @PostMapping("/main")
    public String addFile(
            @AuthenticationPrincipal User userAuthor,
            @RequestParam("file") MultipartFile file,
            @RequestParam("header") String headerServey,
            @RequestParam Map<String, String> form,
            @RequestParam("responderType") String respType,
            @RequestParam(value = "1question", required = false, defaultValue = "0") String question1,
            @RequestParam(value = "2question", required = false, defaultValue = "0") String question2,
            Map<String, Object> model) throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                if (!uploadDir.mkdir()) {
                    throw new FileSystemException("Can not make dir " + uploadPath);
                }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            File uploadedFile = new File(uploadPath + "/" + resultFileName);
            file.transferTo(uploadedFile);

            Servey uploadedServey = new Servey(resultFileName, headerServey, respType, userAuthor);

            boolean intervals = form.keySet().contains("intervals");
            boolean minMax = form.keySet().contains("minMax");
            boolean all = form.keySet().contains("all");
            boolean compare = form.keySet().contains("compare");

            int qNumber1 = -1, qNumber2 = -1;
            if (compare) {
                qNumber1 = Integer.parseInt(question1);
                qNumber2 = Integer.parseInt(question2);
            }


            DescriptionGenerator dg = new DescriptionGenerator(uploadedServey, uploadedServey.getPathToResult(), intervals, minMax, all, compare, qNumber1, qNumber2);
            dg.generateDescription();

            uploadedFile.delete();

        }

        return "main";
    }

}