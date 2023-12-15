package com.davidvignon.googlemaplocationjava.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.davidvignon.googlemaplocationjava.databinding.MainActivityBinding;
import com.davidvignon.googlemaplocationjava.ui.map.MapFragment;
import com.davidvignon.googlemaplocationjava.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        if (savedInstanceState == null) {
            displayFragment(MapFragment.newInstance());
        }
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_FrameLayout_fragment_container, fragment)
                .commitNow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refresh();
    }
}