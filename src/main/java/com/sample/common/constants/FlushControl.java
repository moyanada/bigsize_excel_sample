package com.sample.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FlushControl {
    //테스트 목적으로 자동으로 생성자 flush방법과 수동으로 하는 방식을 테스트해보려고 설정
    //원하는 스타일로 정해서 하면 될듯합니다
    //임의의 값을 넣었다 중요한건 rowAccessWindowSize -1이면 수동으로 flush해줘야하고
    //수동일때 flush의 size를 정해줘야한다
    //rowAccessWindowSize가 > 0 이상이면 해당 값 이후에는 자동으로 flush해준다
    FLUSH_AUTO(100, -1)
    ,FLUSH_MANUAL(-1, 100);

    @Getter
    private final int rowAccessWindowSize;
    @Getter
    private final int manualFlushSize;
}
