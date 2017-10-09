package com.meniga.sdk.models.merchants;


import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class TopMerchantOptions implements Serializable {

	private Integer maxMerchants;
	private Boolean includeUnMappedMerchants;
	private Boolean useParentMerchantIds;

	public static class Builder {

		private Integer maxMerchants;
		private Boolean includeUnMappedMerchants;
		private Boolean useParentMerchantIds;

		public Builder maxMerchants(int maxMerchants) {
			this.maxMerchants = maxMerchants;
			return this;
		}

		public Builder includeUnMappedMerchants(Boolean includeUnMappedMerchants) {
			this.includeUnMappedMerchants = includeUnMappedMerchants;
			return this;
		}

		public Builder useParentMerchantIds(Boolean useParentMerchantIds) {
			this.useParentMerchantIds = useParentMerchantIds;
			return this;
		}

		public TopMerchantOptions build() {
			TopMerchantOptions tmo = new TopMerchantOptions();
			tmo.includeUnMappedMerchants = this.includeUnMappedMerchants;
			tmo.maxMerchants = this.maxMerchants;
			tmo.useParentMerchantIds = this.useParentMerchantIds;
			return tmo;
		}
	}
}
