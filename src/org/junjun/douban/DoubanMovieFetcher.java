package org.junjun.douban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junjun.douban.bean.DoubanSubject;
import org.junjun.douban.mapper.DoubanSubjectMapper;
import org.junjun.douban.utils.HtmlUtls;
import org.junjun.douban.utils.MybatisUtils;

/**
 * 抓取豆瓣的movie的id
 * @author junjun
 *
 */
public class DoubanMovieFetcher 
{
	public static Pattern movieUrlPattern = Pattern.compile("http://movie.douban.com/subject/([0-9]+)/\\?.*");
	
	public static void main(String [] args)
	{
		DoubanSubject ds = new DoubanSubject();
//		ds.setId(10748092);
//		ds.setFetchCount(0);
		DoubanMovieFetcher dmf = new DoubanMovieFetcher();
//		dmf.fetchAndSave(ds);
		SqlSessionFactory fc = MybatisUtils.getSessionFactory();
		SqlSession sqlSession = fc.openSession();
		DoubanSubjectMapper dsMapper = sqlSession.getMapper(DoubanSubjectMapper.class);	
		List<DoubanSubject> dses = dsMapper.findDoubanSubjectByFetchCount(0, 1);
		sqlSession.commit();
		sqlSession.close();
		
		while(dses!=null && dses.size() !=0)
		{
			for(DoubanSubject ds_ : dses)
			{
				dmf.fetchAndSave(ds_);
				
				try 
				{	
					int sleepInterval = 30+Math.abs(new Random().nextInt(30));
					TimeUnit.SECONDS.sleep(sleepInterval);	
					System.out.println("sleep " + sleepInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			sqlSession = fc.openSession();
			dsMapper = sqlSession.getMapper(DoubanSubjectMapper.class);	
			dses = dsMapper.findDoubanSubjectByFetchCount(0, 1);
			sqlSession.commit();
			sqlSession.close();
			System.out.println();
		}
	
	}
	
	
	private DoubanSubject fetchAndSave(DoubanSubject ds)
	{
		System.out.println("deal with"+ds);
		String url = String.format("http://movie.douban.com/subject/%d/?from=showing", ds.getId());
		Document doc;
		try {
			
			 String html = HtmlUtls.getHtml(url);
			doc = Jsoup.parse(html);//Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36").ignoreContentType(true).timeout(30000).get();
			Elements links = doc.select("a[href]");	
			
			int linkcount = 0;
			if(links != null)
				linkcount = links.size();
			String associatedIds="";
			List<DoubanSubject> dses = new ArrayList<>();
			System.out.println("fetch and get "+linkcount+" url form "+ url);
			String seperator = "";
			for(Element element : links)
			{
				String href = element.attr("href");
				Matcher m = movieUrlPattern.matcher(href);
				if(m.matches())
				{			
					try
					{
						String id= m.group(1);
						DoubanSubject ds_ = new DoubanSubject();
						associatedIds+= seperator+id;
						ds_.setId(Long.parseLong(id));
						ds_.setFetchCount(0);
						ds_.setAssociatedId("");
						dses.add(ds_);
						
						seperator = ",";	
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
			}
			
			ds.setAssociatedId(associatedIds);		
			
			SqlSessionFactory fc = MybatisUtils.getSessionFactory();
			SqlSession sqlSession = fc.openSession();
			DoubanSubjectMapper dsMapper = sqlSession.getMapper(DoubanSubjectMapper.class);	
			
			DoubanSubject dscheck = dsMapper.findById(ds.getId());
			if(dscheck == null)
				dsMapper.insertDoubanSubject(ds);
			else 
			{
				ds.setFetchCount(ds.getFetchCount()+1);
				dsMapper.updateoubanSubject(ds);  
				
			}
			 sqlSession.commit();
			 
			
			 
			 for(DoubanSubject ds_:dses)
			 {
				 DoubanSubject dscheck_ = dsMapper.findById(ds_.getId());
				 if(dscheck_ == null)
				 {
					 try{
						 dsMapper.insertDoubanSubject(ds_);
						 sqlSession.commit();
					 }
					 catch(Exception e)
					 {
						 e.printStackTrace();
					 }
				 }
			 }
			 
			 sqlSession.commit();
			 sqlSession.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		
		
		return ds;
	}
    
}
