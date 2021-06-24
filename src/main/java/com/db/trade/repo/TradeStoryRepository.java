package com.db.trade.repo;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.db.trade.model.TradeStory;
import com.db.trade.model.TradeStoryId;

@Repository
public interface TradeStoryRepository extends JpaRepository<TradeStory, TradeStoryId> {
	@Query(value = "SELECT * FROM (SELECT * FROM TRADESTORY where TRADE_ID = ?1 order by TRADE_VERSION DESC) WHERE ROWNUM = 1", nativeQuery = true)
	TradeStory findMaxVersionForTrade(String tradeId);

	@Query(value = "SELECT * FROM TRADESTORY where MATURITY_DATE < ?1 AND EXPIRED = 'N'", nativeQuery = true)
	List<TradeStory> findAllUnExpiredTrade(Date currentDate);
}
