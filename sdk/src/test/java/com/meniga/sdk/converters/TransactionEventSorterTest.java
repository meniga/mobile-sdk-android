package com.meniga.sdk.converters;

import com.meniga.sdk.models.feed.MenigaFeed;
import com.meniga.sdk.models.feed.MenigaTransactionEvent;
import com.meniga.sdk.models.transactions.MenigaTransaction;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 15.11.2018.
 */
@RunWith(Parameterized.class)
public class TransactionEventSorterTest {

	private final MenigaFeed feed;
	private final int expectedIndex;
	private final DateTime expectedDate;
	private final boolean verifyDate;

	@Parameterized.Parameters(name = "{index}: event date: {0}, transaction date: {1}, event insert index: {2}, topicId: {3}, transaction id {4}, verify date: {5} expected index: {6}, expected date: {7}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"2018-07-10T00:00:00.010", "2018-07-11T00:00:00.010", 99, 100, 15, false, 0, "2018-07-10T00:00:00.010"},

				{"2018-07-11T00:00:00.010", "2018-07-01T00:00:00.010", 100, 100, 5, true, 1, "2018-07-01T00:00:00.010"},
				{"2017-07-11T00:12:00.010", "2018-07-02T00:12:00.010", 100, 100, 1, true, 1, "2018-07-02T00:12:00.010"},
				{"2009-07-03T22:12:00.010", "2009-07-03T22:12:00.010", 100, 100, 15, true, 1, "2009-07-03T22:12:00.010"},
				{"2018-07-11T00:00:00.010", "2018-07-01T00:00:00.010", 100, 100, 1, true, 1, "2018-07-01T00:00:00.010"},
		});
	}

	public TransactionEventSorterTest(String eventDate, String transactionDate, long topicId, long transactionId, int insertIndex, boolean verifyDate, int expectedIndex, String expectedDate) {
		feed = new MenigaFeed();
		long id = transactionId;
		DateTime date = new DateTime(transactionDate);
		for (int i = 0; i < 20; i++) {
			MenigaTransaction transaction = mock(MenigaTransaction.class);
			given(transaction.getId()).willReturn(id);
			given(transaction.getDate()).willReturn(date);
			feed.add(transaction);
			date = date.plusDays(1);
			id++;
		}

		MenigaTransactionEvent event = mock(MenigaTransactionEvent.class);
		given(event.getDate()).willReturn(new DateTime(eventDate));
		given(event.getTopicId()).willReturn(topicId);
		feed.add(insertIndex, event);
		this.verifyDate = verifyDate;
		this.expectedIndex = expectedIndex;
		this.expectedDate = new DateTime(expectedDate);
	}

	@Test
	public void moveTransactionEventsToTransaction() {
		// When
		TransactionEventSorter sorter = new TransactionEventSorter();
		sorter.moveTransactionEventsToTransaction(feed);

		// Then
		if (verifyDate) {
			Assertions.assertThat(feed.get(expectedIndex)).isInstanceOf(MenigaTransactionEvent.class);
			MenigaTransactionEvent event = (MenigaTransactionEvent) feed.get(expectedIndex);
			verify(event).setDate(eq(expectedDate));
		} else {
			Assertions.assertThat(feed.size()).isEqualTo(20);
		}
	}
}
