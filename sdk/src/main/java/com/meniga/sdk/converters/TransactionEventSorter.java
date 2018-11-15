package com.meniga.sdk.converters;

import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.feed.MenigaTransactionEvent;
import com.meniga.sdk.models.transactions.MenigaTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 15.11.2018.
 */
public class TransactionEventSorter {

	TransactionEventSorter() {
	}

	void moveTransactionEventsToTransaction(MenigaFeed feed) {
		List<MenigaTransactionEvent> transactionEvents = new ArrayList<>();
		for (int i = 0; i < feed.size(); i++) {
			MenigaFeedItem item = feed.get(i);
			if (item instanceof MenigaTransactionEvent) {
				transactionEvents.add((MenigaTransactionEvent) item);
				feed.remove(item);
				i--;
			}
		}
		for (int i = 0; i < transactionEvents.size(); i++) {
			MenigaTransactionEvent item = transactionEvents.get(i);
			for (int x = 0; x < feed.size(); x++) {
				MenigaFeedItem feedItem = feed.get(x);
				if (feedItem instanceof MenigaTransaction && ((MenigaTransaction) feedItem).getId() == item.getTopicId()) {
					item.setDate(feedItem.getDate());
					feed.add(x + 1, item);
					x++;
				}
			}
		}
	}
}
