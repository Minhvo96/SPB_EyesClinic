package com.codegym.spb_eyesclinic_project.service.avatarService;

import com.cloudinary.Cloudinary;
import com.codegym.spb_eyesclinic_project.domain.Avatar;
import com.codegym.spb_eyesclinic_project.domain.dto.avatarDTO.AvatarResponse;
import com.codegym.spb_eyesclinic_project.repository.AvatarRepository;
import com.codegym.spb_eyesclinic_project.utils.UploadUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class AvatarService {
    private final Cloudinary cloudinary;

    private final AvatarRepository avatarRepository;

    private final UploadUtils uploadUtils;

    public List<AvatarResponse> saveAvatar(List<MultipartFile> files)throws IOException {
        List<AvatarResponse> productImageResponses = new ArrayList<>();
        for ( var file : files){
            var fileNew = new Avatar();
            avatarRepository.save(fileNew);

            var uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadUtils.buildAvatarUploadParams(fileNew));

            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            fileNew.setFileName(fileNew.getId() + "." + fileFormat);
            fileNew.setFileUrl(fileUrl);
            fileNew.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            fileNew.setCloudId(fileNew.getFileFolder() + "/" + fileNew.getId());
            avatarRepository.save(fileNew);
            var fileId = fileNew.getId();
            productImageResponses.add(new AvatarResponse(fileId));
        }
        return productImageResponses;
    }
}
