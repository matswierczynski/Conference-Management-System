package com.wizzard.uploadpapers.storage;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
 
 
@Component
public class FileValidator implements Validator{
public static final String PDF_MIME_TYPE="application/pdf";
public static final long TEN_MB_IN_BYTES = 10485760;
public static final int TITLE_MIN_LENGTH = 5;

    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadModel.class.isAssignableFrom(clazz);
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        FileUploadModel fileUploadModel = (FileUploadModel)target;
        MultipartFile paperFile = fileUploadModel.getPaperFile();
        MultipartFile abstractFile = fileUploadModel.getAbstractFile();
        String title = fileUploadModel.getTitle();
        
        if (title == null || title.isEmpty() || title.length() < TITLE_MIN_LENGTH) {
        	errors.rejectValue("title","error.fileUploadModel","Title must have at least 5 characters");
        }
        
        if(paperFile == null ||paperFile.isEmpty()){
            errors.rejectValue("paperFile","error.fileUploadModel", "File cannot be empty");
        }
        else if(!PDF_MIME_TYPE.equalsIgnoreCase(paperFile.getContentType())){
            errors.rejectValue("paperFile","error.fileUploadModel", "Please upload valid pdf file only");
        }
        else if(paperFile.getSize() > TEN_MB_IN_BYTES){
            errors.rejectValue("paperFile","error.fileUploadModel", "Please upload a file with size less than 10 MB");
        }
        
        if(abstractFile == null || abstractFile.isEmpty()){
            errors.rejectValue("abstractFile","error.fileUploadModel", "File cannot be empty");
        }
        else if(!PDF_MIME_TYPE.equalsIgnoreCase(abstractFile.getContentType())){
            errors.rejectValue("abstractFile","error.fileUploadModel", "Please upload valid pdf file only");
        }
        else if(abstractFile.getSize() > TEN_MB_IN_BYTES){
            errors.rejectValue("abstractFile","error.fileUploadModel", "Please upload a file with size less than 10 MB");
        }

    }
 
}