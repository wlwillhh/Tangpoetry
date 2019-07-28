package com.wl.Tangpoetry;



import com.wl.Tangpoetry.config.ObjectFactory;
import com.wl.Tangpoetry.pachong.Pachong;
import com.wl.Tangpoetry.web.WebController;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import  static  spark.route.HttpMethod.get;

import javax.sql.DataSource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.logging.Logger;

//唐诗分析的之类
public class Tangpoetry {
    //增加日志
    private  static  final Logger LOGGER= (Logger) LoggerFactory.getLogger ((Tangpoetry.class));


    public static void main(String[] args) {


        WebController webController=ObjectFactory.getInstance ().getObject (WebController.class);
        //运行了web服务，提供了接口
        LOGGER.info ("Web server  yunxing");
webController.launch ();

      //启动爬虫
        if(args.length==1 && args[0].equals ("run-pachong")){
            Pachong pachong=ObjectFactory.getInstance().getObject(Pachong.class);
            LOGGER.info ("Pachong start");
            pachong.start ();
        }




       // Spark.get ("/hello",(req,resq)->{
//
//            return "Hello world Pachong"+LocalDateTime.now().toString();
//
    }
}




















       // ConfigPropertise configPropertise=new ConfigPropertise ();

        //构架我的配置页面,放的我的文档页面
        //首先把页面分类；1.文档页面2.详情页面
//        final Page page=new Page (configPropertise.getPachongBase(),
//                configPropertise.getPachongPath (),
//                configPropertise.isPachongDetail ());
//
//
//
//        Pachong pachong=new Pachong ();
//        pachong.addParse (new DocumentParse ());//文档解析器
//        pachong.addParse (new DatePage ());//数据解析器

//加一个打印管道,把配置的数据打印一下
      // pachong.addPipeline (new ConsolePipeline ());

//        //给一个数据源
//        DruidDataSource dataSource=new DruidDataSource ();
//    dataSource.setUsername (configPropertise.getDbUsername ());
//    dataSource.setPassword (configPropertise.getDbPassword ());
//    dataSource.setDriverClassName (configPropertise.getDbDriverClass ());
//    dataSource.setUrl (configPropertise.getDbUrl ());
//
////        pachong.addPipeline (new DatabasePipeline (dataSource));
////
////
////        pachong.addPage (page);
////        pachong.start ();
//
//        AnalyzeDao analyzeDao=new AnalyzeDaoImpl (dataSource);
////        System.out.println ("测试1");
////        analyzeDao.analyzeAthorCount ().forEach
////                (System.out::println );
////
////        System.out.println ("测试2");
////        analyzeDao.queryAllPoertyInfo ().forEach (System.out::println);
//
//
//        AnalyzeService analyzeService=new AnalyzeServiceImpl (analyzeDao);
//        analyzeService.analyzeWordCloud ().forEach (System.out::println );

