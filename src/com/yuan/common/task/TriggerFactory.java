package com.yuan.common.task;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class TriggerFactory {
	
	private static final String TRIGGER_NAME_PREFIX = "trigger";
	private static final String GROUP_NAME_PREFIX = "triggergroup";
	private String name;
	
	public TriggerFactory(String name){
		this.name = name;
	}
	
	private String generateTriggerName(){
		return TRIGGER_NAME_PREFIX + "_" + name + "_" + System.nanoTime();
	}
	private String generateTriggerGroupName(){
		return GROUP_NAME_PREFIX + "_" + name;
	}
	public Trigger makeSimpleTrigger(Date startTime, int repeatInterval, int repeatCount) {
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(generateTriggerName(), generateTriggerGroupName());
		triggerBuilder.startAt(startTime); // 开始时间
		
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
		scheduleBuilder.withIntervalInSeconds(repeatInterval); // 执行间隔
		scheduleBuilder.withRepeatCount(repeatCount); // 重复次数
		
		triggerBuilder.withSchedule(scheduleBuilder);
		return triggerBuilder.build();
	}
	
	/**
	 * 根据cron表达式创建触发器
	 * 0 0 10,14,16 * * ? 	每天上午10点，下午2点，4点
	 * 0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
	 * 0 0 12 ? * 	WED 表示每个星期三中午12点
	 * "0 0 12 * * ?" 每天中午12点触发 
	 * "0 15 10 ? * *" 每天上午10:15触发 
	 * "0 15 10 * * ?" 每天上午10:15触发 
	 * "0 15 10 * * ? *" 每天上午10:15触发 
	 * "0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
	 * "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
	 * "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
	 * "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
	 * "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
	 * "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
	 * "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
	 * "0 15 10 15 * ?" 每月15日上午10:15触发 
	 * "0 15 10 L * ?" 每月最后一日的上午10:15触发 
	 * "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
	 * "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
	 * "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发 
	 * @param expression cron表达式
	 * @return Trigger
	 */
	public Trigger makeCronTrigger(Date startTime, String expression){
		Trigger trigger = null;
		trigger = makeTrigger(startTime, CronScheduleBuilder.cronSchedule(expression));
        
        return trigger;
	}
	protected Trigger makeTrigger(Date startTime, ScheduleBuilder<? extends Trigger> scheduleBuilder){
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(generateTriggerName(), generateTriggerGroupName())
				.startAt(startTime) // 开始时间
				.withSchedule(scheduleBuilder)
				.build();
        
        return trigger;
	}
	
	/**
	 * 日触发器, 每天hour时minute分执行 
	 * @param hour int
	 * @param minute int
	 * @return Trigger
	 */
	public Trigger makeDailyTrigger(int hour, int minute){
		
		return makeTrigger(new Date(), CronScheduleBuilder.dailyAtHourAndMinute(hour, minute));
	}
	
	/**
	 * 每intervalInMinutes分钟执行一次
	 * @param intervalInMinutes 间隔分钟数
	 * @return Trigger
	 */
	public Trigger makeMinutelyTrigger(int intervalInMinutes){
	
		return makeTrigger(new Date(), SimpleScheduleBuilder.repeatMinutelyForever(intervalInMinutes));
	}
	
	/**
	 * 月触发器
	 * @param dayOfMonth int [1-31]
	 * @param hour int [0-23]
	 * @param minute int [0-59]
	 * @return Trigger
	 */
	public Trigger makeMonthlyTrigger(int dayOfMonth, int hour, int minute){
		
		return makeTrigger(new Date(), CronScheduleBuilder.monthlyOnDayAndHourAndMinute(dayOfMonth, hour, minute));
	}
	
	/**
	 * 周统计
	 * @param dayOfWeek int [1-7]
	 * @param hour int [0-23]
	 * @param minute int [0-59]
	 * @return Trigger
	 */
	public Trigger makeWeeklyTrigger(int dayOfWeek, int hour, int minute){
		
		return makeTrigger(new Date(), CronScheduleBuilder.weeklyOnDayAndHourAndMinute(dayOfWeek, hour, minute));
	}
	
	public static void main(String arg[]){
		String s = System.nanoTime() + "";
		System.out.println(s.length());
	}

}


