package com.sample.batch.dao;

import com.sample.batch.dto.TmpTableDto;
import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.excel.TestExcelHandler;

import java.util.List;

public interface TestDAO {
    public boolean createExcelByTmpTableMap(TmpTableSchCmd tmpTableSchCmd, TestExcelHandler testExcelHandler);
    public List<TmpTableDto> selectTmpTable(TmpTableSchCmd tmpTableSchCmd);
    public int insertTmpTable(TmpTableDto tmpTableDto);
}
