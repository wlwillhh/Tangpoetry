package com.wl.Tangpoetry.pachong.common;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/*
存储清洗的数据
转换数据抽象为DataSet类
 */
@ToString
public class DataSet {

    /*
    data 把DOM解析，清洗之后存储的数据--就是你所要的数据
    网页DOM节点解析转换为Java对象的数据
     */

    private Map<String ,Object>  data=new HashMap<> ();

    /*
    通过指定key,从数据集合获取数据
     */
    public Object getData(String key){
       return this.data.get(key);}


   /*
   添加指定key和value到数据集合
    */
   public void putData(String key,Object value){
       this.data.put(key,value);}

       /*
       获取数据集合
        */
       public Map<String ,Object> getData(){
return new HashMap<> (this.data);}



}



