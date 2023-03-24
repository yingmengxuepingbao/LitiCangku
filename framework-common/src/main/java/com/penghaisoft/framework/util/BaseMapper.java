package com.penghaisoft.framework.util;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {

  public Integer create(T t);

  public Integer delete(@Param("entity") T t);

  public Long queryCount(@Param("entity") T t);

  public List<T> queryList(@Param("page") Pager<T> page, @Param("entity") T t);

  public T queryById(Object id);

  public Integer update(T t);

  public Integer updateBySelect(@Param("entity") T t);

  public List<T> queryByAny(@Param("entity") T t);

}
