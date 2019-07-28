package com.wl.Tangpoetry.pachong.pipeline;

import com.wl.Tangpoetry.pachong.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePipeline implements Pipeline {
    private  final Logger logger=LoggerFactory.getLogger (DatabasePipeline.class);

    private final DataSource dataSource;

    public DatabasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void pipeline(final Page page) {
       //PoetryInfo poetryInfo= (PoetryInfo) page.getDateSet ().getData ("poetry");
       // System.out.println ("存储到数据库："+poetryInfo);
      //PoetryInfo poetryInfo=new PoetryInfo ();


      String dynasty= ((String)page.getDataSet ().getData ("dynasty"));
      String author= ((String)page.getDataSet ().getData ("author"));
      String title= ((String)page.getDataSet ().getData ("title "));
      String content= ((String)page.getDataSet ().getData ("content"));

        //可以继续修改---增加
        //有了连接，准备插入
        String sql="insert into poetry_info(title, dynasty, author, content) values (?,?,?,?)";
        try( Connection connection=dataSource.getConnection ();
            PreparedStatement statement=connection.prepareStatement (sql);
            ){
            statement.setString (1,title );
            statement.setString (2,dynasty );
            statement.setString (3,author );
            statement.setString (4,content );
            //执行更新
            statement.executeUpdate ();
        } catch (SQLException e) {
           logger.error ("数据库插入出现异常{}",e.getMessage ());
        }
    }
}
