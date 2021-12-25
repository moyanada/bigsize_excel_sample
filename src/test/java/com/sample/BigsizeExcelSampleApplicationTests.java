package com.sample;

import com.sample.common.constants.FlushControl;
import com.sample.common.util.DateUtil;
import org.junit.jupiter.api.Test;

class BigsizeExcelSampleApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DateUtil.getFormattedString("YYYY"));
        System.out.println(1/100);

        FlushControl flushControl = FlushControl.FLUSH_AUTO;
        System.out.println(flushControl.getRowAccessWindowSize());
        System.out.println(flushControl.getManualFlushSize());
    }

}
