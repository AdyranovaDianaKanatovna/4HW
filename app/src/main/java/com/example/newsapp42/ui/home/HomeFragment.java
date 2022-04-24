package com.example.newsapp42.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsapp42.App;
import com.example.newsapp42.R;
import com.example.newsapp42.databinding.FragmentHomeBinding;
import com.example.newsapp42.models.Article;
import com.example.newsapp42.utils.OnItemClickListener;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private Article article;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new NewsAdapter();
        adapter.addItems(App.getAppDatabase().newsDao().getAll());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNews();
        initAlertDialog();
    }

    private void initAlertDialog() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Удалить новость");
                alert.setMessage("Вы действительно хотитие удалить новость.");
                alert.setPositiveButton("Удалить", (dialog, withButton) -> {
                    article = adapter.getItem(position);
                    if (article != null) {
                        Toast.makeText(requireContext(), "sdasda" + position, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("BTNGO", position);
                        adapter.remove(position);
                        binding.recyclerView.setAdapter(adapter);
                        App.getAppDatabase().newsDao().delete(article);
                    }
                });
                alert.setNegativeButton("Отмена", (dialog, withButton) -> {
                    dialog.cancel();
                });
                alert.show();
            }
        });
    }

    private void initNews() {
        binding.fab.setOnClickListener(view1 -> openFragment());
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), (requestKey, result) -> {
            article = (Article) result.getSerializable("article");
            Log.e("Home", "result =" + article.getDate());
            adapter.setNewsArrayList(article);
            binding.recyclerView.setAdapter(adapter);
        });
        binding.recyclerView.setAdapter(adapter);
    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_news);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            requireActivity().finish();
        }
        if (item.getItemId() == R.id.action_search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapter.setNewsArrayList(App.getAppDatabase().newsDao().getSearch(s));
                    binding.recyclerView.setAdapter(adapter);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.setNewsArrayList(App.getAppDatabase().newsDao().getSearch(s));
                    binding.recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }
        if (item.getItemId() == R.id.action_clear) {
        }
        if (item.getItemId() == R.id.action_sort) {
            adapter.setNewsArrayList(App.getAppDatabase().newsDao().sort());
            binding.recyclerView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }
}