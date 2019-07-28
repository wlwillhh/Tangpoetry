package com.wl.Tangpoetry.analyze.dao.impl;

import com.wl.Tangpoetry.analyze.dao.AnalyzeDao;
import com.wl.Tangpoetry.analyze.entity.PoetryInfo;
import com.wl.Tangpoetry.analyze.model.AuthorCount;
import com.wl.Tangpoetry.pachong.pipeline.DatabasePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeDaoImpl  implements AnalyzeDao {

    private  final Logger logger=LoggerFactory.getLogger (AnalyzeDaoImpl.class);

    private final DataSource dataSource;

    //准备好数据源
    public AnalyzeDaoImpl(DataSource dataSource){
        this.dataSource=dataSource;
    }


   //转为作者
    @Override
    public List<AuthorCount> analyzeAthorCount() {
         List<AuthorCount> datas=new ArrayList<> ();

        //try()自动关闭
        String sql = "SELECT  count(*) as count,author from poetry_info group by  author;";
        try (Connection connection = dataSource.getConnection ();
             PreparedStatement statement = connection.prepareStatement (sql);
             ResultSet rs = statement.executeQuery ()
        ) {
          while(rs.next()){
              AuthorCount authorCount=new AuthorCount ();
              authorCount.setAuthor (rs.getString ("author"));
              authorCount.setCount (rs.getInt ("count"));
              datas.add (authorCount);
          }

        } catch (SQLException e) {
          logger.error ("数据库队列出现异常{}",e.getMessage ());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoertyInfo() {
        List<PoetryInfo> datas=new ArrayList<> ();
        String sql ="select  title,dynasty,author,content from poetry_info;";
        try (Connection connection = dataSource.getConnection ();
             PreparedStatement statement = connection.prepareStatement (sql);
             ResultSet rs = statement.executeQuery ()
        ) {
            while(rs.next()){
                PoetryInfo poetryInfo=new PoetryInfo ();
                poetryInfo.setTitle (rs.getString ("title"));
                poetryInfo.setDynasty (rs.getString ("Dynasty"));
                poetryInfo.setAuthor (rs.getString ("author"));
                poetryInfo.setContent (rs.getString ("content"));
             datas.add (poetryInfo);


                //ORM框架----对象关系映射
                // mybatis  Spring-Data-JDBC hibernate JOOQ TopLink   dbUtils


            }

        } catch (SQLException e) {
            logger.error ("数据库队列出现异常{}",e.getMessage ());
        }
        return datas;

    }
}
