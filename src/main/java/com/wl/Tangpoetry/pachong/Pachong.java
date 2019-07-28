package com.wl.Tangpoetry.pachong;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.wl.Tangpoetry.pachong.common.Page;
import com.wl.Tangpoetry.pachong.parse.DatePage;
import com.wl.Tangpoetry.pachong.parse.DocumentParse;
import com.wl.Tangpoetry.pachong.parse.Parse;
import com.wl.Tangpoetry.pachong.pipeline.ConsolePipeline;
import com.wl.Tangpoetry.pachong.pipeline.Pipeline;


import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Pachong {
//添加日志
    private  final Logger logger=  LoggerFactory.getLogger (Pachong.class);

    /*
    放置文档页（超链接）
    放置详情页面

    未被采集和解析的数据
    Page htmlPage  dataSet（未处理）
     */
    private final Queue<Page> docQueue = new LinkedBlockingDeque<> ();
     /*
        放置 详情页面(处理完成，数据在 dataSet）
      */

    private final Queue<Page> detailQueue = new LinkedBlockingDeque<> ();

    /*
    采集器
     */

    private final WebClient webClient;

    /*
    所有的解析器
     */
    private final List<Parse> parseList = new LinkedList<> ();

    /*
    所有的清洗器(管道）
     */
    private final List<Pipeline> pipelineList = new LinkedList<> ();
    /*
    线程调度器
     */
    private final ExecutorService executorService;


    public Pachong() {
        this.webClient = new WebClient (BrowserVersion.CHROME);
        this.webClient.getOptions ().setJavaScriptEnabled (false);

        //创建一个线程工厂
        this.executorService = Executors.newFixedThreadPool (8, new ThreadFactory () {

            private final AtomicInteger id = new AtomicInteger (0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread (r);
                thread.setName ("pachong-Thread-" + id.getAndIncrement ());
                return thread;
            }
        });

    }


    /*
    爬虫的启动与停止
    启动：1.爬取
         2.解析
         3.清洗
     */
    public void start() {

        this.executorService.submit (new Runnable () {
            @Override
            public void run() {
                parse ();
            }
        });

        //变成方法引用
        this.executorService.submit (this::parse);
        this.executorService.submit (this::pipeline);


    }

    private void parse() {
        while (true) {
            try {
                Thread.sleep (1000);
            } catch (InterruptedException e) {
                logger.error("解析出现异常{}",e.getMessage ());
            }

            //先取出来，如果没有问题，再调度
            final Page page = this.docQueue.poll ();
            if (page == null) {
                continue;
            }
            //出队列,先从这个队列去取，只有base,path,detail，htmlpage 属性
            this.executorService.submit (() -> {

                try {
                    //采集
                    HtmlPage htmlPage = Pachong.this.webClient.getPage (page.getUrl ());

                    //取到文档模型
                    page.setHtmlPage (htmlPage);


                    for (Parse parse : Pachong.this.parseList) {
                        parse.parse (page);
                    }

                    //如果是详情页的话
                    //取到详情页面
                    //page.getSubPage ();
                    if (page.isDetail ()) {

                        Pachong.this.detailQueue.add (page);
                    } else {//如果不是，说明它有子页面，则把它取出来加入到详情页面等待队列中
                        Iterator<Page> iterator = page.getSubPage ().iterator ();
                        while (iterator.hasNext ()) {
                            Page subPage = iterator.next ();
                            System.out.println (subPage);
                            //加到文档中
                            Pachong.this.docQueue.add (page);

                            //this.detailQueue.add ((Page) iterator.next ());
                            iterator.remove ();
                            //System.out.println (page);
                        }
                    }
                } catch (IOException e) {
                    logger.error("解析任务出现异常{}",e.getMessage ());
                }

            });
        }
    }

    //操作管道
    private void pipeline() {
        while (true) {
            try {
                Thread.sleep (1000);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            final Page page = this.detailQueue.poll ();
            if (page == null) {
                continue;
            }

            this.executorService.submit (() -> {

                for (Pipeline pipeline : Pachong.this.pipelineList) {
                    //取出配置
                    pipeline.pipeline (page);
                }

            });

        }
    }

    //添加配置
    public void addPage(Page page) {
        this.docQueue.add (page);
    }

    //添加解析器
    public void addParse(Parse parse) {
        this.parseList.add (parse);
    }

    //添加管道
    public void addPipeline(Pipeline pipeline) {
        this.parseList.add ((Parse) pipeline);
    }

    /*
    爬虫的停止
     */
    public void stop() {
        if (this.executorService != null && this.executorService.isShutdown ()) {
            this.executorService.shutdown ();

        }
        logger.info ("爬虫停止。。。");
    }
}



//    public static void main(String[] args) throws IOException {
//        //构架我的配置页面,放的我的文档页面
//        //首先把页面分类；1.文档页面2.详情页面
//       final Page page=new Page ("https://www.gushiwen.org/","contson45c396367f59",false);
//       Pachong pachong=new Pachong ();
//       pachong.addParse (new DocumentParse ());//文档解析器
//       pachong.addParse (new DatePage ());//数据解析器
//
////加一个打印管道,把配置的数据打印一下
//        pachong.addPipeline (new ConsolePipeline ());
//
//
//       pachong.addPage (page);
//       pachong.start ();
//
////        //配置的路径，只需要取得第一个配置
////        WebClient webClient=new WebClient (BrowserVersion.CHROME);
////        webClient.getOptions ().setJavaScriptEnabled (false);
////        //System.out.println (page.getUrl ());
////       HtmlPage htmlPage= webClient.getPage (page.getUrl ());
////       //再次赋给htmlPage
////        page.setHtmlPage (htmlPage);
////
////       // Queue<Page> detailPageList=new LinkedBlockingDeque<> ();
////
////
////        List<Parse> parseList=new LinkedList<> ();
////        parseList.add (new DocumentParse ());
////        parseList.add (new DatePage());
////        System.out.println(page.getSubPage(););
//////构建调度器
//////        parseList.forEach (parse -> {
//////            parse.parse (page);
//////            if(page.isDetail ()){
//////                page.getSubPage ();
//////            }
//////        });
//////        List<Pipeline> pipelineList=new LinkedList<> ();
//////        pipelineList.add (new ConsolePipeline ());
////
////
////
//////         //new一个解析器
//////        Parse parse=new DatePage ();
//////        //1.parse解析一下
//////        parse.parse (page);
//////        //new一个解析器，解析文档，解析数据
//////        //Parse parse=new DocumentParse ();
//////        //2.管道处理
//////        Pipeline pipeline=new ConsolePipeline ();
//////        pipeline.pipeline (page);


