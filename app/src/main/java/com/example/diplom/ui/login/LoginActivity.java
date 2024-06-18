package com.example.diplom.ui.login;

import android.app.Activity;

import androidx.fragment.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.diplom.R;
import com.example.diplom.ServerClient;
import com.example.diplom.clientmenuActivity;
import com.example.diplom.photographmenuActivity;
import com.example.diplom.ui.dashboard.DashboardFragment;
import com.example.diplom.ui.login.LoginViewModel;
import com.example.diplom.ui.login.LoginViewModelFactory;
import com.example.diplom.databinding.ActivityLoginBinding;
import com.example.diplom.LoginCallback;
import com.example.diplom.RegistrationCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    ServerClient serverClient = new ServerClient();

    private String post = "";
    private String client;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button registrationbutton = binding.button;
        final ProgressBar loadingProgressBar = binding.loading;


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //updateUiWithUser();
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(usernameEditText.getText().toString().isEmpty() && passwordEditText.getText().toString().isEmpty())
                    {
                        return false;
                    }
                    else{
                        loginViewModel.login(usernameEditText.getText().toString(),
                                PasswordHashing.hashPassword(passwordEditText.getText().toString()), new LoginCallback() {
                                    @Override
                                    public void onSucces() {
                                        connect(usernameEditText.getText().toString(),
                                                PasswordHashing.hashPassword(passwordEditText.getText().toString()), new LoginCallback() {
                                                    @Override
                                                    public void onSucces() {

                                                    }

                                                    @Override
                                                    public void onFailure() {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                    }

                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        PasswordHashing.hashPassword(passwordEditText.getText().toString()), new LoginCallback() {
                            @Override
                            public void onSucces() {
                                connect(usernameEditText.getText().toString(),
                                        PasswordHashing.hashPassword(passwordEditText.getText().toString()), new LoginCallback() {
                                            @Override
                                            public void onSucces() {
                                                serverClient.setserverListener(new ServerClient.serverListener() {
                                                    @Override
                                                    public void onDataDownloaded() {
                                                        if(post.equals("Фотограф"))
                                                        {
                                                            Log.d("MSGUSER", client);

                                                           // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                                                            Intent intentlogin = new Intent(LoginActivity.this, photographmenuActivity.class);
                                                            intentlogin.putExtra("client", client);
                                                            startActivity(intentlogin);

                                                        } else if (post.equals("Клиент") ) {

                                                            Intent intentlogin = new Intent(LoginActivity.this, clientmenuActivity.class);
                                                            intentlogin.putExtra("client", client);
                                                            startActivity(intentlogin);
                                                        }
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure() {

                                            }
                                        });
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
            }
        });

        registrationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);

                Intent intentregistration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intentregistration);
            }

        });
    }

    private void connect(String username, String password, LoginCallback loginCallback) {

        serverClient.logIn(username,
                password, new RegistrationCallback() {
                    @Override
                    public void onSucces(String str) {
                        try {
                            Log.d("MSGloginsucces1", str);
                            client = str;
                            post = new JSONObject(str).getString("post");

                            Log.d("MSGloginsucces1", post);
                            //post = Integer.parseInt(str);
                            //Intent intentlogin = new Intent(LoginActivity.this, LoginActivityRegistration.class);
                            //startActivity(intentlogin);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUiWithUser();
                                }
                            });
                            loginCallback.onSucces();
                        } catch (JSONException e) {

                        }

                    }

                    @Override
                    public void onFailure(int error) {
                        Intent intentlogin2 = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intentlogin2);
                        Log.d("MSGlogin", "onFailure");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                failUiWithUser();
                            }
                        });

                    }
                });

    }

    private void failUiWithUser() {
        //String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Неверные логин или пароль. Попробуйте еще раз", Toast.LENGTH_LONG).show();
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome);
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public String getUser()
    {
        return  client;
    }
}