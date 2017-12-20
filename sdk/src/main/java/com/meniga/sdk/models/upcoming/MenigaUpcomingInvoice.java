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
public class MenigaUpcomingInvoice implements Serializable, Parcelable, Cloneable {
	protected long id;
	protected String identifier;
	protected String bankReference;
	protected String invoiceText;
	protected MenigaDecimal amountInCurrency;
	protected MenigaDecimal feeAmount;
	protected MenigaDecimal vatAmount;
	protected String currencyCode;
	protected DateTime issuedDate;
	protected DateTime dueDate;
	protected DateTime finalDueDate;
	protected DateTime bookingDate;
	protected PaymentStatus paymentStatus;
	protected String parsedData;
	protected Boolean isReceivable;
	protected String issuerName;
	protected String issuerIdentifier;
	protected String issuerAccIdentifier;
	protected String issuerReferenceText;
	protected String counterpartyName;
	protected String counterpartyIdentifier;
	protected String counterpartyAccIdentifier;
	protected String counterpartyReferenceText;

	protected MenigaUpcomingInvoice() {
	}

	@Override
	public MenigaUpcomingInvoice clone() throws CloneNotSupportedException {
		return (MenigaUpcomingInvoice) super.clone();
	}

	/**
	 * @return The id set by the Meniga System to identify the instance
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
	 * @return A human readable text that is displayed to the end user as the title or the subject of the invoice
	 */
	public String getInvoiceText() {
		return invoiceText;
	}

	/**
	 * @return The absolute amount of the invoice in the currency defined in "CurrencyCode", excluding fee and VAT
	 */
	public MenigaDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	/**
	 * @return The absolute fee of the invoice
	 */
	public MenigaDecimal getFeeAmount() {
		return feeAmount;
	}

	/**
	 * @return The absolute VAT of the invoice
	 */
	public MenigaDecimal getVatAmount() {
		return vatAmount;
	}

	/**
	 * @return The ISO 4217 currency code of the amounts (AmountInCurrency, FeeAmount, VatAmount)
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return The date when the invoice was issued/created in the external system
	 */
	public DateTime getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @return The date when the invoice is due to be paid
	 */
	public DateTime getDueDate() {
		return dueDate;
	}

	/**
	 * @return The last date before any late fees will be applied to the invoice
	 */
	public DateTime getFinalDueDate() {
		return finalDueDate;
	}

	/**
	 * @return The date when this invoice was paid/booked
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
	 * @return True if invoice is account receivable, false if invoice is account payable
	 */
	public Boolean getReceivable() {
		return isReceivable;
	}

	/**
	 * @return The name of the issuer of the invoice
	 */
	public String getIssuerName() {
		return issuerName;
	}

	/**
	 * @return The identifier that uniquely identifies the issuer of this invoice in the external system
	 */
	public String getIssuerIdentifier() {
		return issuerIdentifier;
	}

	/**
	 * @return The identifier that uniquely identifies the issuer's account that this invoice should be paid from/into in the external system
	 */
	public String getIssuerAccIdentifier() {
		return issuerAccIdentifier;
	}

	/**
	 * @return The internal reference text used by the issuer
	 */
	public String getIssuerReferenceText() {
		return issuerReferenceText;
	}

	/**
	 * @return The name of the counter party or institution entering into an financial contract
	 */
	public String getCounterpartyName() {
		return counterpartyName;
	}

	/**
	 * @return The counter party from the issuer point of view. Identifier that uniquely identifies the party that this invoice was issued to
	 */
	public String getCounterpartyIdentifier() {
		return counterpartyIdentifier;
	}

	/**
	 * @return The identifier that uniquely identifies the counter party's account that this invoice should be paid from/into in the external system
	 */
	public String getCounterpartyAccIdentifier() {
		return counterpartyAccIdentifier;
	}

	/**
	 * @return The internal reference text used by the counterparty
	 */
	public String getCounterpartyReferenceText() {
		return counterpartyReferenceText;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcomingInvoice that = (MenigaUpcomingInvoice) o;

		if (id != that.id) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (bankReference != null ? !bankReference.equals(that.bankReference) : that.bankReference != null) {
			return false;
		}
		if (invoiceText != null ? !invoiceText.equals(that.invoiceText) : that.invoiceText != null) {
			return false;
		}
		if (amountInCurrency != null ? !amountInCurrency.equals(that.amountInCurrency) : that.amountInCurrency != null) {
			return false;
		}
		if (feeAmount != null ? !feeAmount.equals(that.feeAmount) : that.feeAmount != null) {
			return false;
		}
		if (vatAmount != null ? !vatAmount.equals(that.vatAmount) : that.vatAmount != null) {
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
		if (finalDueDate != null ? !finalDueDate.equals(that.finalDueDate) : that.finalDueDate != null) {
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
		if (issuerName != null ? !issuerName.equals(that.issuerName) : that.issuerName != null) {
			return false;
		}
		if (issuerIdentifier != null ? !issuerIdentifier.equals(that.issuerIdentifier) : that.issuerIdentifier != null) {
			return false;
		}
		if (issuerAccIdentifier != null ? !issuerAccIdentifier.equals(that.issuerAccIdentifier) : that.issuerAccIdentifier != null) {
			return false;
		}
		if (issuerReferenceText != null ? !issuerReferenceText.equals(that.issuerReferenceText) : that.issuerReferenceText != null) {
			return false;
		}
		if (counterpartyName != null ? !counterpartyName.equals(that.counterpartyName) : that.counterpartyName != null) {
			return false;
		}
		if (counterpartyIdentifier != null ? !counterpartyIdentifier.equals(that.counterpartyIdentifier) : that.counterpartyIdentifier != null) {
			return false;
		}
		if (counterpartyAccIdentifier != null ? !counterpartyAccIdentifier.equals(that.counterpartyAccIdentifier) : that.counterpartyAccIdentifier != null) {
			return false;
		}
		return counterpartyReferenceText != null ? counterpartyReferenceText.equals(that.counterpartyReferenceText) : that.counterpartyReferenceText == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + (bankReference != null ? bankReference.hashCode() : 0);
		result = 31 * result + (invoiceText != null ? invoiceText.hashCode() : 0);
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (feeAmount != null ? feeAmount.hashCode() : 0);
		result = 31 * result + (vatAmount != null ? vatAmount.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (issuedDate != null ? issuedDate.hashCode() : 0);
		result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
		result = 31 * result + (finalDueDate != null ? finalDueDate.hashCode() : 0);
		result = 31 * result + (bookingDate != null ? bookingDate.hashCode() : 0);
		result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
		result = 31 * result + (parsedData != null ? parsedData.hashCode() : 0);
		result = 31 * result + (isReceivable != null ? isReceivable.hashCode() : 0);
		result = 31 * result + (issuerName != null ? issuerName.hashCode() : 0);
		result = 31 * result + (issuerIdentifier != null ? issuerIdentifier.hashCode() : 0);
		result = 31 * result + (issuerAccIdentifier != null ? issuerAccIdentifier.hashCode() : 0);
		result = 31 * result + (issuerReferenceText != null ? issuerReferenceText.hashCode() : 0);
		result = 31 * result + (counterpartyName != null ? counterpartyName.hashCode() : 0);
		result = 31 * result + (counterpartyIdentifier != null ? counterpartyIdentifier.hashCode() : 0);
		result = 31 * result + (counterpartyAccIdentifier != null ? counterpartyAccIdentifier.hashCode() : 0);
		result = 31 * result + (counterpartyReferenceText != null ? counterpartyReferenceText.hashCode() : 0);
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
		dest.writeString(this.invoiceText);
		dest.writeSerializable(this.amountInCurrency);
		dest.writeSerializable(this.feeAmount);
		dest.writeSerializable(this.vatAmount);
		dest.writeString(this.currencyCode);
		dest.writeSerializable(this.issuedDate);
		dest.writeSerializable(this.dueDate);
		dest.writeSerializable(this.finalDueDate);
		dest.writeSerializable(this.bookingDate);
		dest.writeInt(this.paymentStatus == null ? -1 : this.paymentStatus.ordinal());
		dest.writeString(this.parsedData);
		dest.writeValue(this.isReceivable);
		dest.writeString(this.issuerName);
		dest.writeString(this.issuerIdentifier);
		dest.writeString(this.issuerAccIdentifier);
		dest.writeString(this.issuerReferenceText);
		dest.writeString(this.counterpartyName);
		dest.writeString(this.counterpartyIdentifier);
		dest.writeString(this.counterpartyAccIdentifier);
		dest.writeString(this.counterpartyReferenceText);
	}

	protected MenigaUpcomingInvoice(Parcel in) {
		this.id = in.readLong();
		this.identifier = in.readString();
		this.bankReference = in.readString();
		this.invoiceText = in.readString();
		this.amountInCurrency = (MenigaDecimal) in.readSerializable();
		this.feeAmount = (MenigaDecimal) in.readSerializable();
		this.vatAmount = (MenigaDecimal) in.readSerializable();
		this.currencyCode = in.readString();
		this.issuedDate = (DateTime) in.readSerializable();
		this.dueDate = (DateTime) in.readSerializable();
		this.finalDueDate = (DateTime) in.readSerializable();
		this.bookingDate = (DateTime) in.readSerializable();
		int tmpPaymentStatus = in.readInt();
		this.paymentStatus = tmpPaymentStatus == -1 ? null : PaymentStatus.values()[tmpPaymentStatus];
		this.parsedData = in.readString();
		this.isReceivable = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.issuerName = in.readString();
		this.issuerIdentifier = in.readString();
		this.issuerAccIdentifier = in.readString();
		this.issuerReferenceText = in.readString();
		this.counterpartyName = in.readString();
		this.counterpartyIdentifier = in.readString();
		this.counterpartyAccIdentifier = in.readString();
		this.counterpartyReferenceText = in.readString();
	}

	public static final Creator<MenigaUpcomingInvoice> CREATOR = new Creator<MenigaUpcomingInvoice>() {
		@Override
		public MenigaUpcomingInvoice createFromParcel(Parcel source) {
			return new MenigaUpcomingInvoice(source);
		}

		@Override
		public MenigaUpcomingInvoice[] newArray(int size) {
			return new MenigaUpcomingInvoice[size];
		}
	};
}
