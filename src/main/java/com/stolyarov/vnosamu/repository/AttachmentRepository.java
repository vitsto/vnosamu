package com.stolyarov.vnosamu.repository;

import com.stolyarov.vnosamu.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
