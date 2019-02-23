package com.example.SA.Controller;

import com.example.SA.Service.DescriptionGenertor;
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
import java.util.Map;
import java.util.UUID;

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
            Map<String, Object> model) throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            File uploadedFile = new File(uploadPath + "/" + resultFileName);
            file.transferTo(uploadedFile);

            Servey uploadedServey = new Servey(resultFileName, userAuthor);
            DescriptionGenertor dg = new DescriptionGenertor(uploadedServey, uploadedServey.getPathToResult());
            dg.generateDescription();

            uploadedFile.delete();

        }

        return "main";
    }

}