package com.developlife.reviewtwits.controller;

import com.developlife.reviewtwits.entity.FileInfo;
import com.developlife.reviewtwits.message.request.FileUpdateRequest;
import com.developlife.reviewtwits.service.FileStoreService;
import com.developlife.reviewtwits.type.FileReferenceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileStoreService fileStore;

    @PostMapping("/files/save")
    public ResponseEntity<String> saveFile(@ModelAttribute FileUpdateRequest request) throws IOException {

        String referenceType = request.getReferenceType();
        Long id = request.getId();
        List<MultipartFile> attachedFiles = request.getAttachedFiles();
        if(request.getAttachedFiles().get(0).isEmpty() || !FileReferenceType.isValidFileType(referenceType, attachedFiles)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<FileInfo> storeFiles = fileStore.storeFiles(attachedFiles,id,referenceType);
        String storeFilename = storeFiles.get(0).getRealFilename();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFilename));

        return ResponseEntity.accepted().body(resource.toString());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> attachedFileNullExceptionHandler(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
