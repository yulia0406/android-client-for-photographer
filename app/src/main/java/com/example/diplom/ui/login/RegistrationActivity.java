package com.example.diplom.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom.R;
import com.example.diplom.databinding.ActivityRegistrationBinding;

import com.example.diplom.RegistrationCallback;
import com.example.diplom.ServerClient;
import java.security.MessageDigest;
//import com.example.diplom.data.model.RegistredInUser;


public class RegistrationActivity extends AppCompatActivity {

    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registrationViewModel = new ViewModelProvider(this, new RegistrationViewModelFactory())
                .get(RegistrationViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText emailEditText = binding.email;
        final EditText cityEditText = binding.city;
        final EditText phoneEditText = binding.phone;
        final EditText FIOEditText = binding.FIO;
        final Button loginButton = binding.login;
        final Switch clientSwitch = binding.client;
        final Switch photographerSwitch = binding.photographer;
        final ProgressBar loadingProgressBar = binding.loading;

        registrationViewModel.getRegistrationFormState().observe(this, new Observer<RegistrationFormState>() {
            @Override
            public void onChanged(@Nullable RegistrationFormState registrationFormState) {
                if (registrationFormState == null) {
                    return;
                }
                loginButton.setEnabled(registrationFormState.isDataValid());
                if (registrationFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registrationFormState.getUsernameError()));
                }
                if (registrationFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registrationFormState.getPasswordError()));
                }
                if (registrationFormState.getCityError() != null) {
                    cityEditText.setError(getString(registrationFormState.getCityError()));
                }
                if (registrationFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registrationFormState.getEmailError()));
                }
                if (registrationFormState.getFIOError() != null) {
                    FIOEditText.setError(getString(registrationFormState.getFIOError()));
                }
                if (registrationFormState.getPhoneError() != null) {
                    phoneEditText.setError(getString(registrationFormState.getPhoneError()));
                }
                if(registrationFormState.getPostError() != null)
                {
                    //clientSwitch.setError(getString(registrationFormState.getPostError()));
                    //photographerSwitch.setError(getString(registrationFormState.getPostError()));
                }
            }
        });

        registrationViewModel.getRegistrationResult().observe(this, new Observer<RegistrationResult>() {
            @Override
            public void onChanged(@Nullable RegistrationResult registrationResult) {
                if (registrationResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registrationResult.getError() != null) {
                    showLoginFailed(registrationResult.getError());
                }
                if (registrationResult.getSuccess() != null) {
                    //updateUiWithUser(registrationResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean client = clientSwitch.isChecked();
                boolean photographer = photographerSwitch.isChecked();
                String s1 = "";
                if(client == true && photographer == false)
                {
                    s1 = "Клиент";
                } else if (photographer == true && client == false) {
                    s1 = "Фотограф";
                }else
                    s1 = null;
                registrationViewModel.registrationDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), cityEditText.getText().toString(),
                        emailEditText.getText().toString(), FIOEditText.getText().toString(), phoneEditText.getText().toString(), s1);
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        FIOEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        cityEditText.addTextChangedListener(afterTextChangedListener);
        phoneEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    boolean client = clientSwitch.isChecked();
                    boolean photographer = photographerSwitch.isChecked();
                    String s1 = "";
                    if(client == true && photographer == false)
                    {
                        s1 = "Клиент";
                    } else if (photographer == true && client == false) {
                        s1 = "Фотограф";
                    }else
                        s1 = null;
                    registrationViewModel.registration(usernameEditText.getText().toString(),
                            PasswordHashing.hashPassword(passwordEditText.getText().toString()), cityEditText.getText().toString(),
                            emailEditText.getText().toString(), FIOEditText.getText().toString(), phoneEditText.getText().toString(), s1);
                    connect(usernameEditText.getText().toString(),
                            PasswordHashing.hashPassword(passwordEditText.getText().toString()), cityEditText.getText().toString(),
                            emailEditText.getText().toString(), FIOEditText.getText().toString(),
                            phoneEditText.getText().toString(), s1, null);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                boolean client = clientSwitch.isChecked();
                boolean photographer = photographerSwitch.isChecked();
                String s1 = "";
                if(client == true && photographer == false)
                {
                    s1 = "Клиент";
                } else if (photographer == true && client == false) {
                    s1 = "Фотограф";
                }else
                    s1 = null;
                registrationViewModel.registration(usernameEditText.getText().toString(),
                        PasswordHashing.hashPassword(passwordEditText.getText().toString()),  cityEditText.getText().toString(),
                        emailEditText.getText().toString(), FIOEditText.getText().toString(), phoneEditText.getText().toString(), s1);
                connect(usernameEditText.getText().toString(),
                        PasswordHashing.hashPassword(passwordEditText.getText().toString()), cityEditText.getText().toString(),
                        emailEditText.getText().toString(), FIOEditText.getText().toString(),
                        phoneEditText.getText().toString(), s1, null);;
            }
        });
    }

    private void connect(String username, String password, String city, String email, String FIO, String phone, String post, String description)
    {
        ServerClient serverClient = new ServerClient();

        serverClient.register(username,password, FIO, email, city, phone, post, description,
                new RegistrationCallback() {
                    @Override
                    public void onSucces(String str) {
                        Log.d("MSG","onSucces");
                        Log.d("MSG",str);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                            }
                        });

                        //message = str;
                    }

                    @Override
                    public void onFailure(int error) {
                        Log.d("MSG","onFailure");
                    }

                });

        Intent intentregistration = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intentregistration);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}