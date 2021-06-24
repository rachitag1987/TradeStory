package com.db.trade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.db.trade.model.TradeStory;
import com.db.trade.model.TradeStoryId;
import com.db.trade.repo.TradeStoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TradeStoryRepoTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private TradeStoryRepository repo;

	@Test
	public void should_find_no_trade_if_repository_is_empty() {
		List<TradeStory> findAll = repo.findAll();
		Assert.assertTrue(findAll.size() == 0);
	}

	@Test
	public void saveTradeStory() {
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(new Date());
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		repo.save(entity);
		Optional<TradeStory> findById = repo.findById(entity.getTradeStoryId());
		Assert.assertTrue(findById.isPresent());
	}

	@Test
	public void findMaxVersionForTrade() {
		TradeStory entity = new TradeStory();
		entity.setBookId("B1");
		entity.setCounterPartyId("CP-1");
		entity.setMaturityDate(new Date());
		entity.setExpired("N");
		entity.setTradeStoryId(new TradeStoryId("T1", 1));
		repo.save(entity);
		TradeStory entity1 = new TradeStory();
		entity1.setBookId("B1");
		entity1.setCounterPartyId("CP-1");
		entity1.setMaturityDate(new Date());
		entity1.setExpired("N");
		entity1.setTradeStoryId(new TradeStoryId("T1", 2));
		repo.save(entity1);
		TradeStory findMaxVersionForTrade = repo.findMaxVersionForTrade("T1");
		Assert.assertTrue(findMaxVersionForTrade.getTradeStoryId().getTradeVersion() == 2);
	}

	@Test
	public void findAllUnExpiredTrade() throws ParseException {
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
		repo.save(entity);
		TradeStory entity1 = new TradeStory();
		entity1.setBookId("B1");
		entity1.setCounterPartyId("CP-1");
		entity1.setMaturityDate(dateexpired2);
		entity1.setExpired("Y");
		entity1.setTradeStoryId(new TradeStoryId("T2", 1));
		repo.save(entity1);
		List<TradeStory> findAllUnExpiredTrade = repo.findAllUnExpiredTrade(date);
		Assert.assertTrue(findAllUnExpiredTrade.size() > 0);
	}
}
