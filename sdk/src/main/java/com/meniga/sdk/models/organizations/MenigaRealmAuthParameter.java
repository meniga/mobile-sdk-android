package com.meniga.sdk.models.organizations;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.helpers.KeyVal;
import com.meniga.sdk.models.organizations.enums.AuthParameterType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRealmAuthParameter implements Parcelable, Serializable {
    private String name;
    private String displayName;
    private String regularExpression;
    private Integer minLength;
    private Integer maxLength;
    private boolean isPassword;
    private boolean isHidden;
    private boolean isDropDown;
    private String dropDownValues;
    private Long parentId;
    private boolean canSave;
    private boolean isEncrypted;
    private boolean isIdentity;
    private transient String selectedValue;

    public static class SimpleAuthParameter {
        public String name;
        public String value;
    }

    protected MenigaRealmAuthParameter() {
    }

    protected MenigaRealmAuthParameter(Parcel in) {
        this.name = in.readString();
        this.displayName = in.readString();
        this.regularExpression = in.readString();
        this.minLength = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxLength = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isPassword = in.readByte() != 0;
        this.isHidden = in.readByte() != 0;
        this.isDropDown = in.readByte() != 0;
        this.dropDownValues = in.readString();
        this.parentId = (Long) in.readValue(Long.class.getClassLoader());
        this.canSave = in.readByte() != 0;
        this.isEncrypted = in.readByte() != 0;
        this.isIdentity = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setSelectedValue(String val) {
        selectedValue = val;
    }

    public AuthParameterType getAuthParameterType() {
        if (isDropDown) {
            return AuthParameterType.MULTI_SELECT;
        }
        if (name.equals("message") || name.equals("otp_message") || name.equals("captcha_message")) {
            return AuthParameterType.MESSAGE;
        } else if (name.equals("username")) {
            return AuthParameterType.USERNAME;
        } else if (name.equals("password")) {
            return AuthParameterType.PASSWORD;
        } else if (name.equals("otp")) {
            return AuthParameterType.AUTHENTICATION_METHOD_OTP;
        }
        String[] parts = displayName.split("\\|");
        switch (parts[0]) {
            case "@Username":
                return AuthParameterType.USERNAME;
            
            case "@AuthMethod":
                return AuthParameterType.MULTI_SELECT;

            case "@AuthMethodTodos":
                return AuthParameterType.AUTHENTICATION_METHOD_TODOS;

            case "@AuthMethodSMS":
                return AuthParameterType.AUTHENTICATION_METHOD_SMS;

            case "@PhoneNumber":
                return AuthParameterType.PHONE_NUMBER;

            default:
                return AuthParameterType.UNKNOWN;
        }
    }

    public SimpleAuthParameter getSimpleParameter() {
        SimpleAuthParameter par = new SimpleAuthParameter();
        par.name = name;
        par.value = selectedValue;
        return par;
    }

    public String getDisplayName() {
        if (displayName.contains("|")) {
            String[] splt = displayName.split("\\|");
            return splt[splt.length - 1];
        }
        return displayName;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public boolean isPassword() {
        return isPassword;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isDropDown() {
        return isDropDown;
    }

    public List<KeyVal<String, String>> getDropDownValues() {
        JsonParser parser = new JsonParser();
        JsonElement elements = parser.parse(dropDownValues);
        JsonArray values = elements.getAsJsonArray();
        List<KeyVal<String, String>> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            JsonObject item = values.get(i).getAsJsonObject();
            KeyVal<String, String> entry = new KeyVal<>(item.get("Name").getAsString(), item.get("Value").getAsString());
            entries.add(entry);
        }
        return entries;
    }

    public Long getParentId() {
        return parentId;
    }

    public boolean isCanSave() {
        return canSave;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public boolean isIdentity() {
        return isIdentity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenigaRealmAuthParameter that = (MenigaRealmAuthParameter) o;

        if (isPassword != that.isPassword) {
            return false;
        }
        if (isHidden != that.isHidden) {
            return false;
        }
        if (isDropDown != that.isDropDown) {
            return false;
        }
        if (canSave != that.canSave) {
            return false;
        }
        if (isEncrypted != that.isEncrypted) {
            return false;
        }
        if (isIdentity != that.isIdentity) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) {
            return false;
        }
        if (regularExpression != null ? !regularExpression.equals(that.regularExpression) : that.regularExpression != null) {
            return false;
        }
        if (minLength != null ? !minLength.equals(that.minLength) : that.minLength != null) {
            return false;
        }
        if (maxLength != null ? !maxLength.equals(that.maxLength) : that.maxLength != null) {
            return false;
        }
        if (dropDownValues != null ? !dropDownValues.equals(that.dropDownValues) : that.dropDownValues != null) {
            return false;
        }
        return parentId != null ? parentId.equals(that.parentId) : that.parentId == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (regularExpression != null ? regularExpression.hashCode() : 0);
        result = 31 * result + (minLength != null ? minLength.hashCode() : 0);
        result = 31 * result + (maxLength != null ? maxLength.hashCode() : 0);
        result = 31 * result + (isPassword ? 1 : 0);
        result = 31 * result + (isHidden ? 1 : 0);
        result = 31 * result + (isDropDown ? 1 : 0);
        result = 31 * result + (dropDownValues != null ? dropDownValues.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (canSave ? 1 : 0);
        result = 31 * result + (isEncrypted ? 1 : 0);
        result = 31 * result + (isIdentity ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.displayName);
        dest.writeString(this.regularExpression);
        dest.writeValue(this.minLength);
        dest.writeValue(this.maxLength);
        dest.writeByte(this.isPassword ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHidden ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDropDown ? (byte) 1 : (byte) 0);
        dest.writeString(this.dropDownValues);
        dest.writeValue(this.parentId);
        dest.writeByte(this.canSave ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEncrypted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isIdentity ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<MenigaRealmAuthParameter> CREATOR = new Parcelable.Creator<MenigaRealmAuthParameter>() {
        @Override
        public MenigaRealmAuthParameter createFromParcel(Parcel source) {
            return new MenigaRealmAuthParameter(source);
        }

        @Override
        public MenigaRealmAuthParameter[] newArray(int size) {
            return new MenigaRealmAuthParameter[size];
        }
    };
}
