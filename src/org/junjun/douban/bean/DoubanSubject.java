package org.junjun.douban.bean;

import java.util.List;

public class DoubanSubject 
{	
	public static int DOUBAN_MOVIE = 0;
	public static int DOUBAN_BOOK = 1;
	
	
	private int type = DOUBAN_MOVIE; //类型
	private long id;  //豆瓣一个物品的id
	private int fetchCount = 0;//抓取次数
	private String associatedId; //与之相关的id
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public int getFetchCount() {
		return fetchCount;
	}
	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}
	public String getAssociatedId() {
		return associatedId;
	}
	public void setAssociatedId(String associatedId) {
		this.associatedId = associatedId;
	}	
	
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return String.format("%d,%d,%d,%s", type,id,fetchCount,associatedId);
    }
}
