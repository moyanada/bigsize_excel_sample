package com.sample.common.excel;

import com.sample.batch.dto.ExcelFileInfo;
import com.sample.common.constants.FlushControl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class ExcelResultHandler implements ResultHandler {

    //딱히 아직 할께 없군요;;
    protected boolean isSuccess = true;

    protected SXSSFWorkbook sxssfWorkbook;
    protected SXSSFSheet sxssfSheet;

    // row = xls:65,535 xlsx:1,048,576
    protected final int MAX_SHEET_ROW = 1000000;

    private FlushControl flushControl;
    protected int sheetRowCnt = 1;

    protected ExcelFileInfo excelFileInfo;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ExcelResultHandler(ExcelFileInfo excelFileInfo, FlushControl flushControl) {
        this.excelFileInfo = excelFileInfo;
        this.flushControl = flushControl;
        // 0. 워크북 생성
        this.sxssfWorkbook = new SXSSFWorkbook(flushControl.getRowAccessWindowSize()); // rowAccessWindowSize개의 로우만 메모리 보유, 나머지 디스크로 내보낸다.
        sxssfWorkbook.setCompressTempFiles(true);                    // 임시파일 압축여부
    }

    /**
     * 1. 시트 생성
     */
    protected void createSXSSFSheet(int shtNum) {
        sxssfSheet = sxssfWorkbook.createSheet(shtNum+"번");
        sxssfSheet.createFreezePane(0, 1); // 첫번째줄 고정
        log.info("[Excel] 시트생성 완료");
    }

    /**
     * 2. 타이틀 셀 생성
     */
    protected void createCellTitle(HashMap<String, Object> dbData) {
        CellStyle titleStyle = createCellStyleOfTitle(sxssfWorkbook);
        StringBuffer header = new StringBuffer();
        Set<String> keys = dbData.keySet();
        for (String key : keys) {
            header.append(key);
            header.append(",");
        }
        String[] titleArray = header.toString().split(",");
        Row titleRow = sxssfSheet.createRow(0);
        for (int i = 0; i < titleArray.length; i++) {
            createCell(titleRow, i, titleStyle, titleArray[i]);
        }
        log.info("[Excel] 타이틀 셀 생성 완료");
    }

    /**
     * 셀 스타일 생성 함수
     */
    private CellStyle createCellStyleOfTitle(SXSSFWorkbook wb) {
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        font.setBold(true);
        titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * 3. 데이터 셀 생성
     */
    protected void createDataCell(HashMap<String, Object> dbData) {
        Row dataRow = sxssfSheet.createRow(sheetRowCnt);
        int cellCnt = 0;
        for (Map.Entry<String, Object> entry : dbData.entrySet()) {
            Object valueObj = entry.getValue();
            createCell(dataRow, cellCnt++, null, valueObj);
        }
        sheetRowCnt++; //시트의 row카운트 증가
    }

    /**
     * 데이터 쉘 채우기
     */
    protected void createCell(Row row, int column, CellStyle style, Object cellValueObj) {
        Cell cell = row.createCell(column);
        cell.setCellStyle(style);

        if (cellValueObj == null) {
            cell.setCellValue("");
            return;
        }

        // 셀 값의 데이터 타입에 따라 설정 분기
        if (cellValueObj instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) cellValueObj).doubleValue());
        } else {
            cell.setCellValue(cellValueObj.toString());
        }
    }

    /**
     *  rowAccessWindowSize -1이면 수동 flush작동
     */
    protected void flushMem(int rowCnt) {
        if(flushControl.getRowAccessWindowSize() == -1 && rowCnt % flushControl.getManualFlushSize() == 1) {
            try {
                ((SXSSFSheet)sxssfSheet).flushRows(flushControl.getManualFlushSize());
                log.info("[Excel] flushMem {}", rowCnt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createExcelByTmpFile() {
        try {
            log.info("[Excel] 파일 생성 시작");
            FileOutputStream fos = new FileOutputStream(new File(excelFileInfo.getSaveFileDirPath(), excelFileInfo.getTitle()+".xlsx"));
            sxssfWorkbook.write(fos);
            sxssfWorkbook.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //임시 파일 삭제
            sxssfWorkbook.dispose();
            log.info("[Excel] 파일 생성 완료");
        }
    }

    /**
     * rowCnt는 1부터 시작함
     */
    public abstract void createExcelBody(int rowCnt, HashMap<String, Object> dbData);

    @Override
    public void handleResult(ResultContext resultContext) {
        createExcelBody(resultContext.getResultCount(), (HashMap<String, Object>) resultContext.getResultObject());
    }

}
