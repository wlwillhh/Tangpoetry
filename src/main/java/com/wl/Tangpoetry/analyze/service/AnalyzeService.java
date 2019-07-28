package com.wl.Tangpoetry.analyze.service;

import com.wl.Tangpoetry.analyze.model.AuthorCount;
import com.wl.Tangpoetry.analyze.model.WordCount;

import java.util.List;

public  interface AnalyzeService {
    /*
    //把数据全部查出来,分析唐诗中作者的创作数量
     */

    List<AuthorCount> analyzeAthorCount();


/*
词云分析
 */

    List<WordCount>  analyzeWordCloud();
}
