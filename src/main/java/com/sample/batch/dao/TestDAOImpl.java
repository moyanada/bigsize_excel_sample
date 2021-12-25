package com.sample.batch.dao;

import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.excel.TestExcelHandler;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@RequiredArgsConstructor
@Repository
public class TestDAOImpl implements TestDAO {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public boolean createExcelByTmpTable(TmpTableSchCmd tmpTableSchCmd, TestExcelHandler testExcelHandler) {
        sqlSessionTemplate.select("com.sample.batch.dao.TestDAO.selectTmpTable001", tmpTableSchCmd, testExcelHandler);
        testExcelHandler.createExcelByTmpFile();
        return testExcelHandler.isSuccess();
    }

    @Override
    public int insertTmpTable(HashMap<String, Object> pHm) {
        return sqlSessionTemplate.insert("com.sample.batch.dao.TestDAO.insertTmpTable001", pHm);
    }
}
