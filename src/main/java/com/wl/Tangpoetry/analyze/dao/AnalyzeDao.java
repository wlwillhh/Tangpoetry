package com.wl.Tangpoetry.analyze.dao;

import com.wl.Tangpoetry.analyze.entity.PoetryInfo;
import com.wl.Tangpoetry.analyze.model.AuthorCount;

import java.util.List;

public interface AnalyzeDao {
    //把数据全部查出来,分析唐诗中作者的创作数量
  List<AuthorCount> analyzeAthorCount();

  //查询所有的诗文，提供给业务层进行分析
    List<PoetryInfo> queryAllPoertyInfo();

}
