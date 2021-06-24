package com.db.trade.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class TradeStoryId implements Serializable {
	private String tradeId;
	private int tradeVersion;

	public TradeStoryId() {
		// TODO Auto-generated constructor stub
	}

	public TradeStoryId(String tradeId, int tradeVersion) {
		this.tradeId = tradeId;
		this.tradeVersion = tradeVersion;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getTradeVersion() {
		return tradeVersion;
	}

	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + tradeVersion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeStoryId other = (TradeStoryId) obj;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		if (tradeVersion != other.tradeVersion)
			return false;
		return true;
	}
}
