package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.upcoming.enums.PaymentStatus;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcomingScheduledPayment implements Serializable, Parcelable, Cloneable {
	private long id;
	private String identifier;
	private String bankReference;
	private String paymentText;
	private String referenceText;
	private MenigaDecimal amountInCurrency;
	private String currencyCode;
	private DateTime issuedDate;
	private DateTime dueDate;
	private DateTime bookingDate;
	private PaymentStatus paymentStatus;
	private String parsedData;
	private Boolean isReceivable;
	private String sourceAccIdentifier;
	private String destinationAccIdentifier;

	protected MenigaUpcomingScheduledPayment() {
	}

	@Override
	public MenigaUpcomingScheduledPayment clone() throws CloneNotSupportedException {
		return (MenigaUpcomingScheduledPayment) super.clone();
	}

	/**
	 * @return The id of the scheduled payment
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The identifier that uniquely identifies this entry in the external system
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return An identifier that connects invoices to scheduled payments to transactions in the external system
	 */
	public String getBankReference() {
		return bankReference;
	}

	/**
	 * @return A human readable text that is displayed to the end user as the title or the subject of the scheduled payment
	 */
	public String getPaymentText() {
		return paymentText;
	}

	/**
	 * @return A free form text from the external system
	 */
	public String getReferenceText() {
		return referenceText;
	}

	/**
	 * @return The absolute amount of the scheduled payment in the currency defined in "CurrencyCode"
	 */
	public MenigaDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	/**
	 * @return The ISO 4217 currency code of the "AmountInCurrency"
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return The date when the scheduled payment was issued/created in the external system
	 */
	public DateTime getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return The date when the scheduled payment is due to be paid
	 */
	public DateTime getDueDate() {
		return dueDate;
	}

	/**
	 * @return The date when this scheduled payment was paid/booked
	 */
	public DateTime getBookingDate() {
		return bookingDate;
	}

	/**
	 * @return The payment status of the upcoming PaymentStatusEnumModel = ['Open', 'Paid', 'OnHold']
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @return The custom data parsed into a dictionary of key-values
	 */
	public String getParsedData() {
		return parsedData;
	}

	/**
	 * @return True if scheduled payment is a collection and false is it is payment
	 */
	public Boolean getReceivable() {
		return isReceivable;
	}

	/**
	 * @return The account identifier that uniquely identifies the withdrawal account in the external
	 * system that this scheduled payment should be paid from
	 */
	public String getSourceAccIdentifier() {
		return sourceAccIdentifier;
	}

	/**
	 * @return The account identifier that uniquely identifies the destination account in external
	 * system that the scheduled payment should be paid to
	 */
	public String getDestinationAccIdentifier() {
		return destinationAccIdentifier;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingScheduledPayment that = (MenigaUpcomingScheduledPayment) o;

		if (id != that.id) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (bankReference != null ? !bankReference.equals(that.bankReference) : that.bankReference != null) {
			return false;
		}
		if (paymentText != null ? !paymentText.equals(that.paymentText) : that.paymentText != null) {
			return false;
		}
		if (referenceText != null ? !referenceText.equals(that.referenceText) : that.referenceText != null) {
			return false;
		}
		if (amountInCurrency != null ? !amountInCurrency.equals(that.amountInCurrency) : that.amountInCurrency != null) {
			return false;
		}
		if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) {
			return false;
		}
		if (issuedDate != null ? !issuedDate.equals(that.issuedDate) : that.issuedDate != null) {
			return false;
		}
		if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) {
			return false;
		}
		if (bookingDate != null ? !bookingDate.equals(that.bookingDate) : that.bookingDate != null) {
			return false;
		}
		if (paymentStatus != that.paymentStatus) {
			return false;
		}
		if (parsedData != null ? !parsedData.equals(that.parsedData) : that.parsedData != null) {
			return false;
		}
		if (isReceivable != null ? !isReceivable.equals(that.isReceivable) : that.isReceivable != null) {
			return false;
		}
		if (sourceAccIdentifier != null ? !sourceAccIdentifier.equals(that.sourceAccIdentifier) : that.sourceAccIdentifier != null) {
			return false;
		}
		return destinationAccIdentifier != null ? destinationAccIdentifier.equals(that.destinationAccIdentifier) : that.destinationAccIdentifier == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + (bankReference != null ? bankReference.hashCode() : 0);
		result = 31 * result + (paymentText != null ? paymentText.hashCode() : 0);
		result = 31 * result + (referenceText != null ? referenceText.hashCode() : 0);
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (issuedDate != null ? issuedDate.hashCode() : 0);
		result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
		result = 31 * result + (bookingDate != null ? bookingDate.hashCode() : 0);
		result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
		result = 31 * result + (parsedData != null ? parsedData.hashCode() : 0);
		result = 31 * result + (isReceivable != null ? isReceivable.hashCode() : 0);
		result = 31 * result + (sourceAccIdentifier != null ? sourceAccIdentifier.hashCode() : 0);
		result = 31 * result + (destinationAccIdentifier != null ? destinationAccIdentifier.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.identifier);
		dest.writeString(this.bankReference);
		dest.writeString(this.paymentText);
		dest.writeString(this.referenceText);
		dest.writeSerializable(this.amountInCurrency);
		dest.writeString(this.currencyCode);
		dest.writeSerializable(this.issuedDate);
		dest.writeSerializable(this.dueDate);
		dest.writeSerializable(this.bookingDate);
		dest.writeInt(this.paymentStatus == null ? -1 : this.paymentStatus.ordinal());
		dest.writeString(this.parsedData);
		dest.writeValue(this.isReceivable);
		dest.writeString(this.sourceAccIdentifier);
		dest.writeString(this.destinationAccIdentifier);
	}

	protected MenigaUpcomingScheduledPayment(Parcel in) {
		this.id = in.readLong();
		this.identifier = in.readString();
		this.bankReference = in.readString();
		this.paymentText = in.readString();
		this.referenceText = in.readString();
		this.amountInCurrency = (MenigaDecimal) in.readSerializable();
		this.currencyCode = in.readString();
		this.issuedDate = (DateTime) in.readSerializable();
		this.dueDate = (DateTime) in.readSerializable();
		this.bookingDate = (DateTime) in.readSerializable();
		int tmpPaymentStatus = in.readInt();
		this.paymentStatus = tmpPaymentStatus == -1 ? null : PaymentStatus.values()[tmpPaymentStatus];
		this.parsedData = in.readString();
		this.isReceivable = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.sourceAccIdentifier = in.readString();
		this.destinationAccIdentifier = in.readString();
	}

	public static final Creator<MenigaUpcomingScheduledPayment> CREATOR = new Creator<MenigaUpcomingScheduledPayment>() {
		@Override
		public MenigaUpcomingScheduledPayment createFromParcel(Parcel source) {
			return new MenigaUpcomingScheduledPayment(source);
		}

		@Override
		public MenigaUpcomingScheduledPayment[] newArray(int size) {
			return new MenigaUpcomingScheduledPayment[size];
		}
	};
}
