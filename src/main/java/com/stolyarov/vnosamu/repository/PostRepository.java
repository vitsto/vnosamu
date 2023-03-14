package com.stolyarov.vnosamu.repository;

import com.stolyarov.vnosamu.entity.Attachment;
import com.stolyarov.vnosamu.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
