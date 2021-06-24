package com.db.trade.schedular;

import java.text.ParseException;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.db.trade.service.TradeStoryService;
import com.db.trade.utils.DateUtils;

/**
 * @author Rachit
 *  This task need to be scheduled daily @ 12:01 am
 */
@Component
public class MarkTradeExpiredTask extends AbstractTask {
	Logger logger = LoggerFactory.getLogger(MarkTradeExpiredTask.class);
	@Autowired
	TradeStoryService service;
	
	@Autowired
	Environment env;
	
    
	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		logger.info("---Started Job task - MarkTradeExpiredTask------");
		Date expireddate = new Date();
		String date = env.getProperty("mark.expiry.date");
		if(null != date && !date.isEmpty())
		try {
			expireddate = DateUtils.convertStringToDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		service.markExpiredTrade(expireddate);
	}
}
