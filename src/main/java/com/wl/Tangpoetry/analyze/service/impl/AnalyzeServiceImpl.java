package com.wl.Tangpoetry.analyze.service.impl;

import com.wl.Tangpoetry.analyze.dao.AnalyzeDao;
import com.wl.Tangpoetry.analyze.entity.PoetryInfo;
import com.wl.Tangpoetry.analyze.model.AuthorCount;
import com.wl.Tangpoetry.analyze.model.WordCount;
import com.wl.Tangpoetry.analyze.service.AnalyzeService;
import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.commons.collections.ArrayStack;

import javax.xml.transform.Result;
import java.util.*;

//业务层依赖与数据查询层
public class AnalyzeServiceImpl implements AnalyzeService {
//面向接口编程
    private  final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAthorCount() {
   //此处结果并未排序
        //排序方式：
        //1.DAO层SQL排序
        //2.Service层进行数据排序

//        List<AuthorCount> authorCounts=analyzeDao.analyzeAthorCount ();
//        authorCounts.sort (Comparator.comparing (AuthorCount::getCount)) ;
//
//        return authorCounts;

        List<AuthorCount> authorCounts=analyzeDao.analyzeAthorCount ();
        //Collections.sort (authorCounts, new Comparator<AuthorCount> () {

                    authorCounts.sort(Comparator.comparing ( AuthorCount::getCount));
             //此处是升序
                return authorCounts;
            }



    @Override
    public List<WordCount> analyzeWordCloud() {
         /*
         1.查询出所有的数据
         2.取出 title content
         3.分词,过滤/w,null 空  len<2
       4.统计 k-v  k---是词，k是词频
          */
         Map<String ,Integer>map=new HashMap<> ();
   List<PoetryInfo> poetryInfos=  analyzeDao.queryAllPoertyInfo ();

   for(PoetryInfo poetryInfo:poetryInfos){
       List<Term> terms=new ArrayList<> ();
       String  title=poetryInfo.getTitle ();
       String content=poetryInfo.getContent ();

       terms.addAll (NlpAnalysis.parse(title).getTerms());
       terms.addAll (NlpAnalysis.parse(content).getTerms());


       //过滤
      Iterator<Term> iterator=terms.iterator ();
      while(((Iterator) iterator).hasNext ()){
          Term term=iterator.next ();
          //词性的过滤
          if(term.getNatureStr()==null||term.getNatureStr().equals("w")){
             iterator.remove ();
             continue;
          }
          //词的过滤
          if(term.getRealName().length()<2){
              iterator.remove ();
              continue;
          }
          //统计
          String realName=term.getRealName();
          int count=0;
          if(map.containsKey (realName)){
              count=map.get (realName)+1;
          }else{
              count=1;
          }
          //map包含整个词的名称
          map.put (realName,count);
      }

   }
        List<WordCount> wordCounts=new ArrayList<> ();
          for(Map.Entry<String,Integer> entry:map.entrySet ()){
              WordCount wordCount=new WordCount ();
              wordCount.setCount(entry.getValue ());
              wordCount.setWord(entry.getKey ());
              wordCounts.add(wordCount);
          }
        return wordCounts;//统计结束
    }

//    public static void main(String[] args) {
//        Result result=NlpAnalysis.parse("v经济法v北京发布会v军法局");
//        List<Term> terms=result.getTerms ();
//        for(Term term:terms){
//            System.out.println (term.toString);
//        }
//
//    }
}
