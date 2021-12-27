package com.sample.batch.dao;

import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.excel.TestExcelHandler;

import java.util.HashMap;

public interface TestDAO {
    public boolean createExcelByTmpTableMap(TmpTableSchCmd tmpTableSchCmd, TestExcelHandler testExcelHandler);
    public int insertTmpTable(HashMap<String, Object> pHm);
}
