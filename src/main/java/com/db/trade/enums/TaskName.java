package com.db.trade.enums;

import com.db.trade.schedular.MarkTradeExpiredTask;

public enum TaskName {
	MARK_TRADE_EXPIRED(MarkTradeExpiredTask.class);

	TaskName(Class<?> taskClass) {
		this.taskClass = taskClass;
	}

	Class<?> taskClass;

	public Class<?> getTaskClass() {
		return taskClass;
	}
}
