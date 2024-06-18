package com.example.diplom.data;

import com.example.diplom.data.model.RegistredInUser;

import java.io.IOException;
public class RegistrationDataSource {
    public Result<RegistredInUser> register(String username, String password, String adress, String phone, String email, String FIO, String post) {

        try {
            // TODO: handle loggedInUser authentication
            RegistredInUser fakeUser =
                    new RegistredInUser(
                            java.util.UUID.randomUUID().toString(),
                            username, password, FIO, adress, email, phone, post);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Ошибка регистрации в", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
