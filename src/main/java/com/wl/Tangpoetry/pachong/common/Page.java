package com.wl.Tangpoetry.pachong.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.HashSet;
import java.util.Set;


//用来放公共类
public class Page {
    /*
    数据网站的根地址
eg:http://maven.apache.org
     */


    private  String base;


    /*
    具体网页的路径
    eg:
     */
    private String path;


   /*
  文档对象模型
 */
private HtmlPage  htmlPage;

    public Page(String base, String path, HtmlPage htmlPage, boolean detail, Set<Page> subPage, DataSet dataSet) {
        this.base = base;
        this.path = path;
        this.htmlPage = htmlPage;
        this.detail = detail;
        this.subPage = subPage;
        this.dataSet = dataSet;
    }

    /*
    标识网页是否为详情页

     */
private   boolean detail;

/*
子页面对象集合
 */
private Set<Page> subPage=new HashSet<> ();



//数据对象
    private DataSet dataSet=new DataSet ();


    //待解决
    public Page(String path, String path1, boolean b) {

    }


//提取页面数据，数据对象

    public String getUrl(){

        return this.base+this.path;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    public void setHtmlPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public Set<Page> getSubPage() {
        return subPage;
    }

    public void setSubPage(Set<Page> subPage) {
        this.subPage = subPage;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public String toString() {
        return "Page{" +
                "base='" + base + '\'' +
                ", path='" + path + '\'' +
                ", htmlPage=" + htmlPage +
                ", detail=" + detail +
                ", subPage=" + subPage +
                ", dataSet=" + dataSet +
                '}';
    }

}
