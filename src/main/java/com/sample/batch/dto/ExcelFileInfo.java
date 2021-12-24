package com.sample.batch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ExcelFileInfo {
    //파일 제목
    private String title;
    //파일 저장 경로
    private String saveFileDirPath;
}
