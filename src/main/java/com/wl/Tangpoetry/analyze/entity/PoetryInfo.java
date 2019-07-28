package com.wl.Tangpoetry.analyze.entity;

import lombok.Data;

/*
诗,,,,,跟数据库关联的库
 */
@Data
public class PoetryInfo {
      private String title;
      private String  dynasty;
      private String  author;
      private String  content;
}
