package com.meniga.sdk.models.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType;
import com.meniga.sdk.models.accounts.enums.AccountBalanceHistorySort;
import com.meniga.sdk.models.accounts.enums.AccountCategory;
import com.meniga.sdk.models.accounts.operators.MenigaAccountOperations;
import com.meniga.sdk.models.transactions.MetaData;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An account in the Meniga System containing transactions.
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaAccount extends StateObject implements Parcelable, Serializable, Cloneable {

	public static final Creator<MenigaAccount> CREATOR = new Creator<MenigaAccount>() {
		@Override
		public MenigaAccount createFromParcel(Parcel source) {
			return new MenigaAccount(source);
		}

		@Override
		public MenigaAccount[] newArray(int size) {
			return new MenigaAccount[size];
		}
	};

	protected static MenigaAccountOperations apiOperator;
	protected long id;
	protected String accountIdentifier;
	protected String realmIdentifier;
	protected int realmAccountTypeId;
	protected int accountTypeId;
	protected String name;
	protected MenigaDecimal balance;
	protected MenigaDecimal originalBalance;
	protected MenigaDecimal committedAmount;
	protected MenigaDecimal limit;
	protected String accountClass;
	protected String organizationIdentifier;
	protected Long realmCredentialsId;
	protected AccountAuthorizationType accountAuthorizatonType;
	protected int orderId;
	protected boolean isImportAccount;
	protected DateTime lastUpdate;
	protected Long personId;
	protected String userEmail;
	protected DateTime createDate;
	protected AccountCategory accountCategory;
	protected AccountCategory accountType;
	protected MenigaDecimal emergencyFundBalanceLimit;
	protected boolean inactive;
	protected DateTime attachedToUserDate;
	protected boolean isHidden;
	protected boolean isDisabled;
	protected List<MetaData> metadata;

	protected MenigaAccount() {
	}

	protected MenigaAccount(Parcel in) {
		this.id = in.readLong();
		this.accountIdentifier = in.readString();
		this.realmIdentifier = in.readString();
		this.realmAccountTypeId = in.readInt();
		this.accountTypeId = in.readInt();
		this.name = in.readString();
		this.balance = (MenigaDecimal) in.readSerializable();
		this.originalBalance = (MenigaDecimal) in.readSerializable();
		this.committedAmount = (MenigaDecimal) in.readSerializable();
		this.limit = (MenigaDecimal) in.readSerializable();
		this.accountClass = in.readString();
		this.organizationIdentifier = in.readString();
		this.realmCredentialsId = (Long) in.readValue(Long.class.getClassLoader());
		int tmpAccountAuthorizatonType = in.readInt();
		this.accountAuthorizatonType = tmpAccountAuthorizatonType == -1 ? null : AccountAuthorizationType.values()[tmpAccountAuthorizatonType];
		this.orderId = in.readInt();
		this.isImportAccount = in.readByte() != 0;
		this.lastUpdate = (DateTime) in.readSerializable();
		this.personId = (Long) in.readValue(Long.class.getClassLoader());
		this.userEmail = in.readString();
		this.createDate = (DateTime) in.readSerializable();
		int tmpAccountCategory = in.readInt();
		this.accountCategory = tmpAccountCategory == -1 ? null : AccountCategory.values()[tmpAccountCategory];
		int tmpAccountType = in.readInt();
		this.accountType = tmpAccountType == -1 ? null : AccountCategory.values()[tmpAccountType];
		this.emergencyFundBalanceLimit = (MenigaDecimal) in.readSerializable();
		this.inactive = in.readByte() != 0;
		this.attachedToUserDate = (DateTime) in.readSerializable();
		this.isHidden = in.readByte() != 0;
		this.metadata = in.createTypedArrayList(MetaData.CREATOR);
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaAccountOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaAccountOperations operator) {
		MenigaAccount.apiOperator = operator;
	}

	/**
	 * Retrieves an account with id
	 *
	 * @param id The id of the account to retrieve
	 * @return A Result containing the account object
	 */
	public static Result<MenigaAccount> fetch(long id) {
		return MenigaAccount.apiOperator.getAccount(id);
	}

	/**
	 * Get all accounts the user has
	 *
	 * @return A Result containing a list of all the accounts the user has
	 */
	public static Result<List<MenigaAccount>> fetch() {
		return MenigaAccount.apiOperator.getAccounts(false, false);
	}

	public static Result<List<MenigaAccount>> fetch(boolean includeHidden, boolean includeDisabled) {
		return MenigaAccount.apiOperator.getAccounts(includeHidden, includeDisabled);
	}

	/**
	 * Gets a list of all available account types.
	 *
	 * @return A list of all account types.
	 */
	public static Result<List<MenigaAccountType>> fetchAccountTypes() {
		return MenigaAccount.apiOperator.getAccountTypes();
	}

	/**
	 * Gets a list of all available values as an array of NameId values.
	 *
	 * @return A list of all authorization types
	 */
	public static Result<List<MenigaAuthorizationType>> fetchAuthorizationTypes() {
		return MenigaAccount.apiOperator.getAccountAuthorizationTypes();
	}

	/**
	 * @return The account id for the account.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The identifier for the account set by the originating bank. This identifier is used when getting account statements.
	 */
	public String getAccountIdentifier() {
		return this.accountIdentifier;
	}

	/**
	 * @return The realm identifier for this account. Realm here can mean e.g. a bank institute
	 */
	public String getRealmIdentifier() {
		return this.realmIdentifier;
	}

	/**
	 * @return The realm account type for this account. Realm here can mean e.g. a bank institute
	 */
	public int getRealmAccountTypeId() {
		return this.realmAccountTypeId;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	/**
	 * @return The name of the account set by the user or the originating bank.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Renames the account
	 *
	 * @param name New name for the account.
	 */
	public void setName(String name) {
		if (this.hasChanged(this.name, name)) {
			return;
		}
		this.changed();
		this.name = name;
	}

	/**
	 * @return The current balance of the account.
	 */
	public MenigaDecimal getBalance() {
		return this.balance;
	}

	/**
	 * @return The balance for the account when it was created.
	 */
	public MenigaDecimal getOriginalBalance() {
		return this.originalBalance;
	}

	/**
	 * @return The committed amount to this account
	 */
	public MenigaDecimal getCommittedAmount() {
		return this.committedAmount;
	}

	/**
	 * @return The limit or overdraft for the account
	 */
	public MenigaDecimal getLimit() {
		return this.limit;
	}

	/**
	 * @return The account class that is for example used for displaying an image for this account in CSS
	 */
	public String getAccountClass() {
		return this.accountClass;
	}

	/**
	 * @return A code that identifies the organization associated with this account globally, such as a Swift code
	 */
	public String getOrganizationIdentifier() {
		return this.organizationIdentifier;
	}

	/**
	 * @return Identifier for an online bank user that owns this account
	 */
	public Long getRealmCredentialsId() {
		return this.realmCredentialsId;
	}

	/**
	 * @return Indicates the type of account authorization during account aggregation = ['0', '1', '2', '3'].
	 */
	public AccountAuthorizationType getAccountAuthorizatonType() {
		return this.accountAuthorizatonType;
	}

	/**
	 * @return Accounts are ordered in ascending order by this order key
	 */
	public int getOrderId() {
		return this.orderId;
	}

	/**
	 * Sets a new order id for the account. Accounts are ordered in ascending order by this order key
	 *
	 * @param orderId The new order id for the account.
	 */
	public void setOrderId(int orderId) {
		if (this.hasChanged(this.orderId, orderId)) {
			return;
		}
		this.changed();
		this.orderId = orderId;
	}

	/**
	 * @return Indicates if this account causes transactions to be imported manually or not.
	 */
	public boolean getIsImportAccount() {
		return this.isImportAccount;
	}

	/**
	 * @return Last update time of this account.
	 */
	public DateTime getLastUpdate() {
		return this.lastUpdate;
	}

	/**
	 * @return Identifier for the person that owns this account.
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * @return Email for the person that owns this account.
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

	/**
	 * @return Creation date for this account.
	 */
	public DateTime getCreateDate() {
		return this.createDate;
	}

	/**
	 * @return Indicates if this is a Current account, Credit account or Saving account = ['0', '1', '2', '3', '4', '5', '21', '22'].
	 */
	public AccountCategory getAccountCategory() {
		if (this.accountCategory == null) {
			return this.accountType;
		}
		return this.accountCategory;
	}

	/**
	 * @return If this account is used to track Life goals, this amount indicates the amount that is not used for goal allocation.
	 */
	public MenigaDecimal getEmergencyFundBalanceLimit() {
		return this.emergencyFundBalanceLimit;
	}

	/**
	 * Sets a new emergency fund balance limit for this account
	 *
	 * @param emergencyFundBalanceLimit The new emergencyFundBalanceLimit for the account.
	 */
	public void setEmergencyFundBalanceLimit(MenigaDecimal emergencyFundBalanceLimit) {
		if (this.hasChanged(this.emergencyFundBalanceLimit, emergencyFundBalanceLimit)) {
			return;
		}
		this.changed();
		this.emergencyFundBalanceLimit = emergencyFundBalanceLimit;
	}

	/**
	 * @return True if the account is inactive.
	 */
	public boolean getIsInactive() {
		return this.inactive;
	}

	/**
	 * @return DateTime when the user added thia account.
	 */
	public DateTime getAttachedToUserDate() {
		return this.attachedToUserDate;
	}

	/**
	 * @return True if the account should be marked as hidden. It is up to each component/widget what to do with this information.
	 */
	public boolean getIsHidden() {
		return this.isHidden;
	}

	/**
	 * Sets if this account should be marked as hidden
	 *
	 * @param isHidden If the account should be hidden. True means it's hidden.
	 */
	public void setIsHidden(boolean isHidden) {
		if (this.hasChanged(this.isHidden, isHidden)) {
			return;
		}
		this.changed();
		this.isHidden = isHidden;
	}

	/**
	 * @return True if the this account should be excluded from calculations.
	 */
	public boolean getIsDisabled() {
		return this.isDisabled;
	}

	/**
	 * Sets if this account should be marked as diabled or not.
	 *
	 * @param isDisabled Should the account be disabled or not.
	 */
	public void setIsDisabled(boolean isDisabled) {
		if (this.hasChanged(this.isDisabled, isDisabled)) {
			return;
		}
		this.changed();
		this.isDisabled = isDisabled;
	}

	/**
	 * @return List of key-vals for various metadata.
	 */
	public List<MetaData> getMetadata() {
		return this.metadata;
	}


	/*
	--- API calls below ---
	 */

	/**
	 * @return List of key-vals for various metadata as a map.
	 */
	public Map<String, String> getMetaDataAsMap() {
		Map<String, String> result = new HashMap<>();
		if (this.metadata != null) {
			for (int i = 0; i < this.metadata.size(); ++i) {
				result.put(this.metadata.get(i).getName(), this.metadata.get(i).getValue());
			}
		}
		return result;
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaAccount)) {
			return;
		}

		MenigaAccount lastRev = (MenigaAccount) lastRevision;
		this.name = lastRev.name;
		this.orderId = lastRev.orderId;
		this.emergencyFundBalanceLimit = lastRev.emergencyFundBalanceLimit;
		this.isHidden = lastRev.isHidden;
		this.isDisabled = lastRev.isDisabled;
	}

	@Override
	public String toString() {
		String ret = "null";
		if (this.name != null) {
			ret = this.name;
		}
		if (this.balance != null) {
			ret += ", " + this.balance.doubleValue();
		}
		if (this.accountCategory != null) {
			ret += " (" + this.accountCategory + ")";
		}
		return ret;
	}

	@Override
	public MenigaAccount clone() throws CloneNotSupportedException {
		return (MenigaAccount) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaAccount that = (MenigaAccount) o;

		if (id != that.id) {
			return false;
		}
		if (realmAccountTypeId != that.realmAccountTypeId) {
			return false;
		}
		if (accountTypeId != that.accountTypeId) {
			return false;
		}
		if (orderId != that.orderId) {
			return false;
		}
		if (isImportAccount != that.isImportAccount) {
			return false;
		}
		if (inactive != that.inactive) {
			return false;
		}
		if (isHidden != that.isHidden) {
			return false;
		}
		if (isDisabled != that.isDisabled) {
			return false;
		}
		if (accountIdentifier != null ? !accountIdentifier.equals(that.accountIdentifier) : that.accountIdentifier != null) {
			return false;
		}
		if (realmIdentifier != null ? !realmIdentifier.equals(that.realmIdentifier) : that.realmIdentifier != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (balance != null ? !balance.equals(that.balance) : that.balance != null) {
			return false;
		}
		if (originalBalance != null ? !originalBalance.equals(that.originalBalance) : that.originalBalance != null) {
			return false;
		}
		if (committedAmount != null ? !committedAmount.equals(that.committedAmount) : that.committedAmount != null) {
			return false;
		}
		if (limit != null ? !limit.equals(that.limit) : that.limit != null) {
			return false;
		}
		if (accountClass != null ? !accountClass.equals(that.accountClass) : that.accountClass != null) {
			return false;
		}
		if (organizationIdentifier != null ? !organizationIdentifier.equals(that.organizationIdentifier) : that.organizationIdentifier != null) {
			return false;
		}
		if (realmCredentialsId != null ? !realmCredentialsId.equals(that.realmCredentialsId) : that.realmCredentialsId != null) {
			return false;
		}
		if (accountAuthorizatonType != that.accountAuthorizatonType) {
			return false;
		}
		if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) {
			return false;
		}
		if (personId != null ? !personId.equals(that.personId) : that.personId != null) {
			return false;
		}
		if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) {
			return false;
		}
		if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) {
			return false;
		}
		if (accountCategory != that.accountCategory) {
			return false;
		}
		if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) {
			return false;
		}
		if (emergencyFundBalanceLimit != null ? !emergencyFundBalanceLimit.equals(that.emergencyFundBalanceLimit) : that.emergencyFundBalanceLimit != null) {
			return false;
		}
		return attachedToUserDate != null ? attachedToUserDate.equals(that.attachedToUserDate) : that.attachedToUserDate == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (accountIdentifier != null ? accountIdentifier.hashCode() : 0);
		result = 31 * result + (realmIdentifier != null ? realmIdentifier.hashCode() : 0);
		result = 31 * result + realmAccountTypeId;
		result = 31 * result + accountTypeId;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		result = 31 * result + (originalBalance != null ? originalBalance.hashCode() : 0);
		result = 31 * result + (committedAmount != null ? committedAmount.hashCode() : 0);
		result = 31 * result + (limit != null ? limit.hashCode() : 0);
		result = 31 * result + (accountClass != null ? accountClass.hashCode() : 0);
		result = 31 * result + (organizationIdentifier != null ? organizationIdentifier.hashCode() : 0);
		result = 31 * result + (realmCredentialsId != null ? realmCredentialsId.hashCode() : 0);
		result = 31 * result + (accountAuthorizatonType != null ? accountAuthorizatonType.hashCode() : 0);
		result = 31 * result + orderId;
		result = 31 * result + (isImportAccount ? 1 : 0);
		result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
		result = 31 * result + (personId != null ? personId.hashCode() : 0);
		result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
		result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
		result = 31 * result + (accountCategory != null ? accountCategory.hashCode() : 0);
		result = 31 * result + (emergencyFundBalanceLimit != null ? emergencyFundBalanceLimit.hashCode() : 0);
		result = 31 * result + (inactive ? 1 : 0);
		result = 31 * result + (attachedToUserDate != null ? attachedToUserDate.hashCode() : 0);
		result = 31 * result + (isHidden ? 1 : 0);
		result = 31 * result + (isDisabled ? 1 : 0);
		result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.accountIdentifier);
		dest.writeString(this.realmIdentifier);
		dest.writeInt(this.realmAccountTypeId);
		dest.writeInt(this.accountTypeId);
		dest.writeString(this.name);
		dest.writeSerializable(this.balance);
		dest.writeSerializable(this.originalBalance);
		dest.writeSerializable(this.committedAmount);
		dest.writeSerializable(this.limit);
		dest.writeString(this.accountClass);
		dest.writeString(this.organizationIdentifier);
		dest.writeValue(this.realmCredentialsId);
		dest.writeInt(this.accountAuthorizatonType == null ? -1 : this.accountAuthorizatonType.ordinal());
		dest.writeInt(this.orderId);
		dest.writeByte(this.isImportAccount ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.lastUpdate);
		dest.writeValue(this.personId);
		dest.writeString(this.userEmail);
		dest.writeSerializable(this.createDate);
		dest.writeInt(this.accountCategory == null ? -1 : this.accountCategory.ordinal());
		dest.writeInt(this.accountType == null ? -1 : this.accountType.ordinal());
		dest.writeSerializable(this.emergencyFundBalanceLimit);
		dest.writeByte(this.inactive ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.attachedToUserDate);
		dest.writeByte(this.isHidden ? (byte) 1 : (byte) 0);
		dest.writeTypedList(this.metadata);
	}

	/**
	 * Updates the data in the account on the server, only the following fields can be updated:
	 * name and isHidden
	 *
	 * @return A Task indicating if the update was successful
	 */
	public Result<Void> update() {
		Result<Void> task = MenigaAccount.apiOperator.updateAccount(this);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<Void>() {
			@Override
			public void onFinished(Void result, boolean failed) {
				if (!failed) {
					MenigaAccount.this.resetState();
				}
			}
		});
	}

	/**
	 * Deletes the account
	 *
	 * @return A Result of type void, the task will have an error and be marked as failed if deletion is not successful
	 */
	public Result<Void> delete() {
		return MenigaAccount.apiOperator.deleteAccount(this.getId());
	}

	/**
	 * Gets a list of all parameters for the account instance
	 *
	 * @return List of parameters for the account
	 */
	public Result<List<KeyVal<String, String>>> fetchMetadata() {
		return MenigaAccount.apiOperator.getMetadata(this.getId());
	}

	/**
	 * Adds or updates a metadata key value pair
	 *
	 * @return The updated key value metadata
	 */
	public Result<KeyVal<String, String>> updateMetadata(KeyVal<String, String> keyVal) {
		return MenigaAccount.apiOperator.updateMetadata(this.getId(), keyVal);
	}

	/**
	 * Gets a value for a single account parameter
	 *
	 * @param key The key who's value we want to get
	 * @return The keyval for the account parameter
	 */
	public Result<KeyVal<String, String>> fetchMetadataKeyVal(String key) {
		return MenigaAccount.apiOperator.getMetadataKeyVal(this.getId(), key);
	}

	/**
	 * Gets a list of account balance history entries for this account
	 *
	 * @param from The start date for the entries to be fetched.
	 * @param to   The end date for the entries to be fetched.
	 * @param sort Allows sorting by BalanceDate and Balance. Prefixing the property by "-" indicates ordering by DESC else ASC. The default is BalanceDate.
	 * @return A list of the account balance history matching the given criteria
	 */
	public Result<List<MenigaAccountBalanceHistory>> fetchBalanceHistory(DateTime from, DateTime to, AccountBalanceHistorySort sort) {
		return MenigaAccount.apiOperator.getBalanceHistory(this.getId(), from, to, sort);
	}

	/**
	 * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
	 *
	 * @return A Result of type void, the task will have an error and be marked as failed if it is not successful
	 */
	public Result<MenigaAccount> refresh() {
		Result<MenigaAccount> task = MenigaAccount.fetch(this.id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaAccount>() {
			@Override
			public void onFinished(MenigaAccount result, boolean failed) {
				try {
					Merge.merge(MenigaAccount.this, result);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
