package com.stolyarov.vnosamu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolyarov.vnosamu.dto.PostDTO;
import com.stolyarov.vnosamu.entity.Post;
import com.stolyarov.vnosamu.service.AttachmentService;
import com.stolyarov.vnosamu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createPost(@RequestPart("model") String jsonObject,
                                            @RequestPart(required = false) List<MultipartFile> attachments) {
        postService.savePost(jsonObject, attachments);
        return ResponseEntity.ok().build();
    }
}
