package com.yuan.common.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.AssertUtil;

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
		
		JobDetail jobDetail = jobBuilder.build();
		
		if(AssertUtil.notEmpty(listenList)){
			KeyMatcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
			for(JobListener jobListener : listenList){
				try {
					scheduler.getListenerManager().addJobListener(jobListener, matcher);
				} catch (SchedulerException e) {
					logger.error(e.getMessage(), e);
				}
				
			}//for
		}//if
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
		
		return jobDetail.getKey();
	}//addTask
	
	public boolean deleteTask(JobKey jobKey){
		if(scheduler != null){
			try {
				pauseJob(jobKey);
				scheduler.deleteJob(jobKey);
			} catch (SchedulerException e) {
				logger.warn(e.getMessage(), e);
				return false;
			}
		}
		
		return true;
	}
	public boolean deleteTask(String jobName){
		
		return deleteTask(getJobKey(jobName));
	}
	
	// 暂停任务
	public boolean pauseJob(JobKey jobKey){
		if(scheduler != null){ 
			try {
				scheduler.pauseJob(jobKey);
			} catch (SchedulerException e) {
				logger.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	
	// 恢复任务
	public boolean resumeJob(JobKey jobKey){
		if(scheduler != null){
			try {
				scheduler.resumeJob(jobKey);
			} catch (SchedulerException e) {
				logger.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	
	private JobKey getJobKey(String jobName){
		return JobKey.jobKey(generateJobName(jobName), generateJobGroupName());
	}
	
	public void clear(){
		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public boolean checkExists(String jobName){
		try {
			return scheduler.checkExists(getJobKey(jobName));
		} catch (SchedulerException e) {
			logger.warn(e.getMessage(), e);
		}
		
		return false;
	}
	
	// 暂停任务
    public void pauseTrigger(String triggerName,String group){        
        try {  
            scheduler.pauseTrigger(new TriggerKey(triggerName, group));//停止触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    // 恢复任务
    public void resumeTrigger(String triggerName,String group){       
        try {  
            scheduler.resumeTrigger(new TriggerKey(triggerName, group));//重启触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    // 移除任务
    public boolean removeTrigdger(String triggerName,String group){       
        try {  
            scheduler.pauseTrigger(new TriggerKey(triggerName, group));//停止触发器  
            return scheduler.unscheduleJob(new TriggerKey(triggerName, group));//移除触发器  
        } catch (SchedulerException e) {  
            throw new RuntimeException(e);  
        } 
    }
	
}

