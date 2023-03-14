package com.stolyarov.vnosamu.service.impl;

import com.stolyarov.vnosamu.entity.Attachment;
import com.stolyarov.vnosamu.repository.AttachmentRepository;
import com.stolyarov.vnosamu.service.AttachmentService;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Value("${path.to.data.file}")
    private String pathToFile;

    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Transactional
    @Override
    public Attachment upload(MultipartFile resource) {
        String key = generateKey(resource.getName());
        Attachment createdAttachment = Attachment.builder()
                .name(FilenameUtils.getBaseName(resource.getOriginalFilename()))
                .extension(FilenameUtils.getExtension(resource.getOriginalFilename()))
                .key(key)
                .path(pathToFile + "/documents")
                .creationDateTime(LocalDateTime.now())
                .size(resource.getSize())
                .build();

        attachmentRepository.save(createdAttachment);

        Path dir = Paths.get(createdAttachment.getPath());
        Path file;
        try {
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            file = Files.createFile(Paths.get(dir.toString(), createdAttachment.getKey()));
            BufferedInputStream fis = new BufferedInputStream(resource.getInputStream());
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file.toFile()));
            fos.write(fis.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Transactional()
    @Override
    public Attachment findAttachmentById(Long id) {
        return attachmentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Resource download(String key) {
        Path path = Paths.get(pathToFile + "/documents/" + key);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw  new IOException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateKey(String name) {
        return DigestUtils.md5DigestAsHex((name + LocalDateTime.now()).getBytes());
    }


}
