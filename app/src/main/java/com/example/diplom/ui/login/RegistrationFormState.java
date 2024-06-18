package com.example.diplom.ui.login;

import androidx.annotation.Nullable;

public class RegistrationFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer cityError;
    @Nullable
    private Integer FIOError;
    @Nullable
    private Integer postError;
    private boolean isDataValid;
;

    RegistrationFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer emailError, @Nullable Integer phoneError, @Nullable Integer cityError, @Nullable Integer FIOError, @Nullable Integer postError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.cityError = cityError;
        this.FIOError = FIOError;
        this.emailError = emailError;
        this.phoneError = phoneError;
        this.postError = postError;
        this.isDataValid = false;
    }

    RegistrationFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.cityError = null;
        this.FIOError = null;
        this.emailError = null;
        this.phoneError = null;
        this.postError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getPostError() {
        return postError;
    }

    @Nullable
    Integer getCityError() {
        return cityError;
    }

    @Nullable
    Integer getFIOError() {
        return FIOError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPhoneError() {
        return phoneError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
