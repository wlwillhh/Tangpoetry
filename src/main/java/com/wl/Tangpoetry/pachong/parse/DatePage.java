package com.wl.Tangpoetry.pachong.parse;

import com.gargoylesoftware.htmlunit.html.*;
import com.wl.Tangpoetry.pachong.common.Page;

//详情页面的解析
public class DatePage implements Parse{
    @Override
    public void parse(Page page) {
//配置的不是详情页面
        if(!page.isDetail()){
            return;
        }
       // System.out.println (page.getHtmlPage ().getBody ().asText ());
        //取它的元素,拿到它的文档
//      HtmlDivision area=( HtmlDivision) page.
//              getHtmlPage ().getElementById("contson45c396367f59");
//
//        //获取到他的内容
//        String countent=area.asText ();
//
//        //git 我的数据集合
//
//        page.getDateSet().putData("正文",countent);

         HtmlPage htmlPage=page.getHtmlPage ();
        HtmlElement body=htmlPage.getBody ();


        //如何解析数据----标题
        String titlePath="//div[@class='sons']//div[@class='cont']/h1/test()";
        DomText titleDom= (DomText) body.getByXPath (titlePath).get(0);
        // System.out.println ( titleDom.asText ());
        String title=titleDom.asText ();

        //朝代
        String dynastyPath="//div[@class='cont']/p/a[1]";
        HtmlAnchor  dynastyDom = (HtmlAnchor) body.getByXPath (dynastyPath).get(0);
        String dynasty=dynastyDom.asText ();



        //作者
        String authorPath="//div[@class='cont']/p/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath (authorPath).get(0);
        String author=authorDom.asText ();

        //正文
        String contentPath="//div[@class='cont']/div[@class='contson']";


        HtmlDivision  contentDom=(HtmlDivision)body.
                getByXPath (contentPath).get(0);
        String content=contentDom.asText ();


       //相当于解析
        page.getDataSet ().putData ("title",title);
        page.getDataSet ().putData ("dynasty",dynasty);
        page.getDataSet ().putData ("author",author);
        page.getDataSet ().putData ("content",content);

        //可以加更多的数据
        page.getDataSet ().putData ("url",page.getUrl ());

        //传入一个诗对象
      //page.getDateSet ().putData ("poetry",poetryInfo);

    }
}
