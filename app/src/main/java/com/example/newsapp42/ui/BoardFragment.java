package com.example.newsapp42.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newsapp42.R;
import com.example.newsapp42.databinding.FragmentBoardBinding;
import com.example.newsapp42.utils.Prefs;
import com.google.android.material.tabs.TabLayoutMediator;

public class BoardFragment extends Fragment implements BoardAdapter.OnClickItem {
    private FragmentBoardBinding binding;
    private BoardAdapter adapter;
    private final String[] tabText = {"", "", ""};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        initClick();
        initView();
        BoardAdapter adapter = new BoardAdapter(this);
        binding.viewPager.setAdapter(adapter);
    }
    private void initClick() {
        binding.buttonSpecial.setOnClickListener(view1 -> onClick(binding.buttonSpecial));
    }

    private void initViewPager() {
        adapter = new BoardAdapter(this);
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabText[position])).attach();
    }

    private void initView() {
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 2) binding.buttonSpecial.setVisibility(View.INVISIBLE);
                else binding.buttonSpecial.setVisibility(View.GONE);
            }
        });
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.savedBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    @Override
    public void onClick(Button button) {
        Toast.makeText(requireContext(), "Работает", Toast.LENGTH_SHORT).show();
        close();
    }
}