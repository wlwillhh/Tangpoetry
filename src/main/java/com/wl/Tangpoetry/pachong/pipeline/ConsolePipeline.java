package com.wl.Tangpoetry.pachong.pipeline;

import com.sun.activation.registries.MailcapParseException;
import com.wl.Tangpoetry.pachong.common.Page;

import java.util.Map;

public class ConsolePipeline implements Pipeline{

    @Override
    public void pipeline(final Page page) {
        //实现管道方法
        Map<String ,Object>data=page.getDataSet ().getData ();
        //存储
        System.out.println (data);
    }
}
