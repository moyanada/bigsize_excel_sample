package com.sample.batch.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestBatchTest {
    @Autowired
    private TestBatch testBatch;

    @Test
    void doProcessOOM_for() {
        testBatch.doProcessOOM_for();
    }
}