package com.db.trade.schedular;

import org.quartz.JobExecutionContext;

public interface Task {
	void execute(JobExecutionContext jobExecutionContext);
}
