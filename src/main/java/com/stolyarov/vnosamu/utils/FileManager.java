package com.stolyarov.vnosamu.utils;

import com.stolyarov.vnosamu.entity.Attachment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    public void upload(Attachment attachment, InputStream fileInputStream) {
        Path dir = Paths.get(attachment.getPath());
        Path file;
        try {
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            file = Files.createFile(Paths.get(dir.toString(), attachment.getKey()));
            BufferedInputStream fis = new BufferedInputStream(fileInputStream);
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file.toFile()));
            fos.write(fis.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
