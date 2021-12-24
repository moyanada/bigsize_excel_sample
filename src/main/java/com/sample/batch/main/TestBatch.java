package com.sample.batch.main;

import com.sample.batch.dao.TestDAOImpl;
import com.sample.batch.dto.ExcelFileInfo;
import com.sample.common.constants.SampleConstants;
import com.sample.common.excel.TestExcelHandler;
import com.sample.common.file.BatchFileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestBatch {

    private final BatchFileManager batchFileManager;
    private final TestDAOImpl testDAOImpl;

    public void doProcess() {
        //임시테스트로 값을 셋팅해보자
        String code = "A0001";
        String targetDay = "20211224";

        //디렉토리 생성 및 임시파일 path설정
        batchFileManager.createDirAndSetPath(code, targetDay);

        //엑셀 파일명, 저장할 디렉토리 path설정
        ExcelFileInfo excelFileInfo = new ExcelFileInfo();
        excelFileInfo.setTitle(code);
        excelFileInfo.setSaveFileDirPath(batchFileManager.getFileFullPath());

        TestExcelHandler testExcelHandler = new TestExcelHandler(excelFileInfo, SampleConstants.EXCEL_MEMORY_KEEP_SIZE);
        testDAOImpl.createExcelByTmpTable(testExcelHandler);

    }

}
