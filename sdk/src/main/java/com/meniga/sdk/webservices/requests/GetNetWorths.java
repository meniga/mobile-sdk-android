package com.meniga.sdk.webservices.requests;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetNetWorths extends QueryRequestObject {

	public DateTime startDate;
	public DateTime endDate;
	public Boolean useInterpolation;
	public int skip;
	public int take;

	public Map<String, String> toQueryMap() {
		Map<String, String> map = new HashMap<>();
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		map.put("startDate", fmt.print(this.startDate));
		map.put("endDate", fmt.print(this.endDate));
		map.put("useInterpolation", this.useInterpolation == null ? "false" : Boolean.toString(this.useInterpolation));
		map.put("skip", Integer.toString(skip));
		map.put("take", Integer.toString(take));

		return map;
	}

	@Override
	public long getValueHash() {
		return this.startDate.getMillis() +
				this.endDate.getMillis() +
				this.skip +
				this.take +
				(this.useInterpolation == null ? 1 : this.useInterpolation ? 2 : 1);
	}
}
