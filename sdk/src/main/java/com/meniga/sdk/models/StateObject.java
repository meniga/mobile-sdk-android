package com.meniga.sdk.models;

import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.helpers.Objects;

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

	protected <T> boolean hasChanged(T par1, T par2) {
		return !Objects.equals(par1, par2);
	}
}
