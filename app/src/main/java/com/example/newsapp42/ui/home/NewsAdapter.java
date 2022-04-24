package com.example.newsapp42.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp42.utils.OnItemClickListener;
import com.example.newsapp42.databinding.ItemNewsBinding;
import com.example.newsapp42.models.Article;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Article> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private ItemNewsBinding binding;

    public void setNewsArrayList(Article article) {
        list.add(article);
        notifyItemInserted(list.size());
    }

    public void setNewsArrayList(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItems(List<Article> list14) {
        list.addAll(list14);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void remove(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public Article getItem(int position) {
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemNewsBinding itemView) {
            super(binding.getRoot());
        }

        public void bind(Article article) {
            binding.textTitle.setText(article.getText());
            binding.dataTv.setText(String.valueOf(article.getDate()));
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(view -> {
                onItemClickListener.onItemLongClick(getAdapterPosition());
                return true;
            });
        }
    }
}
