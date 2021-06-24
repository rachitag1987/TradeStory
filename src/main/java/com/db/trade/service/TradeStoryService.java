package com.db.trade.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.db.trade.model.TradeStory;
import com.db.trade.model.TradeStoryDto;
import com.db.trade.model.TradeStoryId;
import com.db.trade.repo.TradeStoryRepository;

@Service
public class TradeStoryService {
	@Autowired
	TradeStoryRepository repo;
	@Autowired
	Environment env;
	Logger logger = LoggerFactory.getLogger(TradeStoryService.class);

	@Transactional
	public String add(TradeStoryDto dto) {
		TradeStory existingTrade = repo.findMaxVersionForTrade(dto.getTradeId());
		if (existingTrade == null || (existingTrade.getTradeStoryId().getTradeVersion() < dto.getTradeVersion())) {
			// Insert new trade
			TradeStory entity = toEntity(dto);
			repo.save(entity);
			return "Successfully Inserted New Trade";
		} else if (existingTrade.getTradeStoryId().getTradeVersion() > dto.getTradeVersion()) {
			// Validation Error
			throw new TradeStoryAppException(env.getProperty("AVL_HIGHER_VERSION"));
		} else {
			// Update same Entity
			updateEntityFromDto(existingTrade, dto);
			repo.save(existingTrade);
			return "Successfully Updated Existing Trade -" + existingTrade.getTradeStoryId().getTradeId();
		}
	}

	@Transactional
	public String markExpiredTrade(Date date) {
		logger.info("Started marking expired");
		List<TradeStory> findAllUnExpiredTrade = repo.findAllUnExpiredTrade(date);
		for (TradeStory trade : findAllUnExpiredTrade) {
			trade.setExpired("Y");
		}
		repo.saveAll(findAllUnExpiredTrade);
		if (findAllUnExpiredTrade != null) {
			String tradeIds = findAllUnExpiredTrade.stream().map(e -> e.getTradeStoryId().getTradeId())
					.collect(Collectors.joining(","));
			return env.getProperty("MATURITY_DATE_PASSED") + tradeIds;
		}
		return env.getProperty("NO_EXPIRED_TRADE_AVL");
	}

	private void updateEntityFromDto(TradeStory tradeStory, TradeStoryDto dto) {
		tradeStory.setBookId(dto.getBookId());
		tradeStory.setCounterPartyId(dto.getCounterPartyId());
		tradeStory.setMaturityDate(dto.getMaturityDate());
		tradeStory.setExpired(getExpiredFlag(dto.getMaturityDate()));
	}

	private TradeStory toEntity(TradeStoryDto dto) {
		TradeStory entry = new TradeStory();
		TradeStoryId id = new TradeStoryId(dto.getTradeId(), dto.getTradeVersion());
		entry.setTradeStoryId(id);
		entry.setBookId(dto.getBookId());
		entry.setCounterPartyId(dto.getCounterPartyId());
		entry.setMaturityDate(dto.getMaturityDate());
		entry.setExpired(getExpiredFlag(dto.getMaturityDate()));
		return entry;
	}

	private String getExpiredFlag(Date maturityDate) {
		if (null != maturityDate && maturityDate.before(new Date()))
			return "Y";
		return "N";
	}

	private TradeStoryDto toDTO(TradeStory entity) {
		TradeStoryDto dto = new TradeStoryDto();
		dto.setTradeId(entity.getTradeStoryId().getTradeId());
		dto.setTradeVersion(entity.getTradeStoryId().getTradeVersion());
		dto.setBookId(entity.getBookId());
		dto.setCounterPartyId(entity.getCounterPartyId());
		dto.setMaturityDate(entity.getMaturityDate());
		dto.setExpired(entity.getExpired());
		return dto;
	}

	public List<TradeStoryDto> getAllTradeStories() {
		List<TradeStory> findAll = repo.findAll(Sort.by(Sort.Direction.ASC, "tradeStoryId"));
		if (null != findAll) {
			return findAll.stream().map(this::toDTO).collect(Collectors.toList());
		}
		return null;
	}
}
