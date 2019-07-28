package com.wl.Tangpoetry.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wl.Tangpoetry.analyze.dao.AnalyzeDao;
import com.wl.Tangpoetry.analyze.dao.impl.AnalyzeDaoImpl;
import com.wl.Tangpoetry.analyze.service.AnalyzeService;
import com.wl.Tangpoetry.analyze.service.impl.AnalyzeServiceImpl;
import com.wl.Tangpoetry.pachong.Pachong;
import com.wl.Tangpoetry.pachong.common.Page;
import com.wl.Tangpoetry.pachong.parse.DatePage;
import com.wl.Tangpoetry.pachong.parse.DocumentParse;
import com.wl.Tangpoetry.pachong.pipeline.ConsolePipeline;
import com.wl.Tangpoetry.pachong.pipeline.DatabasePipeline;
import com.wl.Tangpoetry.web.WebController;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public  final class ObjectFactory {

    private  final Logger logger=LoggerFactory.getLogger (ObjectFactory.class);

    private  static  final ObjectFactory instant =new ObjectFactory ();

    /*
    存放所有的对象
     */
    private  final Map<Class, Object> ObjectHashMap
            = new HashMap<>  ();

    private ObjectFactory() {
        //1.初始化配置对象
        initConfigPropertise ();

        //2.数据源对象
        initDataSource ();

        //3.爬虫对象
        initPachong ();

        //4.web对象
         initWebController();

        //5.对象清单打印输出
        printObjectList ();

    }

    private void initWebController() {
        DataSource dataSource=getObject (DataSource.class);
        AnalyzeDao analyzeDao=new AnalyzeDaoImpl (dataSource);
        AnalyzeService analyzeService=new AnalyzeServiceImpl (analyzeDao);
        WebController webController=new WebController (analyzeService);

        ObjectHashMap.put(WebController.class,webController);



    }

    private void initPachong() {
        ConfigPropertise configPropertise= getObject (ConfigPropertise.class);
        DataSource dataSource=getObject (DataSource.class);
                final Page page=new Page (configPropertise.getPachongBase(),
                configPropertise.getPachongPath (),
                configPropertise.isPachongDetail ());



        Pachong pachong=new Pachong ();
        pachong.addParse (new DocumentParse ());//文档解析器
        pachong.addParse (new DatePage ());//数据解析器

        if(configPropertise.isEnableConsole ()){
            pachong.addPipeline (new ConsolePipeline ());
        }


        pachong.addPipeline (new DatabasePipeline (dataSource));
        //爬虫的配置
        pachong.addPage (page);
        ObjectHashMap.put (Pachong.class,pachong);

    }

    private void initDataSource() {
      ConfigPropertise configPropertise= getObject (ConfigPropertise.class);
        DruidDataSource dataSource=new DruidDataSource ();
        dataSource.setUsername (configPropertise.getDbUsername ());
        dataSource.setPassword (configPropertise.getDbPassword ());
        dataSource.setUrl (configPropertise.getDbUrl ());
        dataSource.setDriverClassName (configPropertise.getDbDriverClass ());

        ObjectHashMap.put (DataSource.class,dataSource);

    }



    private void initConfigPropertise() {

        ConfigPropertise configPropertise=new ConfigPropertise ();
        ObjectHashMap.put (ConfigPropertise.class,configPropertise);

        //打印配置文件
        logger.info ("配置属性为：\n{}",configPropertise.toString ());
    }


    public  <T> T getObject(Class classz){
        if(ObjectHashMap.containsKey (classz)){
         throw new IllegalArgumentException ("Class"+classz.getName ()+
                 "not  found Object");
        }
        return (T) ObjectHashMap.get (classz);

    }

    public static ObjectFactory getInstance() {
        return instant;
    }
    public void printObjectList(){
        logger.info ("==========ObjectFactory===========");
        for(Map.Entry<Class,Object> entry:ObjectHashMap.entrySet ()){
            logger.info (String.format ("[%-5s]==>[&s]",entry.getKey ().
                    getCanonicalName (),entry.getValue ().getClass ().getCanonicalName ()));
        }
        logger.info ("======================================");
    }




}
