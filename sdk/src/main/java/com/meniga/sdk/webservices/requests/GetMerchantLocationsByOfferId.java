package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetMerchantLocationsByOfferId extends QueryRequestObject {

	public long offerId;
	public Double latitude;
	public Double longitude;
	public Double radiusKm;
	public Integer limitLocations;

	public GetMerchantLocationsByOfferId(long offerId) {
		this.offerId = offerId;
	}

	@Override
	public Map<String, String> toQueryMap() {
		Map<String, String> query = new HashMap<>();
		if (this.latitude != null) {
			query.put("latitude", Double.toString(latitude));
		}
		if (this.longitude != null) {
			query.put("longitude", Double.toString(longitude));
		}
		if (this.radiusKm != null) {
			query.put("radiusKm", Double.toString(radiusKm));
		}
		if (this.limitLocations != null) {
			query.put("limitLocations", Integer.toString(this.limitLocations));
		}
		return query;
	}

	@Override
	public long getValueHash() {
		int result = (int) (offerId ^ (offerId >>> 32));
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (radiusKm != null ? radiusKm.hashCode() : 0);
		result = 31 * result + (limitLocations != null ? limitLocations.hashCode() : 0);
		return result;
	}
}
