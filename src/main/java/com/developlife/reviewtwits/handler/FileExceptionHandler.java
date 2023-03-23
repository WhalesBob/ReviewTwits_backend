package com.developlife.reviewtwits.handler;

import com.developlife.reviewtwits.message.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.developlife.reviewtwits.handler.ExceptionHandlerTool.makeErrorResponse;

@Slf4j
@RestControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public List<ErrorResponse> sizeLimitExceptionHandler(SizeLimitExceededException e){
        System.out.printf("허가된 파일 사이즈는 %d 인데, 파일 사이즈가 %d 입니다\n",e.getPermittedSize(), e.getActualSize());
        return makeErrorResponse(e, "accountId");
    }
}
