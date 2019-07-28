package com.wl.Tangpoetry;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class TestHtmlUnit {
    public static void main(String[] args) {
        //模拟一个浏览器
        try (WebClient webClient = new WebClient (BrowserVersion.CHROME)) {

            //
            webClient.getOptions ().setJavaScriptEnabled (false);

            HtmlPage htmlPage = webClient.getPage ("https://www.gushiwen.org/contsona51c59087fc8");

//           HtmlElement  bodyElement=htmlPage.getBody ();
//          String text= bodyElement.asText ();
//           System.out.println (text);
            //采集
//           DomElement domElement=htmlPage.getElementById ("");
//              //解析
//           String divContent=  domElement.asText ();
//         System.out.println (divContent);

            HtmlElement body = htmlPage.getBody ();


            //如何解析数据----标题
            String titlePath = "//div[@class='sons']//div[@class='cont']/h1/test()";
            DomText titleDom = (DomText) body.getByXPath (titlePath).get (0);
            // System.out.println ( titleDom.asText ());
            String title = titleDom.asText ();

            //朝代
            String dynastyPath = "//div[@class='cont']/p/a[1]";
            HtmlAnchor dynastyDom = (HtmlAnchor) body.getByXPath (dynastyPath).get (0);
            String dynasty = dynastyDom.asText ();


            //作者
            String authorPath = "//div[@class='cont']/p/a[2]";
            HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath (authorPath).get (0);
            String author = authorDom.asText ();

            //正文
            String contentPath = "//div[@class='cont']/div[@class='contson']";


            HtmlDivision contentDom = (HtmlDivision) body.getByXPath (contentPath).get (0);
            String conent = contentDom.asText ();


//            PoetryInfo poetryInfo = new PoetryInfo ();
//            poetryInfo.setDynasty (dynasty);
//            poetryInfo.setAuthor (author);
//            poetryInfo.setTitle (title);
//            poetryInfo.setContent (conent);


        } catch (IOException e) {
            e.printStackTrace ();
        }


    }
    }

