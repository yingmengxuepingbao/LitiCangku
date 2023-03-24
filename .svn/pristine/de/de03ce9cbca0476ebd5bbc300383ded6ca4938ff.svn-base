//package com.penghaisoft.framework.reportManagement.bean;
//
//import com.penghaisoft.framework.constant.Constant.ConfigInfo;
//import com.penghaisoft.framework.reportManagement.model.business.ipml.ReportManagementBusinessImpl;
//import org.apache.log4j.Logger;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPubSub;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
///**
// * 报表文件上传订阅
// * @author 秦超
// * 2017-10-09 14:29:18
// */
//public class ReportRedisSubscribe extends JedisPubSub{
//
//	private static final String CHANNEL_NAME = ConfigInfo.REDIS_REPORT_FILE_CHANNEL_NAME;
//
//	private static JedisPool jedisPool;
//
//	Logger log = Logger.getLogger(ReportRedisSubscribe.class);
//	/**
//	 * 订阅方法
//	 * @author 秦超
//	 * @Date 2017年9月16日 上午9:52:48
//	 * @param channel
//	 * @param message
//	 */
//	@Override
//	public void onMessage(String channel, String message) {
//
//		//1.1 文件上传目录绝对路径
//		String directoryPath = ReportManagementBusinessImpl.class.getClassLoader().getResource(ConfigInfo.MENU_FILE_ROOT_PATH).getPath()+ ConfigInfo.MENU_FILE_REPORT_PATH;
//		File fileDirectory = new File(directoryPath);
//
//		//1.2 上传目录不存在则创建
//		if (!fileDirectory.exists()) {
//			fileDirectory.mkdirs();
//		}
//
//		//3.保存文件
//		String reportName = message;
//		String filePath = directoryPath + reportName;
//		File uploadFile = new File(filePath);
//
//		Jedis getMessageJedis = jedisPool.getResource();
//		byte[] reportFile = getMessageJedis.get(message.getBytes());
//
//		try(FileOutputStream fos = new FileOutputStream(uploadFile)) {
//			//用FileOutputStream 的write方法写入字节数组
//			fos.write(reportFile);
//		} catch (Exception e) {
//			log.error("保存报表文件失败", e);
//		}
//		getMessageJedis.close();
//	}
//
//	/**
//	 * 给spring的Bean构造器提供的set方法,用于加载JedisPool
//	 * @author 秦超
//	 * @Date 2017年9月16日 下午4:35:24
//	 * @param jedisPool
//	 */
//	public static void setJedisPool(JedisPool jedisPool) {
//
//		Jedis subsribeJedis = jedisPool.getResource();
//
//		ReportRedisSubscribe.jedisPool = jedisPool;
//
//		//订阅redis
//		RedisSubscribeThread thread = new RedisSubscribeThread(subsribeJedis);
//	    // 使用另一个线程来执行该方法,避免调用线程卡死
//
//	    new Thread(thread).start();
//	}
//
//	/**
//	 * redis订阅子线程
//	 * @author 秦超
//	 * @Date 2017年9月12日 下午9:19:27
//	 */
//	private static class RedisSubscribeThread implements Runnable {
//
//		Jedis jedis;
//
//		public RedisSubscribeThread(Jedis jedis){
//			this.jedis = jedis;
//		}
//
//		@Override
//		public void run() {
//			//订阅指定的channel
//			jedis.subscribe(new ReportRedisSubscribe(), CHANNEL_NAME);
//		}
//	}
//}
