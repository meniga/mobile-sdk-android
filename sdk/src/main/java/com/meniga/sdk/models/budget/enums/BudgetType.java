package com.meniga.sdk.models.budget.enums;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public enum BudgetType implements Serializable {
    @SerializedName("planning")
    PLANNING,
    @SerializedName("budget")
    BUDGET,
    @SerializedName("challenges")
    CHALLENGES;

    @Override
    public String toString() {
        switch (this) {

            case CHALLENGES:
                return "challenges";

            case BUDGET:
                return "budget";

            case PLANNING:
                return "planning";

            default:
                return "budget";
        }
    }
}
