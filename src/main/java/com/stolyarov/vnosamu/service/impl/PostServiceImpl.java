package com.stolyarov.vnosamu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stolyarov.vnosamu.dto.PostDTO;
import com.stolyarov.vnosamu.entity.Attachment;
import com.stolyarov.vnosamu.entity.Post;
import com.stolyarov.vnosamu.repository.PostRepository;
import com.stolyarov.vnosamu.service.AttachmentService;
import com.stolyarov.vnosamu.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private ObjectMapper objectMapper;
    private AttachmentService attachmentService;
    private PostRepository postRepository;

    public PostServiceImpl(ObjectMapper objectMapper, AttachmentService attachmentService, PostRepository postRepository) {
        this.objectMapper = objectMapper;
        this.attachmentService = attachmentService;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public PostDTO savePost(String jsonObject, List<MultipartFile> files) {
        try {
            Post post = objectMapper.readValue(jsonObject, Post.class);
            if (files != null) {
                Set<Attachment> collect = files
                        .stream()
                        .map(file -> attachmentService.upload(file, "posts"))
                        .collect(Collectors.toSet());
                post.setAttachmentSet(collect);
            }
            postRepository.save(post);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
