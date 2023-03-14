package com.stolyarov.vnosamu.service;

import com.stolyarov.vnosamu.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDTO savePost(String jsonObj, List<MultipartFile> files);
}
