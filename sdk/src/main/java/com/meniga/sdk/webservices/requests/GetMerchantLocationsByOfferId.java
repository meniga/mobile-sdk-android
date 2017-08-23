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
		return this.hashCode();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GetMerchantLocationsByOfferId that = (GetMerchantLocationsByOfferId) o;

		if (offerId != that.offerId) return false;
		if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
			return false;
		if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
			return false;
		if (radiusKm != null ? !radiusKm.equals(that.radiusKm) : that.radiusKm != null)
			return false;
		return limitLocations != null ? limitLocations.equals(that.limitLocations) : that.limitLocations == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (offerId ^ (offerId >>> 32));
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (radiusKm != null ? radiusKm.hashCode() : 0);
		result = 31 * result + (limitLocations != null ? limitLocations.hashCode() : 0);
		return result;
	}
}
