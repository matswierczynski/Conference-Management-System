package com.cms.valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.cms.model.FeedbackUploadModel;

@Component
public class FeedbackValidator implements Validator {
    public static final String PDF_MIME_TYPE = "application/pdf";
    public static final long TEN_MB_IN_BYTES = 10485760;
    public static final int MIN_SCORE = 1;
    public static final int MAX_SCORE = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return FeedbackUploadModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FeedbackUploadModel feedbackUploadModel = (FeedbackUploadModel) target;
        MultipartFile feedbackFile = feedbackUploadModel.getFeedbackFile();
        Integer score = feedbackUploadModel.getScore();

        if (score == null || score < MIN_SCORE || score > MAX_SCORE) {
            errors.rejectValue("score", "error.feedbackUploadModel", "Score must be between 1 and 100");
        }

        if (feedbackFile == null || feedbackFile.isEmpty()) {
            errors.rejectValue("feedbackFile", "error.feedbackUploadModel", "File cannot be empty");
        } else if (!PDF_MIME_TYPE.equalsIgnoreCase(feedbackFile.getContentType())) {
            errors.rejectValue("feedbackFile", "error.feedbackUploadModel", "Please upload valid pdf file only");
        } else if (feedbackFile.getSize() > TEN_MB_IN_BYTES) {
            errors.rejectValue("feedbackFile", "error.feedbackUploadModel",
                    "Please upload a file with size less than 10 MB");
        }

    }

}