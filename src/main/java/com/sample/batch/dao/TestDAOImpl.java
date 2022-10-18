package com.sample.batch.dao;

import com.sample.batch.dto.TmpTableDto;
import com.sample.batch.dto.TmpTableSchCmd;
import com.sample.common.excel.TestExcelHandler;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TestDAOImpl implements TestDAO {

    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public boolean createExcelByTmpTableMap(TmpTableSchCmd tmpTableSchCmd, TestExcelHandler testExcelHandler) {
        sqlSessionTemplate.select("com.sample.batch.dao.TestDAO.selectMapOfTmpTable", tmpTableSchCmd, testExcelHandler);
        testExcelHandler.createExcelByTmpFile();
        return testExcelHandler.isSuccess();
    }

    @Override
    public List<TmpTableDto> selectTmpTable(TmpTableSchCmd tmpTableSchCmd) {
        return sqlSessionTemplate.selectList("com.sample.batch.dao.TestDAO.selectTmpTableDtoOfTmpTable", tmpTableSchCmd);
    }

    @Override
    public int insertTmpTable(TmpTableDto tmpTableDto) {
        return sqlSessionTemplate.insert("com.sample.batch.dao.TestDAO.insertTmpTable001", tmpTableDto);
    }
}
