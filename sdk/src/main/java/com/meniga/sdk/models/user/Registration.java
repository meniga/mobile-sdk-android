package com.meniga.sdk.models.user;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class Registration {

	private final String email;
	private final String password;
	private final String culture;
	private final String signupToken;

	private Registration(@Nonnull String email, @Nonnull String password, @Nonnull String culture, @Nullable String signupToken) {
		this.email = requireNonNull(email);
		this.password = requireNonNull(password);
		this.culture = requireNonNull(culture);
		this.signupToken = signupToken;
	}

	public static Builder builder() {
		return new Builder();
	}

	@Nonnull
	public String getEmail() {
		return email;
	}

	@Nonnull
	public String getPassword() {
		return password;
	}

	@Nonnull
	public String getCulture() {
		return culture;
	}

	@Nullable
	public String getSignupToken() {
		return signupToken;
	}

	public static final class Builder {
		private String email;
		private String password;
		private String culture;
		private String signupToken;

		private Builder() {
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder culture(String culture) {
			this.culture = culture;
			return this;
		}

		public Builder signupToken(String signupToken) {
			this.signupToken = signupToken;
			return this;
		}

		public Registration build() {
			return new Registration(email, password, culture, signupToken);
		}
	}
}
