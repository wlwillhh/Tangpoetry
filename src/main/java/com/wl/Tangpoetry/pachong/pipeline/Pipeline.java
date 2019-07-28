package com.wl.Tangpoetry.pachong.pipeline;

import com.wl.Tangpoetry.pachong.common.Page;
//管道，用来处理配置中的数据
public interface Pipeline {
    void pipeline(final Page page);
}
