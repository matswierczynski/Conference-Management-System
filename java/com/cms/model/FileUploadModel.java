package com.cms.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadModel {

    private MultipartFile paperFile;
    private MultipartFile abstractFile;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getPaperFile() {
        return paperFile;
    }

    public void setPaperFile(MultipartFile paperFile) {
        this.paperFile = paperFile;
    }

    public MultipartFile getAbstractFile() {
        return abstractFile;
    }

    public void setAbstractFile(MultipartFile abstractFile) {
        this.abstractFile = abstractFile;
    }

}
