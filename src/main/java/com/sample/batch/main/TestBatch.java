package com.sample.batch.main;

import com.sample.batch.dao.TestDAOImpl;
import com.sample.batch.dto.ExcelFileInfo;
import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.constants.FlushControl;
import com.sample.common.excel.TestExcelHandler;
import com.sample.common.file.BatchFileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestBatch {

    private final BatchFileManager batchFileManager;
    private final TestDAOImpl testDAOImpl;

    public void doProcess() {
        StopWatch stopwatch = new StopWatch("TestBatch");
        stopwatch.start();

        //임시테스트로 값을 셋팅해보자
        String code = "A0001";
        String targetDay = "20211224";

        //디렉토리 생성 및 임시파일 path설정
        batchFileManager.createDirAndSetPath(code, targetDay);

        //엑셀 파일명, 저장할 디렉토리 path설정
        ExcelFileInfo excelFileInfo = new ExcelFileInfo();
        excelFileInfo.setTitle(code);
        excelFileInfo.setSaveFileDirPath(batchFileManager.getFileFullPath());

        //자동 or 수동
        FlushControl flushControl = FlushControl.FLUSH_AUTO;
//        FlushControl flushControl = FlushControl.FLUSH_MANUAL;

        TmpTableSchCmd tmpTableSchCmd = new TmpTableSchCmd();
        tmpTableSchCmd.setLimit(true);

        TestExcelHandler testExcelHandler = new TestExcelHandler(excelFileInfo, flushControl);
        testDAOImpl.createExcelByTmpTableMap(tmpTableSchCmd, testExcelHandler);

        stopwatch.stop();
        log.info("수행시간 : {}", stopwatch.getTotalTimeSeconds());

    }

}
