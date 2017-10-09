package com.meniga.sdk.models.transactions;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * A list of transaction that includes the total number of transactions available given the criteria
 * used to generate the list.
 */
public class MenigaTransactionPage extends ArrayList<MenigaTransaction> implements Serializable {

	protected int totalNumTransactions;
	protected int numPages;
	protected int page;
	protected int numItemsPerPage;

	/**
	 * @return The page we are on in the paginated result set
	 */
	public int getPage() {
		return this.page;
	}

	/**
	 * Sets the page this result is on.
	 *
	 * @param page The page we are on in the paginated result set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return The total number of transaction according to your original request. Used for pagination to know the number of pages.
	 */
	public int getTotalNumTransactions() {
		return this.totalNumTransactions;
	}

	/**
	 * Sets the total number of transactions that match the criteria (filter) that was used to generate this transaction page
	 *
	 * @param num The total number of transactions of the query
	 */
	public void setTotalNumTransactions(int num) {
		this.totalNumTransactions = num;
		if (this.numItemsPerPage > 0) {
			this.calcPages();
		}
	}

	/**
	 * @return The total number of pages, calculated by dividing the total number of transactions that match the query with the number of transactions per page
	 */
	public int getNumPages() {
		if (this.numPages == 0 && this.totalNumTransactions > 0 && this.numItemsPerPage > 0) {
			this.calcPages();
		}
		return this.numPages;
	}

	/**
	 * @return The number of transactions per page.
	 */
	public int getNumItemsPerPage() {
		return this.numItemsPerPage;
	}

	/**
	 * Sets the number of items per page. Used for paginating the results. The query may apply to many more transactions than will be contained in the
	 * page (use getTotalNumTransactions to get that number).
	 *
	 * @param numItemsPerPage The number of transactions per page
	 */
	public void setNumItemsPerPage(int numItemsPerPage) {
		this.numItemsPerPage = numItemsPerPage;
		if (this.totalNumTransactions > 0) {
			this.calcPages();
		}
	}

	private void calcPages() {
		this.numPages = (int) Math.ceil(this.totalNumTransactions / (float) this.numItemsPerPage);
	}
}
