package com.sample.common.file;

import com.sample.common.properties.ExcelProperties;
import com.sample.common.util.DateUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.DefaultTempFileCreationStrategy;
import org.apache.poi.util.TempFile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

@Component
@RequiredArgsConstructor
@Getter @Setter
@Slf4j
public class BatchFileManager {

    private final ExcelProperties excelProperties;

    /**
     * 파일 fullpath
     */
    private String fileFullPath;

    /**
     * 디렉토리 생성 및 임시파일 path설정
     */
    public void createDirAndSetPath(String code, String targetDay) {
        makeBaseDir(code, targetDay);
        setTmpPath();
    }

    /**
     * 디렉토리 생성
     */
    private void makeBaseDir(String code, String targetDay) {
        setFileFullPath(excelProperties.getFileDirPath());

        String[] aDirName = new String[3];
        aDirName[0] = code;

        if(StringUtils.hasText(targetDay) && targetDay.length() >= 6) {
            aDirName[1] = targetDay.substring(0, 4);    /* Year */
            aDirName[2] = targetDay.substring(4,6);     /* Month */
        } else {
            aDirName[1] = DateUtil.getFormattedString("YYYY");
            aDirName[2] = DateUtil.getFormattedString("MM");
        }
        for(String dirName : aDirName) {
            setFileFullPath(getFileFullPath().toString()+"/"+dirName);
            mkDir(dirName);
        }
    }

    private void mkDir(String dirName) {
        File fdir = new File(getFileFullPath());
        if(!fdir.exists()) {
            fdir.mkdir();
            log.info("[DIR] {} 생성 완료", getFileFullPath());
        }
        log.info("[DIR] {} 생성 이미 생성됨", getFileFullPath());
    }

    /**
     * org.apache.poi tmp파일 경로 수정
     */
    private void setTmpPath() {
        TempFile.setTempFileCreationStrategy(new DefaultTempFileCreationStrategy(new File(excelProperties.getTmpDirPath())));
    }

}
