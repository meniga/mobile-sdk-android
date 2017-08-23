package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.transactions.operators.MenigaCommentOperations;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * A comment on a transaction.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaComment extends StateObject implements Parcelable, Serializable, Cloneable {

	public static final Parcelable.Creator<MenigaComment> CREATOR = new Parcelable.Creator<MenigaComment>() {
		public MenigaComment createFromParcel(Parcel source) {
			return new MenigaComment(source);
		}

		public MenigaComment[] newArray(int size) {
			return new MenigaComment[size];
		}
	};

	protected static MenigaCommentOperations apiOperator;

	protected long id;
	protected long transactionId;
	protected long personId;
	protected String comment;
	protected DateTime createdDate;

	protected MenigaComment() {
	}

	protected MenigaComment(Parcel in) {
		this.id = in.readLong();
		this.personId = in.readLong();
		this.comment = in.readString();
		this.createdDate = new DateTime(in.readLong());
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaCommentOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaCommentOperations operator) {
		MenigaComment.apiOperator = operator;
	}

	/**
	 * @return The id of the comment.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The comment string
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * Updates the comment string in this comment object.
	 *
	 * @param comment The new comment.
	 */
	public void setComment(String comment) {
		if (this.hasChanged(this.comment, comment)) {
			return;
		}
		this.changed();
		this.comment = comment;
	}

	/**
	 * @return The transaction id that the comment belongs to.
	 */
	public long getTransactionId() {
		return this.transactionId;
	}

	/**
	 * Sets a transaction id for the comment. This field can't be persisted to the API.
	 *
	 * @param transactionId the transaction id.
	 */
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return The Id of the person who created the comment.
	 */
	public long getPersonId() {
		return this.personId;
	}

	/**
	 * @return The creation date of the comment.
	 */
	public DateTime getCreatedDate() {
		return this.createdDate;
	}

	@Override
	public MenigaComment clone() throws CloneNotSupportedException {
		return (MenigaComment) super.clone();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.personId);
		dest.writeString(this.comment);
		dest.writeLong(this.createdDate.getMillis());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaComment that = (MenigaComment) o;

		if (id != that.id) {
			return false;
		}
		if (personId != that.personId) {
			return false;
		}
		if (comment != null ? !comment.equals(that.comment) : that.comment != null) {
			return false;
		}
		return createdDate != null ? createdDate.equals(that.createdDate) : that.createdDate == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (personId ^ (personId >>> 32));
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		return result;
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaComment)) {
			return;
		}

		MenigaComment other = (MenigaComment) lastRevision;
		this.comment = other.comment;
	}

	/*
	--- API calls below ---
	 */

	/**
	 * Updates the text of this comment object. All hashtagged text (such as #tag) will cause a tag
	 * object to be created. The removal of a hashtagged text will cause the tag that did exist to be
	 * deleted.
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> update() {
		return MenigaComment.apiOperator.updateComment(this);
	}

	/**
	 * Delets this comment object
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return MenigaComment.apiOperator.deleteComment(this.transactionId, this.id);
	}

	/**
	 * Creates a new comment for a given transaction. All text in the comment that starts with the #
	 * character and ends with a space will cause a tag to be created (if the tag does not already
	 * exist)
	 *
	 * @param transactionId The id of the transaction to which this comment belongs
	 * @param comment       The text of the comment, hashtagged text (such as #tag) will cause a tag object
	 *                      to be created.
	 * @return The new comment object
	 */
	public static Result<MenigaComment> create(final long transactionId, final String comment) {
		Result<MenigaComment> task = MenigaComment.apiOperator.createComment(transactionId, comment);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaComment>() {
			@Override
			public void onFinished(MenigaComment result, boolean failed) {
				if (!failed && result != null) {
					result.transactionId = transactionId;
				}
			}
		});
	}
}
