package com.example.newsapp42.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp42.App;
import com.example.newsapp42.R;
import com.example.newsapp42.databinding.FragmentHomeBinding;
import com.example.newsapp42.databinding.FragmentNewsBinding;
import com.example.newsapp42.models.Article;

import java.text.DateFormat;
import java.util.Date;


public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(view1 -> save());
    }

    private void save() {
        long millis = System.currentTimeMillis();
        Bundle bundle = new Bundle();
        String text = binding.editText.getText().toString().trim();

        Article article = new Article(text, DateFormat.getDateTimeInstance().format(new Date(millis)) + "   ");
        App.appDatabase.newsDao().insert(article); //Записывает данные в бд
        bundle.putSerializable("article",article);
        getParentFragmentManager().setFragmentResult("rk_news",bundle);

        if (text.isEmpty()){
            Toast.makeText(requireContext(), "Заполните поле", Toast.LENGTH_SHORT).show();
            return;
        }
        close();
    }
    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}