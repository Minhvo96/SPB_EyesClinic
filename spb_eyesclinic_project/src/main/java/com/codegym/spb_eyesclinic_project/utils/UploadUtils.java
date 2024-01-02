package com.codegym.spb_eyesclinic_project.utils;

import com.cloudinary.utils.ObjectUtils;
import com.codegym.spb_eyesclinic_project.domain.Avatar;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtils {
    public static final String IMAGE_UPLOAD_FOLDER = "eyes-clinic";
    public Map buildAvatarUploadParams(Avatar file) {
        if (file == null || file.getId() == null)
            throw new RuntimeException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, file.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }
}
