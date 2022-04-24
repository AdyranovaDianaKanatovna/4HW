package com.example.newsapp42;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.newsapp42.databinding.ActivityMainBinding;
import com.example.newsapp42.utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private final ArrayList<Integer> integerArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNavController();
        logicBoard(navController);
    }

    private void initNavController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        /*navController.addOnDestinationChangedListener(((controller, destination, argument) -> {
            if (integerArrayList.contains(destination.getId())) {
                binding.navView.setVisibility(View.GONE);
            } else binding.navView.setVisibility(View.VISIBLE);
            if (destination.getId() == R.id.navigation_board) getSupportActionBar().hide();
            else getSupportActionBar().show();
        }));*/
    }

    private void logicBoard(NavController navController) {
        Prefs prefs = new Prefs(this);
        if (!prefs.isBoardShown()) {
            navController.navigate(R.id.navigation_board);
            prefs.savedBoardState();
        } else {
            navController.navigate(R.id.navigation_home);
        }
    }
}