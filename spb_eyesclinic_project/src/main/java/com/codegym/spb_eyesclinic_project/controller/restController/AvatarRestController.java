package com.codegym.spb_eyesclinic_project.controller.restController;

import com.codegym.spb_eyesclinic_project.domain.dto.avatarDTO.AvatarResponse;
import com.codegym.spb_eyesclinic_project.service.avatarService.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/avatar")
@AllArgsConstructor
public class AvatarRestController {
    private final AvatarService avatarService;

    @PostMapping()
    public List<AvatarResponse> upload(@RequestParam("files") List<MultipartFile> files) throws IOException {
        return avatarService.saveAvatar(files);
    }
}
