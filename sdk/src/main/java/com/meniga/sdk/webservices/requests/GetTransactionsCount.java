package com.meniga.sdk.webservices.requests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetTransactionsCount extends QueryRequestObject {

	public List<Long> accountIds = new ArrayList<>();

	@Override
	public long getValueHash() {

		Collections.sort(this.accountIds, new Comparator<Long>() {
			@Override
			public int compare(Long lhs, Long rhs) {
				if (lhs == null)
					return -1;
				else if (rhs == null)
					return 1;
				else
					return lhs.compareTo(rhs);
			}
		});

		StringBuilder bld = new StringBuilder();
		for (Long accId : this.accountIds)
			bld.append(accId == null ? 0 : accId);
		return bld.toString().hashCode();
	}
}
