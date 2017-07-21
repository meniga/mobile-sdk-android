package com.meniga.sdk.models;

import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.models.categories.enums.CategoryType;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public abstract class StateObject implements Cloneable {

	private boolean dirty;
	private List<StateObject> revisions = new ArrayList<>();

	protected abstract void revertToRevision(StateObject lastRevision);

	public void revert() {
		this.dirty = false;
		if (this.revisions.size() > 0) {
			this.revertToRevision(this.revisions.get(this.revisions.size() - 1));
			this.revisions.remove(this.revisions.size() - 1);
		}
	}

	protected void changed() {
		if (this.dirty) {
			return;
		}
		if (this.revisions == null) {
			this.revisions = new ArrayList<>();
		}

		try {
			this.revisions.add((StateObject) this.clone());
		} catch (CloneNotSupportedException ex) {
			ErrorHandler.reportAndHandle(ex);
		}
		this.dirty = true;
	}

	protected void resetState() {
		this.dirty = false;
		if (this.revisions.size() > 0) {
			this.revisions.remove(StateObject.this.revisions.size() - 1);
		}
	}

	protected <E> List<E> cloneList(List<E> list) {
		List<E> newList = new ArrayList<>();
		for (E obj : list) {
			newList.add(obj);
		}
		return newList;
	}

	protected boolean hasChanged(Boolean par1, Boolean par2) {
		return par1 == null && par2 == null || (par1 != null && par2 != null && !par1 != par2);
	}

	protected boolean hasChanged(String par1, String par2) {
		return par1 == null && par2 == null || (par1 != null && par2 != null && par1.equals(par2));
	}

	protected boolean hasChanged(List<String> par1, List<String> par2) {
		if (par1 == null || par2 == null) {
			return true;
		}
		if (par1.size() != par2.size()) {
			return false;
		}
		for (int i = 0; i < par1.size(); i++) {
			if (!par1.get(i).equals(par2.get(i))) {
				return false;
			}
		}
		return true;
	}

	protected boolean hasChanged(DateTime par1, DateTime par2) {
		return par1 == null && par2 == null || (par1 != null && par2 != null && par1.equals(par2));
	}

	protected boolean hasChanged(Long par1, Long par2) {
		return par1 == null && par2 == null || (par1 != null && par2 != null && par1.equals(par2));
	}

	protected boolean hasChanged(MenigaDecimal par1, MenigaDecimal par2) {
		return par1 == null && par2 == null || (par1 != null && par2 != null && par1.doubleValue() == par2.doubleValue());
	}

	protected boolean hasChanged(CategoryType par1, CategoryType par2) {
		return !(par1 == null && par2 == null) && (par1 == null || par2 == null || par1 != par2);
	}

	protected boolean hasChanged(int par1, int par2) {
		return par1 == par2;
	}
}
