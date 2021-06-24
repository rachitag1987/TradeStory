package com.db.trade;

import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;
import com.db.trade.model.TradeStory;
import com.db.trade.model.TradeStoryDto;
import com.db.trade.model.TradeStoryId;
import com.db.trade.repo.TradeStoryRepository;
import com.db.trade.service.TradeStoryService;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TradeStoryServiceLayerTest {
	@Mock
	private TradeStoryRepository repo;
	@Mock
	private Environment env;
	@InjectMocks
	private TradeStoryService service;

	

	@Test
	public void insertTrade() {
		TradeStoryDto product = new TradeStoryDto();
		product.setBookId("B1");
		product.setCounterPartyId("CP-1");
		product.setMaturityDate(new Date());
		product.setExpired("N");
		product.setTradeId("T1");
		product.setTradeVersion(2);
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(new Date());
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		String id = "t1";
		when(repo.findMaxVersionForTrade(id)).thenReturn(null);
		when(repo.save(entity)).thenReturn(entity);
		String add = service.add(product);
		Assert.assertTrue(add.contains("Inserted"));
	}

	@Test
	public void updateTrade() {
		TradeStoryDto dto = new TradeStoryDto();
		dto.setBookId("B1");
		dto.setCounterPartyId("CP-1");
		dto.setMaturityDate(new Date());
		dto.setExpired("N");
		dto.setTradeId("T1");
		dto.setTradeVersion(1);
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(new Date());
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		when(repo.findMaxVersionForTrade(dto.getTradeId())).thenReturn(entity);
		when(repo.save(entity)).thenReturn(entity);
		String add = service.add(dto);
		Assert.assertTrue(add.contains("Updated"));
	}

	@Test
	public void getAllTradeStories() {
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(new Date());
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		TradeStory entity1 = new TradeStory();
		entity1.setBookId("B1");
		entity1.setCounterPartyId("CP-1");
		entity1.setMaturityDate(new Date());
		entity1.setExpired("N");
		entity1.setTradeStoryId(new TradeStoryId("T2", 1));
		List<TradeStory> findAll = new ArrayList<TradeStory>();
		findAll.add(entity1);
		findAll.add(entity);
		when(repo.findAll(Sort.by(Sort.Direction.ASC, "tradeStoryId"))).thenReturn(findAll);
		List<TradeStoryDto> allTradeStories = service.getAllTradeStories();
		Assert.assertTrue(allTradeStories.size() > 0);
	}

	@Test
	public void markExpiredTrade() throws ParseException {
		Date date = new Date();
		String expiredDate1 = "10/06/2021";
		Date dateexpired1 = new SimpleDateFormat("dd/MM/yyyy").parse(expiredDate1);
		String expiredDate2 = "05/06/2021";
		Date dateexpired2 = new SimpleDateFormat("dd/MM/yyyy").parse(expiredDate2);
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(dateexpired1);
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		TradeStory entity1 = new TradeStory();
		entity1.setBookId("B1");
		entity1.setCounterPartyId("CP-1");
		entity1.setMaturityDate(dateexpired2);
		entity1.setExpired("N");
		entity1.setTradeStoryId(new TradeStoryId("T2", 1));
		List<TradeStory> findAll = new ArrayList<TradeStory>();
		findAll.add(entity1);
		findAll.add(entity);
		when(repo.findAllUnExpiredTrade(date)).thenReturn(findAll);
		when(repo.saveAll(findAll)).thenReturn(null);
		when(env.getProperty("MATURITY_DATE_PASSED"))
				.thenReturn("Updated expired for all trades whose maturity date has been passed.");
		when(env.getProperty("NO_EXPIRED_TRADE_AVL"))
				.thenReturn("No trade available for which maturity date has been passed and to mark expire.");
		String markExpiredTrade = service.markExpiredTrade(date);
		System.out.println(markExpiredTrade);
	}
}
