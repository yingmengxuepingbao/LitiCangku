package com.penghaisoft.framework.distributedsession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.authorization.constant.SecurityManagerConstant;
import com.penghaisoft.framework.distributedsession.util.DistributeSessionUtil;
import com.penghaisoft.framework.distributedsession.util.SessionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpSession类——封装了对HttpSession对象的创建、重新构建以及对redis进行操作的方法
 */
@Service
@Slf4j
public class HttpSession implements IHttpSession {

	private static final String SESSION_PREFIX = "session:";

	private static final String HEADER_SESSION_KEY = "token";
//	过期时间，默认20分钟
	@Value("${framework.expiredTime:20}")
	private Integer expiredTime;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 将session信息写入redis
	 * 并重新设置失效时间
	 * @param sessionInfo
	 * @return
	 */
	private JSONObject updateSession2Redis(String sessionId,JSONObject sessionInfo){
		if (sessionId==null){
			sessionId = getSessionIdFromHeader();
		}
		if (sessionId!=null){
			String sessionKey = SESSION_PREFIX + sessionId;
			redisTemplate.opsForValue().set(sessionKey,sessionInfo.toJSONString());
			redisTemplate.expire(sessionKey,expiredTime,TimeUnit.MINUTES);
			return sessionInfo;
		}else {
			return null;
		}
	}

	/**
	 * 将session信息存入redis
	 * @param sessionId
	 * @return
	 */
	private JSONObject saveSession2Redis(String sessionId){
		JSONObject sessionInfo = new JSONObject();
		saveSession2Redis(sessionId,sessionInfo);
		return sessionInfo;
	}

	private JSONObject saveSession2Redis(String sessionId,JSONObject sessionInfo){
		String sessionKey = SESSION_PREFIX + sessionId;
//        放入创建时间
		sessionInfo.put(SessionConstant.HSET_FIRST_KEY, DistributeSessionUtil.simpleDateFormat.format(new Date()));
		redisTemplate.opsForValue().set(sessionKey,sessionInfo.toJSONString());
		redisTemplate.expire(sessionKey,expiredTime, TimeUnit.MINUTES);

		return sessionInfo;
	}

	/**
	 * 从header中获取sessionId
	 * @return
	 */
	public String getSessionIdFromHeader(){
		String sessionId = request.getHeader(HEADER_SESSION_KEY);
		return sessionId;
	}

	/**
	 * 创建HttpSession对象,SessionId为随机生成
	 *
	 * @return HttpSession(根据随机生成的sessionId创建的HttpSession对象)
	 */
	public JSONObject createSession() {
		String sessionId = DistributeSessionUtil.getRandomSessionId(SessionConstant.SESSIONID_LEN);
		return saveSession2Redis(sessionId);
	}

	@Override
	public String createSessionGetToken(String userId) {
		String sessionId = DistributeSessionUtil.getRandomSessionId(SessionConstant.SESSIONID_LEN);
		JSONObject sessionInfo = new JSONObject();
		sessionInfo.put(SecurityManagerConstant.USER_ID,userId);
		saveSession2Redis(sessionId,sessionInfo);
		return sessionId;
	}

	public JSONObject createSession(String sessionId) {
		return saveSession2Redis(sessionId);
	}

	@Override
	public boolean existSession(String sessionId) {
		return redisTemplate.hasKey(SESSION_PREFIX + sessionId);
	}

	@Override
	public JSONObject getCurrentSession(String sessionId) {
		if (existSession(sessionId)){
			String sessionInfoStr = redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId);
			JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
//			自动续期
			extensionSession(sessionId);
			return sessionInfo;
		}else {
			return null;
		}
	}

	@Override
	public void extensionSession(String sessionId) {
		redisTemplate.expire(SESSION_PREFIX + sessionId,expiredTime, TimeUnit.MINUTES);
	}

	/**
	 * 根据指定的key设置String类型的值
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	@Override
	public void setString(String key, String value) {
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null){
			sessionInfo.put(key,value);
			updateSession2Redis(null,sessionInfo);
		}
	}

	@Override
	public void setString(String sessionId, String key, String value) {
		JSONObject sessionInfo = getCurrentSession(sessionId);
		if (sessionInfo!=null){
			sessionInfo.put(key,value);
			updateSession2Redis(sessionId,sessionInfo);
		}
	}

	/**
	 * 根据指定的key设置Map类型的值
	 *
	 * @param key
	 * @param map
	 * @return void
	 */
	@Override
	public void setMap(String key, Map<String, String> map) {
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null){
			sessionInfo.put(key,map);
			updateSession2Redis(null,sessionInfo);
		}
	}

	/**
	 * 根据指定的key获得String类型的值
	 *
	 * @param key@return String(根据指定的key值取出的String类型的对象)
	 */
	@Override
	public String getString(String key) {
		String val = null;
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null && sessionInfo.containsKey(key)){
			val = sessionInfo.getString(key);
		}
		return val;
	}

	/**
	 * 获取多个参数
	 *
	 * @param key
	 * @return
	 */
	@Override
	public List<String> getStringList(String... key) {
		List<String> result = new ArrayList<>();
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null){
			for (String k:key) {
				result.add(sessionInfo.getString(k));
			}
		}
		return result;
	}

	/**
	 * 根据指定的key删除String类型的值
	 *
	 * @param key@return void
	 */
	@Override
	public void delString(String key) {
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null && sessionInfo.containsKey(key)){
			sessionInfo.remove(key);
			updateSession2Redis(null,sessionInfo);
		}
	}

	/**
	 * 根据指定的key删除String类型的值
	 *
	 * @param key@return void
	 */
	@Override
	public void delStringList(String... key) {
		JSONObject sessionInfo = getCurrentSession();
		if (sessionInfo!=null){
			for (String k:key) {
				sessionInfo.remove(k);
				updateSession2Redis(null,sessionInfo);
			}
		}
	}

	@Override
	public JSONObject getCurrentSession() {
		String sessionId = getSessionIdFromHeader();
		if (sessionId!=null){
			return getCurrentSession(sessionId);
		}
		return null;
	}

	/**
	 * 立即销毁HttpSession对象
	 */
	@Override
	public void abort(){
		String sessionId = getSessionIdFromHeader();
		if (sessionId!=null){
			redisTemplate.delete(SESSION_PREFIX + sessionId);
		}
	}
	/**
	 * 更新session的有效时间
	 */
	@Override
	public JSONObject updateStringByTime(Integer sessionTime){
		//获取sessionId
		String sessionId = request.getHeader(HEADER_SESSION_KEY);
		JSONObject sessionInfo;
		//判断sessionId是否存在
		if(existSession(sessionId)){
			sessionId = SESSION_PREFIX +sessionId;
			String sessionInfoStr = redisTemplate.opsForValue().get(sessionId);
			sessionInfo = JSON.parseObject(sessionInfoStr);
			sessionInfo.put(SessionConstant.HSET_FIRST_KEY, DistributeSessionUtil.simpleDateFormat.format(new Date()));
			redisTemplate.delete(sessionId);
			//如果存在，就更新session的有效时间
			redisTemplate.opsForValue().set(sessionId,sessionInfo.toJSONString(),sessionTime,TimeUnit.MINUTES);
			//redisTemplate.expire(sessionId,sessionTime,TimeUnit.MINUTES);
		}else{
			//如果不存在，创建
			sessionId= SESSION_PREFIX + sessionId;
			sessionInfo =new JSONObject();
			sessionInfo.put(SessionConstant.HSET_FIRST_KEY, DistributeSessionUtil.simpleDateFormat.format(new Date()));
			redisTemplate.opsForValue().set(sessionId,sessionInfo.toJSONString(),sessionTime,TimeUnit.MINUTES);
		}
		return sessionInfo;
	}

}
