package com.example.diplom.data;

import com.example.diplom.data.model.RegistredInUser;

public class RegistrationRepository {
    private static volatile RegistrationRepository instance;

    private RegistrationDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RegistredInUser user = null;

    // private constructor : singleton access
    private RegistrationRepository(RegistrationDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegistrationRepository getInstance(RegistrationDataSource dataSource) {
        if (instance == null) {
            instance = new RegistrationRepository(dataSource);
        }
        return instance;
    }

    public boolean isRegistered() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setRegisteredUser(RegistredInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<RegistredInUser> register(String username, String password, String adress, String phone, String email, String FIO, String post) {
        // handle login
        Result<RegistredInUser> result = dataSource.register(username, password, adress, phone, email, FIO, post);
        if (result instanceof Result.Success) {
            setRegisteredUser(((Result.Success<RegistredInUser>) result).getData());
        }
        return result;
    }
}
