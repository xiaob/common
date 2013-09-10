package com.yuan.common.util;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.concurrent.FutureCallback;

public class AsyncHttp {
	
	private ExecutorService executorService;
	
	public AsyncHttp(){
		executorService = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors()*2);
	}
	
	public void doPost(final String url, final String body,
			final FutureCallback<String> callback) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					String result = HttpUtil.doPost(url, "application/json", body, "UTF-8");
					callback.completed(result);
				} catch (IOException e) {
					callback.failed(e);
				}
			}
		});
	}

}
