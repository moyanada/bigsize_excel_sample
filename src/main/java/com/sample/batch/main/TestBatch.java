package com.sample.batch.main;

import com.sample.batch.dao.TestDAOImpl;
import com.sample.batch.dto.ExcelFileInfo;
import com.sample.batch.dto.TmpTableDto;
import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.constants.FlushControl;
import com.sample.common.excel.TestExcelHandler;
import com.sample.common.file.BatchFileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

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

    public void doProcessOOM() {
        StopWatch stopwatch = new StopWatch("TestBatch");
        stopwatch.start();

        TmpTableSchCmd tmpTableSchCmd = new TmpTableSchCmd();
        tmpTableSchCmd.setLimit(true);

        List<TmpTableDto> tmpTableDtos = testDAOImpl.selectTmpTable(tmpTableSchCmd);

        stopwatch.stop();
        log.info("수행시간 : {}", stopwatch.getTotalTimeSeconds());

    }

    public void doSaveData() {

        TmpTableDto tmpTableDto = TmpTableDto.builder()
                .col1("데이터를좀넣어야함")
                .col2("데이터를좀넣어야함")
                .col3("데이터를좀넣어야함")
                .col4("데이터를좀넣어야함")
                .col5("데이터를좀넣어야함")
                .col6("데이터를좀넣어야함")
                .col7("데이터를좀넣어야함")
                .col8("데이터를좀넣어야함")
                .col9("데이터를좀넣어야함")
                .col10("데이터를좀넣어야함")
                .col11("데이터를좀넣어야함")
                .col12("데이터를좀넣어야함")
                .col13("데이터를좀넣어야함")
                .col14("데이터를좀넣어야함")
                .col15("데이터를좀넣어야함").build();
        for(int i = 0; i < 2000000; i++) {
            testDAOImpl.insertTmpTable(tmpTableDto);
        }


    }

}
