package com.db.trade.schedular;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTask implements Task {
	Logger logger = LoggerFactory.getLogger(AbstractTask.class);

	public void executeTask(JobExecutionContext jobExecutionContext) {
		try {
			Long startTime = System.currentTimeMillis();
			this.execute(jobExecutionContext);
			Long endTime = System.currentTimeMillis();
			logger.info("Job execution takes- " + (endTime - startTime));
		} catch (Exception ex) {
			logger.error("Job execution Failed");
		}
	}
}
