package com.example.diplom;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.diplom.ui.ProcessingCompletionCallback;
import com.example.diplom.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.widget.Toolbar;

import com.example.diplom.databinding.ActivityPhotographmenuBinding;

public class photographmenuActivity extends AppCompatActivity {
    private String client = "";
    //Context context;

    private ActivityPhotographmenuBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //context = this;
        binding = ActivityPhotographmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            client = (String)bundle.get("client");
        }
        Log.d("MSGcrrrrrrrrrrrrrr", client);

        //Bundle args = new Bundle();
        //args.putString("user", client);
        //dashboardFragment.setArguments(args);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      //  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
       //         R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        //        .build();
       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_photographmenu);
        //setSupportActionBar(binding.toolbar);
        NavigationUI.setupWithNavController(navView, navController);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }





    public String getUser()
    {
        //this.listener = null;



        return client;
    }

}