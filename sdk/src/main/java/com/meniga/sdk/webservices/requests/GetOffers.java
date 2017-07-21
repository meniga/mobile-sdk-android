package com.meniga.sdk.webservices.requests;

import com.google.gson.annotations.SerializedName;
import com.meniga.sdk.models.offers.enums.OfferFilterState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetOffers extends QueryRequestObject {

	public int skip;
	public int take;
	@SerializedName("filter.states")
	public List<OfferFilterState> filterStates;
	@SerializedName("filter.offerIds")
	public List<Long> filterOfferIds;
	@SerializedName("filter.expiredWithCashBackOnly")
	public boolean filterExpiredWithCashBackOnly;

	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		map.put("skip", Integer.toString(this.skip));
		map.put("take", Integer.toString(this.take));

		StringBuilder sb = new StringBuilder();
		if (filterStates != null) {
			for (int i = 0; i < this.filterStates.size(); i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(this.filterStates.get(i).toString());
			}
			map.put("filter.states", sb.toString());
		}

		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < this.filterOfferIds.size(); i++) {
			if (i > 0) {
				sb2.append(",");
			}
			sb2.append(Long.toString(this.filterOfferIds.get(i)));
		}
		map.put("filter.states", sb.toString());
		map.put("filter.expiredWithCashBackOnly", Boolean.toString(filterExpiredWithCashBackOnly));
		return map;
	}

	@Override
	public long getValueHash() {
		return 0;
	}
}
