package com.example.diplom;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.diplom.databinding.ActivityClientmenuBinding;
import com.example.diplom.databinding.ActivityPhotographmenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class clientmenuActivity extends AppCompatActivity {
    private String client = "";
    //Context context;
    private ActivityClientmenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //context = this;
        binding = ActivityClientmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            client = (String)bundle.get("client");
        }

        //Bundle args = new Bundle();
        //args.putString("user", client);
        //dashboardFragment.setArguments(args);
        BottomNavigationView navView = findViewById(R.id.nav_clientview);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      //  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
       //         R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        //        .build();
       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_clientmenu);
        //setSupportActionBar(binding.toolbar);
        NavigationUI.setupWithNavController(navView, navController);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
    public String getUser()
    {
        return  client;
    }
}