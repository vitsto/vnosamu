package com.stolyarov.vnosamu.controller;

import com.stolyarov.vnosamu.entity.Attachment;
import com.stolyarov.vnosamu.service.AttachmentService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@RequestParam MultipartFile attachment) {
        attachmentService.upload(attachment);
        return ResponseEntity.ok().build();
    }

//    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<Resource> download(@PathVariable("id") Long id) {
//        try {
//            Attachment foundAttachment = attachmentService.findAttachmentById(id);
//            Resource resource = attachmentService.download(foundAttachment.getKey());
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; " +
//                            "filename=\"" + foundAttachment.getName() + "." + foundAttachment.getExtension()+"\"")
//                    .body(resource);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) {
        try {
            Attachment foundAttachment = attachmentService.findAttachmentById(id);
            Resource resource = attachmentService.download(foundAttachment.getKey());
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(foundAttachment.getName()+ "." + foundAttachment.getExtension(), StandardCharsets.UTF_8)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                    .body(resource);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
