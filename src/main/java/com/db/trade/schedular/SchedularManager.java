package com.db.trade.schedular;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.db.trade.enums.TaskName;
@Component
public class SchedularManager {
	@Autowired
	ApplicationContext applicationContext;
	Logger logger = LoggerFactory.getLogger(SchedularManager.class);
	public void schedule(TaskName taskName, String cronExpression) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(taskName.name(), "TRIGGER_GROUP");
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.forJob(taskName.name(), "TRIGGER_GROUP_NAME").build();
		scheduleJob(taskName, trigger);
	}
	private void scheduleJob(TaskName taskName, Trigger trigger) throws SchedulerException {
		Scheduler scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
		scheduler.start();
		JobKey jobKey = new JobKey(taskName.name(), "TRIGGER_GROUP_NAME");
		JobDetail job = JobBuilder.newJob(DefaultJob.class)
				.withIdentity(jobKey)
				.build();
		job.getJobDataMap().put("TRADE_STORY_TASK", applicationContext.getBean(taskName.getTaskClass()));
		job.getJobDataMap().put("TRADE_STORY_TASK_NAME", taskName);
		scheduler.scheduleJob(job,trigger);
		logger.info("Task has been scheduled successfully" + taskName.name());
	}
	public void reschedule(TaskName taskName, String cronExpression) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(taskName.name(), "TRIGGER_GROUP");
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.forJob(taskName.name(), "TRIGGER_GROUP_NAME").build();
		Scheduler scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
		scheduler.rescheduleJob(triggerKey, trigger);
	}
	public void delete(TaskName taskName) throws SchedulerException {
		Scheduler scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
		JobKey jobKey = new JobKey(taskName.name(), "TRIGGER_GROUP_NAME");
		scheduler.deleteJob(jobKey);
	}
}
