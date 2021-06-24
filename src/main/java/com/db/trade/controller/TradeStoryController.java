package com.db.trade.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.db.trade.enums.TaskName;
import com.db.trade.model.TradeStoryDto;
import com.db.trade.model.TradeStoryResponse;
import com.db.trade.schedular.SchedularManager;
import com.db.trade.service.TradeStoryAppException;
import com.db.trade.service.TradeStoryService;
import com.db.trade.utils.DateUtils;

@RestController
@RequestMapping("/tradeStory")
public class TradeStoryController {
	@Autowired
	TradeStoryService service;
	@Autowired
	SchedularManager schedularManager;

	@GetMapping("/getAllTradeStories")
	public List<TradeStoryDto> getAll() {
		return service.getAllTradeStories();
	}

	@PostMapping("/insertTradeStory")
	public ResponseEntity<TradeStoryResponse> insertTradeStory(@RequestBody TradeStoryDto dto) {
		String status = service.add(dto);
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(), status, null);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/markExpiredTrade")
	public ResponseEntity<TradeStoryResponse> markExpiredTrade(@RequestParam String date) throws ParseException {
		if (date == null)
			throw new TradeStoryAppException("Please pass date.");
		Date dateToMark = DateUtils.convertStringToDate(date);
		String status = service.markExpiredTrade(dateToMark);
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(), status, null);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/scheduleJob")
	public ResponseEntity<TradeStoryResponse> scheduleJob(@RequestParam String taskName,
			@RequestParam String cronExpression) throws SchedulerException {
		schedularManager.schedule(TaskName.valueOf(taskName), cronExpression);
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(),
				"Task has been scheduled successfully", null);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/rescheduleJob")
	public ResponseEntity<TradeStoryResponse> rescheduleJob(@RequestParam String taskName,
			@RequestParam String cronExpression) throws SchedulerException {
		schedularManager.reschedule(TaskName.valueOf(taskName), cronExpression);
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(),
				"Task has been rescheduled successfully", null);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/deleteJob")
	public ResponseEntity<TradeStoryResponse> cancelJob(@RequestParam String taskName) throws SchedulerException {
		schedularManager.delete(TaskName.valueOf(taskName));
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(),
				"Task has been deleted successfully", null);
		return ResponseEntity.ok(message);
	}
}
