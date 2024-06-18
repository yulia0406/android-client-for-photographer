package com.example.diplom.ui.login;

import androidx.annotation.Nullable;

import com.example.diplom.data.model.RegistredInUser;

public class RegistrationResult {
    @Nullable
    private RegistredInUser success;
    @Nullable
    private Integer error;

    RegistrationResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistrationResult(@Nullable RegistredInUser success) {
        this.success = success;
    }

    @Nullable
    RegistredInUser getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
