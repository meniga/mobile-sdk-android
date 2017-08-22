package com.meniga.sdk.models.serverpublic;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.serverpublic.operators.MenigaPublicSettingsOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Public configurations for the whole Meniga system.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaPublicSettings implements Serializable, Parcelable, Cloneable {
	public static final Creator<MenigaPublicSettings> CREATOR = new Creator<MenigaPublicSettings>() {
		@Override
		public MenigaPublicSettings createFromParcel(Parcel source) {
			return new MenigaPublicSettings(source);
		}

		@Override
		public MenigaPublicSettings[] newArray(int size) {
			return new MenigaPublicSettings[size];
		}
	};
	private static final String DEFAULT_NUMBER_FORMAT = "#,##0.00";

	protected static MenigaPublicSettingsOperations apiOperator;

	protected String defaultCultureName;
	protected String systemCurrency;
	protected String numberFormat;
	protected String currencyFormat;
	protected String clusterNodeName;
	protected int currencyRoundOff;
	protected int currencyDecimalDigits;
	protected List<MenigaCurrency> currencies;
	protected String currentCulture;
	protected String currencyGroupSymbol;
	protected String currencyDecimalSymbol;

	protected MenigaPublicSettings() {
	}

	protected MenigaPublicSettings(Parcel in) {
		this.defaultCultureName = in.readString();
		this.systemCurrency = in.readString();
		this.numberFormat = in.readString();
		this.currencyFormat = in.readString();
		this.clusterNodeName = in.readString();
		this.currencyRoundOff = in.readInt();
		this.currencyDecimalDigits = in.readInt();
		this.currencies = in.createTypedArrayList(MenigaCurrency.CREATOR);
		this.currentCulture = in.readString();
		this.currencyGroupSymbol = in.readString();
		this.currencyDecimalSymbol = in.readString();
	}

	@Override
	protected MenigaPublicSettings clone() throws CloneNotSupportedException {
		return (MenigaPublicSettings) super.clone();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaPublicSettingsOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaPublicSettingsOperations operator) {
		MenigaPublicSettings.apiOperator = operator;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.defaultCultureName);
		dest.writeString(this.systemCurrency);
		dest.writeString(this.numberFormat);
		dest.writeString(this.currencyFormat);
		dest.writeString(this.clusterNodeName);
		dest.writeInt(this.currencyRoundOff);
		dest.writeInt(this.currencyDecimalDigits);
		dest.writeTypedList(currencies);
		dest.writeString(this.currentCulture);
		dest.writeString(this.currencyGroupSymbol);
		dest.writeString(this.currencyDecimalSymbol);
	}

	/**
	 * @return The default culture name.
	 */
	public String getDefaultCultureName() {
		return defaultCultureName;
	}

	/**
	 * @return The system currency.
	 */
	public String getSystemCurrency() {
		return systemCurrency;
	}

	/**
	 * @return The number format.
	 */
	public String getNumberFormat() {
		return numberFormat;
	}

	/**
	 * @return The currency format.
	 */
	public String getCurrencyFormat() {
		return currencyFormat;
	}

	/**
	 * @return The cluster node name.
	 */
	public String getClusterNodeName() {
		return clusterNodeName;
	}

	/**
	 * @return The currency budget round off.
	 */
	public int getCurrencyRoundOff() {
		return currencyRoundOff;
	}

	/**
	 * @return The currency decimal digits.
	 */
	public int getCurrencyDecimalDigits() {
		return currencyDecimalDigits;
	}

	/**
	 * @return The currencies the system offers.
	 */
	public List<MenigaCurrency> getCurrencies() {
		return currencies;
	}

	/**
	 * @return The current system culture.
	 */
	public String getCurrentCulture() {
		return currentCulture;
	}

	/**
	 * @return The currency group symbol.
	 */
	public String getCurrencyGroupSymbol() {
		return currencyGroupSymbol;
	}

	/**
	 * @return The currency decimal symbol.
	 */
	public String getCurrencyDecimalSymbol() {
		return currencyDecimalSymbol;
	}

	/**
	 * Retrieves the number format (e.g. ###,####.##) for the currency code provided
	 * @param currencyCode The currency code used to look up the number format
	 * @return The number format corresponding to the currency code
	 */
	public String getNumberFormat(String currencyCode) {
		MenigaCurrency currency = findCurrency(currencyCode);
		String format = numberFormat;
		if (currency != null) {
			format = currency.getFormat();
		}

		if (format == null) {
			return DEFAULT_NUMBER_FORMAT;
		}

		return format;
	}

	/**
	 * Retrieves the standalone currency symbol for the currency code provided
	 * @param currencyCode The currency code to use to look for the symbol (USD = $, EUR = € etc)
	 * @return The standalone currency symbol ($, € etc)
	 */
	public String getCurrencySymbol(String currencyCode) {
		MenigaCurrency currency = findCurrency(currencyCode);
		String format = currencyFormat;
		if (currency != null) {
			format = currency.getCurrencyFormat();
		}

		if (format == null) {
			if (systemCurrency != null) {
				return systemCurrency;
			} else if (currencyCode != null) {
				return currencyCode;
			} else {
				return "";
			}
		}

		String symbol = format.replace(" ", "");
		for (int i = 0; i < 11; i++) {
			symbol = symbol.replace("{" + i + "}", "");
		}
		return symbol;
	}

	/**
	 * Converts the currency format into a java format-able string. The server format is in C# format
	 * @param currencyCode The currency code to retrieve the correct currency format
	 * @return The currency format in java format for the currency code requested
	 */
	public String getJavaFormatForCurrency(String currencyCode) {
		MenigaCurrency currency = findCurrency(currencyCode);
		String format = currencyFormat;
		if (currency != null) {
			format = currency.getCurrencyFormat();
		}

		if (format == null) {
			if (currency != null && currency.getCode() != null) {
				return "{1}" + currency.getCode() + "{0}";
			} else if (systemCurrency != null) {
				return "{1}" + systemCurrency + "{0}";
			} else {
				return "{1}{0}";
			}
		}

		if (!format.contains("{1}")) {
			format = format.replace("{0}", "{1}{0}");
		}
		format = format.replace("{0}", "%2$s");
		format = format.replace("{1}", "%1$s");
		return format;
	}

	private MenigaCurrency findCurrency(String code) {
		if (currencies == null) {
			return null;
		}
		for (MenigaCurrency currency : currencies) {
			if (currency.getCode().toUpperCase().equals(code.toUpperCase(Locale.getDefault()))) {
				return currency;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaPublicSettings that = (MenigaPublicSettings) o;

		if (currencyRoundOff != that.currencyRoundOff) {
			return false;
		}
		if (currencyDecimalDigits != that.currencyDecimalDigits) {
			return false;
		}
		if (defaultCultureName != null ? !defaultCultureName.equals(that.defaultCultureName) : that.defaultCultureName != null) {
			return false;
		}
		if (systemCurrency != null ? !systemCurrency.equals(that.systemCurrency) : that.systemCurrency != null) {
			return false;
		}
		if (numberFormat != null ? !numberFormat.equals(that.numberFormat) : that.numberFormat != null) {
			return false;
		}
		if (currencyFormat != null ? !currencyFormat.equals(that.currencyFormat) : that.currencyFormat != null) {
			return false;
		}
		if (clusterNodeName != null ? !clusterNodeName.equals(that.clusterNodeName) : that.clusterNodeName != null) {
			return false;
		}
		if (currencies != null ? !currencies.equals(that.currencies) : that.currencies != null) {
			return false;
		}
		if (currentCulture != null ? !currentCulture.equals(that.currentCulture) : that.currentCulture != null) {
			return false;
		}
		if (currencyGroupSymbol != null ? !currencyGroupSymbol.equals(that.currencyGroupSymbol) : that.currencyGroupSymbol != null) {
			return false;
		}
		return currencyDecimalSymbol != null ? currencyDecimalSymbol.equals(that.currencyDecimalSymbol) : that.currencyDecimalSymbol == null;
	}

	@Override
	public int hashCode() {
		int result = defaultCultureName != null ? defaultCultureName.hashCode() : 0;
		result = 31 * result + (systemCurrency != null ? systemCurrency.hashCode() : 0);
		result = 31 * result + (numberFormat != null ? numberFormat.hashCode() : 0);
		result = 31 * result + (currencyFormat != null ? currencyFormat.hashCode() : 0);
		result = 31 * result + (clusterNodeName != null ? clusterNodeName.hashCode() : 0);
		result = 31 * result + currencyRoundOff;
		result = 31 * result + currencyDecimalDigits;
		result = 31 * result + (currencies != null ? currencies.hashCode() : 0);
		result = 31 * result + (currentCulture != null ? currentCulture.hashCode() : 0);
		result = 31 * result + (currencyGroupSymbol != null ? currencyGroupSymbol.hashCode() : 0);
		result = 31 * result + (currencyDecimalSymbol != null ? currencyDecimalSymbol.hashCode() : 0);
		return result;
	}

	private static class MenigaCurrency implements Parcelable, Serializable {

		public static final Creator<MenigaCurrency> CREATOR = new Creator<MenigaCurrency>() {
			@Override
			public MenigaCurrency createFromParcel(Parcel source) {
				return new MenigaCurrency(source);
			}

			@Override
			public MenigaCurrency[] newArray(int size) {
				return new MenigaCurrency[size];
			}
		};

		protected int id;
		protected String code;
		protected Boolean isDefault;
		protected String name;
		protected String format;
		protected String currencyFormat;
		protected String provider;
		protected Integer roundOff;

		protected MenigaCurrency() {
		}

		protected MenigaCurrency(Parcel in) {
			this.id = in.readInt();
			this.code = in.readString();
			this.isDefault = (Boolean) in.readValue(Boolean.class.getClassLoader());
			this.name = in.readString();
			this.format = in.readString();
			this.currencyFormat = in.readString();
			this.provider = in.readString();
			this.roundOff = (Integer) in.readValue(Integer.class.getClassLoader());
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.id);
			dest.writeString(this.code);
			dest.writeValue(this.isDefault);
			dest.writeString(this.name);
			dest.writeString(this.format);
			dest.writeString(this.currencyFormat);
			dest.writeString(this.provider);
			dest.writeValue(this.roundOff);
		}

		/**
		 * @return Id of the currency.
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return The currency code.
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @return True if the currency is the default one for the system.
		 */
		public Boolean getDefault() {
			return isDefault;
		}

		/**
		 * @return Name of the currency.
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return The format of the currency.
		 */
		public String getFormat() {
			return format;
		}

		/**
		 * @return The format of the currency name.
		 */
		public String getCurrencyFormat() {
			return currencyFormat;
		}

		/**
		 * @return Who gives you the information about the currency.
		 */
		public String getProvider() {
			return provider;
		}

		/**
		 * @return Number of decimal numbers to round off.
		 */
		public Integer getRoundOff() {
			return roundOff;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			MenigaCurrency currency = (MenigaCurrency) o;

			if (id != currency.id) {
				return false;
			}
			if (code != null ? !code.equals(currency.code) : currency.code != null) {
				return false;
			}
			if (isDefault != null ? !isDefault.equals(currency.isDefault) : currency.isDefault != null) {
				return false;
			}
			if (name != null ? !name.equals(currency.name) : currency.name != null) {
				return false;
			}
			if (format != null ? !format.equals(currency.format) : currency.format != null) {
				return false;
			}
			if (currencyFormat != null ? !currencyFormat.equals(currency.currencyFormat) : currency.currencyFormat != null) {
				return false;
			}
			if (provider != null ? !provider.equals(currency.provider) : currency.provider != null) {
				return false;
			}
			return roundOff != null ? roundOff.equals(currency.roundOff) : currency.roundOff == null;
		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + (code != null ? code.hashCode() : 0);
			result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
			result = 31 * result + (name != null ? name.hashCode() : 0);
			result = 31 * result + (format != null ? format.hashCode() : 0);
			result = 31 * result + (currencyFormat != null ? currencyFormat.hashCode() : 0);
			result = 31 * result + (provider != null ? provider.hashCode() : 0);
			result = 31 * result + (roundOff != null ? roundOff.hashCode() : 0);
			return result;
		}
	}

	/*
	API CALLS
	 */

	/**
	 * Returns the public system settings.
	 *
	 * @return Public system settings.
	 */
	public static Result<MenigaPublicSettings> fetch() {
		return MenigaPublicSettings.apiOperator.getPublicSettings();
	}
}
