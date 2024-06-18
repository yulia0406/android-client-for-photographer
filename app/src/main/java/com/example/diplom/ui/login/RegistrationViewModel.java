package com.example.diplom.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diplom.R;
import com.example.diplom.data.RegistrationRepository;
import com.example.diplom.data.Result;
import com.example.diplom.data.model.RegistredInUser;


public class RegistrationViewModel extends ViewModel{
    private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
    private RegistrationRepository registrationRepository;

    RegistrationViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    LiveData<RegistrationFormState> getRegistrationFormState() {
        return registrationFormState;
    }

    LiveData<RegistrationResult> getRegistrationResult() {
        return registrationResult;
    }

    public void registration(String username, String password, String city, String email, String FIO, String phone, String post) {
        // can be launched in a separate asynchronous job
        Result<RegistredInUser> result = registrationRepository.register(username, password, city, email, FIO, phone, post);

        if (result instanceof Result.Success) {
            RegistredInUser data = ((Result.Success<RegistredInUser>) result).getData();
            registrationResult.setValue(new RegistrationResult(new RegistredInUser(data.getDisplayName(), data.getLogin(), data.getPassword(), data.getCity(), data.getEmail(), data.getFIO(), data.getPhone(), data.getPost())));
        } else {
            registrationResult.setValue(new RegistrationResult(R.string.registration_failed));
        }
    }

    public void registrationDataChanged(String username, String password, String adress, String email, String FIO, String phone, String post) {
        if (!isUserNameValid(username)) {
            registrationFormState.setValue(new RegistrationFormState(R.string.invalid_username, null, null, null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registrationFormState.setValue(new RegistrationFormState(null, R.string.invalid_password, null, null, null, null, null));
        }else if (!isPhoneValid(phone)){
            registrationFormState.setValue(new RegistrationFormState(null, null, null, R.string.invalid_phone, null, null, null));
        }else if (!isAddressValid(adress)){
            registrationFormState.setValue(new RegistrationFormState(null, null, null, null, R.string.invalid_address, null, null));
        }else if (!isFIOValid(FIO)){
            registrationFormState.setValue(new RegistrationFormState(null, null, null, null, null, R.string.invalid_FIO, null));
        }else if (!isEmailValid(email)){
            registrationFormState.setValue(new RegistrationFormState(null, null, R.string.invalid_email, null, null, null, null));
        }else if(!isPostValid(post)){
            registrationFormState.setValue(new RegistrationFormState(null, null,null, null, null, null, R.string.invalid_post));
        }
        else {
            registrationFormState.setValue(new RegistrationFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
    private boolean isPostValid(String post) {
        return post != null && !post.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isPhoneValid(String phone)
    {
        String phoneNumberPattern = "^[+]?[0-9]{10,13}$";
        String input = phone.trim();
        return phone != null && input.matches(phoneNumberPattern);


    }

    private boolean isFIOValid(String FIO)
    {
        return FIO != null && !FIO.trim().isEmpty();
    }

    private boolean isAddressValid(String address)
    {
        return address != null && !address.trim().isEmpty();
    }

    private boolean isEmailValid(String email)
    {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
