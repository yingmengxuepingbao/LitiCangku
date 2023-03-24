package com.penghaisoft.framework.util.ConvertFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** 
* 将对象转化为jason格式
* @author 刘立华
* @date 2017-08-25 
*/
@Slf4j
public class JsonConvert implements Convert{

	@Override
	public String convert(Object object) {
		String result = JSON.toJSONString(object);
//		log.info("JsonConvert=="+result);
		return result;
	}

	/**
	 * 对结果url编码
	 * @return
	 */
	private String genUrlEncoderResult(String result){
		String resultEncoder = null;
		try {
			resultEncoder = URLEncoder.encode(result, "UTF8");
		} catch (UnsupportedEncodingException e) {
			log.error("返回结果转码抛错",e);
		}
		//空格转义问题处理
		if(resultEncoder == null){
			return resultEncoder;
		}else{
			return resultEncoder.replaceAll("\\+", "%20");
		}
	}

} 
