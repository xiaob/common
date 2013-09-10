package com.yuan.common.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
	
	public static final String JOB_NAME_PREFIX = "job";
	public static final String ANONYMOUS_JOB_NAME_PREFIX = "anonymous";
	public static final String JOB_GROUP_NAME_PREFIX = "jobgroup";
	
	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	
	private Scheduler scheduler;
	private String name;
	
	public Task(String name){
		this.name = name;
		try {
        	SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			this.scheduler = schedulerFactory.getScheduler(); //默认10个worker线程
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public Task(String name, Properties props){
		this.name = name;
        try {
        	SchedulerFactory schedulerFactory = new StdSchedulerFactory(props);
			this.scheduler = schedulerFactory.getScheduler(); //默认10个worker线程
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void start(){
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void shutdown(){
		try {
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public JobKey addTask(String jobName, Class<? extends Job> jc, Trigger trigger, Map<String, Object> jobDataMap){
		List<JobListener> listenList = new ArrayList<JobListener>();
		return this.addTask(generateJobName(jobName), jc, trigger, listenList, jobDataMap);
	}
	public JobKey addTask(Class<? extends Job> jc, Trigger trigger, Map<String, Object> jobDataMap){
		List<JobListener> listenList = new ArrayList<JobListener>();
		return this.addTask(generateAnonymousJobName(), jc, trigger, listenList, jobDataMap);
	}
	
	public JobKey addTask(Class<? extends Job> jc, Trigger trigger, JobListener jobListener, Map<String, Object> jobDataMap){
		List<JobListener> listenList = new ArrayList<JobListener>();
		listenList.add(jobListener);
		return this.addTask(generateAnonymousJobName(), jc, trigger, listenList, jobDataMap);
	}
	protected String generateAnonymousJobName(){
		return ANONYMOUS_JOB_NAME_PREFIX + "_" + name;
	}
	protected String generateJobName(String jobName){
		return JOB_NAME_PREFIX + "_" + name + "_" + jobName;
	}
	protected String generateJobGroupName(){
		return JOB_GROUP_NAME_PREFIX + "_" + name;
	}
	
	public JobKey addTask(String jobName, Class<? extends Job> jc, Trigger trigger, List<JobListener> listenList, Map<String, Object> jobDataMap){
		JobBuilder jobBuilder = JobBuilder.newJob(jc);
		jobBuilder.withIdentity(jobName, generateJobGroupName());
		if(jobDataMap != null){
			jobBuilder.usingJobData(new JobDataMap(jobDataMap));
		}
		
//		JobDetail jobDetail = jobBuilder.build();
		
//		if(AssertUtil.notEmpty(listenList)){
//			Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
//			for(JobListener jobListener : listenList){
//				try {
//					scheduler.getListenerManager().addJobListener(jobListener, matcher);
//				} catch (SchedulerException e) {
//					logger.error(e.getMessage(), e);
//				}
//				
//			}//for
//		}//if
//		
//		try {
//			scheduler.scheduleJob(jobDetail, trigger);
//		} catch (SchedulerException e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		return jobDetail.getKey();
		return null;
	}//addTask
	
	public boolean deleteTask(JobKey jobKey){
//		if(scheduler != null){
//			try {
//				scheduler.deleteJob(jobKey);
//			} catch (SchedulerException e) {
//				logger.warn(e.getMessage(), e);
//				return false;
//			}
//		}
		
		return true;
	}
	public boolean deleteTask(String jobName){
		
		return deleteTask(JobKey.jobKey(generateJobName(jobName), generateJobGroupName()));
	}
	
	public void clear(){
//		try {
//			scheduler.clear();
//		} catch (SchedulerException e) {
//			logger.warn(e.getMessage(), e);
//		}
	}
	
	public boolean checkExists(String jobName){
//		try {
//			return scheduler.checkExists(JobKey.jobKey(generateJobName(jobName), generateJobGroupName()));
//		} catch (SchedulerException e) {
//			logger.warn(e.getMessage(), e);
//		}
		
		return false;
	}
	
}

