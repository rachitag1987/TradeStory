package com.db.trade.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tradestory")
public class TradeStory extends AbstractTimestampEntity implements Serializable {
	@EmbeddedId
	private TradeStoryId tradeStoryId;
	@Column(name = "counterPartyId")
	private String counterPartyId;
	@Column(name = "bookId")
	private String bookId;
	@Column(name = "maturityDate")
	private Date maturityDate;
	@Column(name = "expired")
	private String expired;

	public TradeStoryId getTradeStoryId() {
		return tradeStoryId;
	}

	public void setTradeStoryId(TradeStoryId tradeStoryId) {
		this.tradeStoryId = tradeStoryId;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
}
