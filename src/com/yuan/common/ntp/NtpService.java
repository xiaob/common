package com.yuan.common.ntp;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.jnative.JNative;
import com.yuan.common.jnative.NativeFactory;

public class NtpService {
	
	private Logger logger = LoggerFactory.getLogger(NtpService.class);
	
	private String ip;
	private int port = 123;
	private int period = 24; //时间同步周期, 单位小时
	private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	private JNative jNative = NativeFactory.newNative();

	public NtpService(String ip, int period){
		this(ip, 123, period);
	}
	public NtpService(String ip, int port, int period){
		this.ip = ip;
		this.port = port;
		this.period = period;
	}
	
	public void start(){
		future = service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Date date = NetTool.ntp(ip, port);
				jNative.setLocalTime(date);
				logger.info("时钟同步成功. 时钟源服务器: " + ip);
			}
		}, 0, period, TimeUnit.HOURS);
	}
	
	public void shutdown(){
		if(future != null){
			future.cancel(true);
		}
		service.shutdownNow();
	}
}
