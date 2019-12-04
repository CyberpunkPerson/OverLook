package com.overlook.core.converter;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OverlookMultipartFile implements MultipartFile {

    private final byte[] content;

    @Getter
    private String name;

    @Getter
    private String originalFilename;

    @Getter
    private String contentType;

    public OverlookMultipartFile(@NonNull String name, @NonNull String contentType, @NonNull byte[] content) {
        this.name = name;
        this.contentType = contentType;
        this.content = content;
    }

    public OverlookMultipartFile(@NonNull String name, @NonNull String originalFilename, @NonNull String contentType, @NonNull byte[] content) {
        this(name, contentType, content);
        this.originalFilename = originalFilename;
    }

    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() {
        return this.content;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}
