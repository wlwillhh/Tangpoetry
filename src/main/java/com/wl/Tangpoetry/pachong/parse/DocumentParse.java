package com.wl.Tangpoetry.pachong.parse;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.wl.Tangpoetry.pachong.common.Page;

import java.util.List;

/*
连接解析
 */
public class DocumentParse implements Parse {
    @Override
    public void parse(final Page page) {

        //如果不是文档
        if(page.isDetail ()){
            return;
        }
        //写个变量统计一下
        //final AtomicInteger count=new AtomicInteger (0);


    //取得其配置的html
        HtmlPage htmlPage=page.getHtmlPage ();

        htmlPage.getBody ().
                getElementsByAttribute
                        ("div","class",
                                "typecont").
                forEach (div ->{//取所有的div,然乎做遍历
                //节点集合
        List<HtmlElement> aNodeList= div.getHtmlElementsByTagName ("a");
         aNodeList.forEach (//每一个div下的百标签
                 aNode->{
                     //取属性（子连接）
                     String path=aNode.getAttribute ("href");
                    //count.getAndIncrement ();
                     //System.out.println (path);
                    // System.out.println (aNode.asXml ());
                     //写一个子页面
                     Page subPage=new Page (page.getPath (),path,true);
                     page.getSubPage ().add(subPage);
                 }
         );
                //打印结构
              // System.out.println (htmlElement.asXml ());

        });
       // System.out.println ("总共 "+count.get ()+"地址");
    }
}
