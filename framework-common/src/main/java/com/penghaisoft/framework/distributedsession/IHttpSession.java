package com.penghaisoft.framework.distributedsession;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * IHttpSession 接口类
 * @author 丛琛原
 * @date 2017年8月2日
 */
public interface IHttpSession {

	/**
	 * 从cookie 中获取sessionId
	 * @return
	 */
	String getSessionIdFromHeader();

	/**
	 * 创建空session
	 * @return
	 */
	JSONObject createSession();

	String createSessionGetToken(String userId);

	/**
	 * 创建空session
	 * @return
	 */
	JSONObject createSession(String sessionId);

	/**
	 * session是否存在
	 * @param sessionId
	 * @return
	 */
	boolean existSession(String sessionId);

	/**
	 * 获取当前sessionInfo
	 * @return
	 */
	JSONObject getCurrentSession();

	/**
	 * 获取当前sessionInfo
	 * @param sessionId
	 * @return
	 */
	JSONObject getCurrentSession(String sessionId);

	/**
	 * 给session续期
	 * @param sessionId
	 */
	void extensionSession(String sessionId);

	/**
	 * 根据指定的key设置String类型的值
	 * 
	 * @param key(指定的key值),String value(想要存入的String类型的值 )
	 * @return void
	 */
	void setString(String key, String value);

	void setString(String sessionId,String key, String value);

	/**
	 * 根据指定的key设置Map类型的值
	 *
	 * @param  key(指定的key值),Map<String, String> map(想要存入的Map<String, String>类型的值)
	 * @return void
	 */
	void setMap(String key, Map<String, String> map);

	/**
	 * 根据指定的key获得String类型的值
	 *
	 * @param key(指定的key)
	 * @return String(根据指定的key值取出的String类型的对象)
	 */
	String getString(String key);

	/**
	 * 从session中获取多个参数
	 * @param key 多个
	 * @return
	 */
	List<String> getStringList(String... key);

	/**
	 * 根据指定的key删除String类型的值
	 *
	 * @param key(指定的key)
	 * @return void
	 */
	void delString(String key);

	/**
	 * 根据指定的key删除String类型的值
	 *
	 * @param key(指定的key)
	 * @return void
	 */
	void delStringList(String... key);


	/**
	 * 立即销毁HttpSession对象
	 */
	void abort();

	/**
	 * 更新session的有效时间
	 */
	JSONObject updateStringByTime(Integer sessionTime);
}