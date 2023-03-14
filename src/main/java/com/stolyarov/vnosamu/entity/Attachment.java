package com.stolyarov.vnosamu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attachment_name")
    private String name;
    @Column(name = "attachment_extension")
    private String extension;
    @Column(name = "attachment_size")
    private Long size;
    @Column(name = "attachment_path")
    private String path;
    @Column(name = "attachment_key")
    private String key;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDateTime;

    @ManyToMany (mappedBy = "attachmentSet")
    Set<Post> postSet;

    @ManyToMany (mappedBy = "attachmentSet")
    Set<Comment> commentSet;
}
