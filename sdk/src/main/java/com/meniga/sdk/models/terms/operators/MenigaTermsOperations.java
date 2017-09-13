package com.meniga.sdk.models.terms.operators;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.terms.MenigaTermType;
import com.meniga.sdk.models.terms.MenigaTerms;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public interface MenigaTermsOperations {
	Result<List<MenigaTerms>> getTerms(String culture);

    Result<List<MenigaTermType>> getTermTypes(String culture);

    Result<Void> acceptTerms(long typeId);

    Result<Void> declineTerms(long typeId);
}
