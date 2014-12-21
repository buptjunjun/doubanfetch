package org.junjun.douban.mapper;

import java.util.List;

import org.junjun.douban.bean.DoubanSubject;
 public interface DoubanSubjectMapper {
    public DoubanSubject findById(long id);
    public int insertDoubanSubject(DoubanSubject ds);
    public int updateoubanSubject(DoubanSubject ds);
    public List<DoubanSubject> findDoubanSubjectByFetchCount(int fetchCount, int limit);
}
