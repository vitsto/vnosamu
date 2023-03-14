package com.stolyarov.vnosamu.service;

import com.stolyarov.vnosamu.entity.Attachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentService {
    Attachment upload(MultipartFile resource, String directory);

    Attachment findAttachmentById(Long id);

    Resource download(String key);
}
