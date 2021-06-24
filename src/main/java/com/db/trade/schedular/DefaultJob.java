package com.db.trade.schedular;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.db.trade.enums.TaskName;

public class DefaultJob implements Job {
	TaskName taskName;
	Task task;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.task = (Task) context.getJobDetail().getJobDataMap().get("TRADE_STORY_TASK");
		this.taskName = (TaskName) context.getJobDetail().getJobDataMap().get("TRADE_STORY_TASK_NAME");
		((AbstractTask) task).executeTask(context);
	}

	public TaskName getTaskName() {
		return taskName;
	}

	public Task getTask() {
		return task;
	}
}
