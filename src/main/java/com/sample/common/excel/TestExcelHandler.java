package com.sample.common.excel;

import com.sample.batch.dto.ExcelFileInfo;
import com.sample.common.constants.FlushControl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class TestExcelHandler extends ExcelResultHandler {

    public TestExcelHandler(ExcelFileInfo excelFileInfo, FlushControl flushControl) {
        super(excelFileInfo, flushControl);
    }

    @Override
    public void createExcelBody(int rowCnt, HashMap<String, Object> dbData) {
        //워크북생성 -> 시트생성 -> 쉘 타이틀생성
        if(rowCnt % MAX_SHEET_ROW == 1) {
            createSXSSFSheet((rowCnt/MAX_SHEET_ROW)+1);
            createCellTitle(dbData);
            //새로운 시트의 ROW는 1부터 시작
            sheetRowCnt = 1;
        }
        //ROW 생성
        createDataCell(dbData);

        //...중간중간에 flush
        flushMem(rowCnt);
    }

}
