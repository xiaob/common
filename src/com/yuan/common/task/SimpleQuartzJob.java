package com.yuan.common.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleQuartzJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleQuartzJob.class);

	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		 log.info(jobContext.getJobDetail().getKey().getName() + " at " 
	                + new Date() + " by " + jobContext.getTrigger().getKey().getName());
		 jobContext.getJobDetail().getJobDataMap().put("test", 100);

	}

}
