package com.yuan.common.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuan.common.util.AssertUtil;

public class Task {

	private static final String ANONYMOUS_JOB_NAME_PREFIX = "anonymous";
	private static final String ANONYMOUS_JOB_GROUP_PREFIX = "anonymousgroup";

	private String JOB_NAME_PREFIX = "job";
	private String JOB_GROUP_NAME_PREFIX = "jobgroup";

	private static final Logger logger = LoggerFactory.getLogger(Task.class);

	private Scheduler scheduler;

	public Task(String name) {
		JOB_NAME_PREFIX += "_" + name;
		JOB_GROUP_NAME_PREFIX += "_" + name;
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			this.scheduler = schedulerFactory.getScheduler(); // 默认10个worker线程
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Task(String name, Properties props) {
		JOB_NAME_PREFIX += "_" + name;
		JOB_GROUP_NAME_PREFIX += "_" + name;
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory(props);
			this.scheduler = schedulerFactory.getScheduler(); // 默认10个worker线程
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void start() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void shutdown() {
		try {
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public JobKey addTask(String group, String jobName, Class<? extends Job> jc, Trigger trigger,
			Map<String, Object> jobDataMap) {
		List<JobListener> listenList = new ArrayList<JobListener>();
		return this.addTask(generateJobGroupName(group), generateJobName(jobName), jc, trigger, listenList, jobDataMap);
	}

	public JobKey addTask(Class<? extends Job> jc, Trigger trigger, Map<String, Object> jobDataMap) {
		List<JobListener> listenList = new ArrayList<JobListener>();
		return this.addTask(generateAnonymousJobGroup(), generateAnonymousJobName(), jc, trigger, listenList,
				jobDataMap);
	}

	public JobKey addTask(Class<? extends Job> jc, Trigger trigger, JobListener jobListener,
			Map<String, Object> jobDataMap) {
		List<JobListener> listenList = new ArrayList<JobListener>();
		listenList.add(jobListener);
		return this.addTask(generateAnonymousJobGroup(), generateAnonymousJobName(), jc, trigger, listenList,
				jobDataMap);
	}

	protected String generateAnonymousJobName() {
		return ANONYMOUS_JOB_NAME_PREFIX + "_" + System.currentTimeMillis();
	}

	protected String generateAnonymousJobGroup() {
		return ANONYMOUS_JOB_GROUP_PREFIX;
	}

	protected String generateJobName(String jobName) {
		return JOB_NAME_PREFIX + "_" + jobName + "_" + System.currentTimeMillis();
	}

	protected String generateJobGroupName(String group) {
		return JOB_GROUP_NAME_PREFIX + "_" + group;
	}

	public JobKey addTask(String group, String jobName, Class<? extends Job> jc, Trigger trigger,
			List<JobListener> listenList, Map<String, Object> jobDataMap) {
		JobBuilder jobBuilder = JobBuilder.newJob(jc);
		jobBuilder.withIdentity(jobName, group);
		if (jobDataMap != null) {
			jobBuilder.usingJobData(new JobDataMap(jobDataMap));
		}

		JobDetail jobDetail = jobBuilder.build();

		if (AssertUtil.notEmpty(listenList)) {
			KeyMatcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
			for (JobListener jobListener : listenList) {
				try {
					scheduler.getListenerManager().addJobListener(jobListener, matcher);
				} catch (SchedulerException e) {
					logger.error(e.getMessage(), e);
				}

			}// for
		}// if

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

		return jobDetail.getKey();
	}// addTask

	public boolean deleteTask(JobKey jobKey) {
		if (scheduler != null) {
			try {
				List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggerList) {
					scheduler.pauseTrigger(trigger.getKey()); // 停止触发器
					scheduler.unscheduleJob(trigger.getKey()); // 移除触发器
				}

				scheduler.deleteJob(jobKey); // 删除任务
			} catch (SchedulerException e) {
				logger.warn(e.getMessage(), e);
				return false;
			}
		}

		return true;
	}

	public boolean deleteTask(String group, String jobName) {

		return deleteTask(getJobKey(group, jobName));
	}

	// 暂停任务
	public boolean pauseJob(JobKey jobKey) {
		if (scheduler != null) {
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
	public boolean resumeJob(JobKey jobKey) {
		if (scheduler != null) {
			try {
				scheduler.resumeJob(jobKey);
			} catch (SchedulerException e) {
				logger.warn(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}

	private JobKey getJobKey(String group, String jobName) {
		return JobKey.jobKey(generateJobName(jobName), generateJobGroupName(group));
	}

	public void clear() {
		try {
			scheduler.clear();
		} catch (SchedulerException e) {
			logger.warn(e.getMessage(), e);
		}
	}

	public boolean checkExists(String group, String jobName) {
		try {
			return scheduler.checkExists(getJobKey(group, jobName));
		} catch (SchedulerException e) {
			logger.warn(e.getMessage(), e);
		}

		return false;
	}

	// 暂停任务
	public void pauseTrigger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(new TriggerKey(triggerName, group));// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 恢复任务
	public void resumeTrigger(String triggerName, String group) {
		try {
			scheduler.resumeTrigger(new TriggerKey(triggerName, group));// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	// 移除任务
	public boolean removeTrigdger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(new TriggerKey(triggerName, group));// 停止触发器
			return scheduler.unscheduleJob(new TriggerKey(triggerName, group));// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取该组的所有JOB
	 * 
	 * @param group
	 * @return
	 * @throws SchedulerException
	 */
	public Set<JobDetail> getJobDetails(String group) throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupEquals(generateJobGroupName(group));
		Set<JobKey> keySet = scheduler.getJobKeys(matcher);

		Set<JobDetail> jobDetailSet = new HashSet<JobDetail>();
		for (JobKey jobKey : keySet) {
			jobDetailSet.add(scheduler.getJobDetail(jobKey));
		}

		return jobDetailSet;
	}
	
	/**
	 * 获取个任务关联的所有触发器
	 * @param jobKey
	 * @return
	 * @throws SchedulerException
	 */
	public List<? extends Trigger> getTriggers(JobKey jobKey) throws SchedulerException{
		return scheduler.getTriggersOfJob(jobKey);
	}

}
