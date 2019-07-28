package com.wl.Tangpoetry.web;

import com.google.gson.Gson;
import com.wl.Tangpoetry.analyze.model.AuthorCount;
import com.wl.Tangpoetry.analyze.model.WordCount;
import com.wl.Tangpoetry.analyze.service.AnalyzeService;
import com.wl.Tangpoetry.config.ObjectFactory;
import com.wl.Tangpoetry.pachong.Pachong;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/*
Web ApI
1.Sparkjava 框架完成Web API开发
2.Servlet  技术实现Web API
3.java-Httpd 实现Web API（纯java语言实现的web服务
     Socket Http协议非常清楚


     api.js
     1.主要用于前端页面与后端的交互
         前端：HTML+CSS+JAVAscript开发的浏览器识别的Web程序
         后端：Java开发服务程序，通过HTTP协议提供 Web API接口
         前端页面与后端的交互：通信协议用的是HTTP协议
 */

public class WebController {

    private  final AnalyzeService analyzeService;


    public WebController(AnalyzeService analyzeService){
        this.analyzeService=analyzeService;
    }
    //http://127.0.0.1:4567
    //-> analyze/author_count
    private  List<AuthorCount> analyzeAuthorCounts(){
        return analyzeService.analyzeAthorCount ();
    }

    //http://127.0.0.1:4567
    //-> analyze/word_cloud
    private  List<WordCount> analyzeWordClode(){
        return analyzeService.analyzeWordCloud ();
    }


    //运行web
    public void  launch(){
        ResponseTransformer transformer=new JSONResponseTransformer ();
        //src/main/resources/static
        //前端静态文件的目录
        Spark.staticFileLocation ("/static");

       //服务端接口
        Spark.get ("/analyze/author_count",((request,response)->analyzeAuthorCounts ()),
              transformer);
        Spark.get ("/analyze/word_cloud",((request,response)->analyzeAuthorCounts ()),
                transformer);

        Spark.get ("/pachong/stop",(((request, response) -> {
            Pachong pachong=ObjectFactory.getInstance ().getObject (Pachong.class);
            pachong.stop ();
            return "爬虫停止";
        })));
    }

    public static class JSONResponseTransformer implements ResponseTransformer{
        //Object=>String
       private Gson gson =new Gson ();
        @Override
        public String render(Object o) throws Exception {
            return gson.toJson (o);
        }
    }

//    public static void main(String[] args) {
//        Gson gson =new Gson ();
//
//        WordCount wordCount=new WordCount ();
//        wordCount.setWord("java");
//        wordCount.setCount(10);
//        System.out.println (gson.toJson (wordCount));
//
//
//        //序列化
//        String str="{\"word\":\"java\",\"count\":10}";
//        WordCount wordCount1=gson.fromJson (str,WordCount.class);
//        System.out.println (wordCount1);
//    }


}
