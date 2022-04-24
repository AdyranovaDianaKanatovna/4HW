package com.example.newsapp42.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp42.R;
import com.example.newsapp42.databinding.ItemBoardBinding;

@SuppressLint("NotifyDataSetChanged")
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private ItemBoardBinding binding;
    private final String[] titles = new String[]{"Привет", "Hello", "Салам"};
    private final Integer[] listImage = new Integer[]{R.raw.kaguya, R.raw.kaguya, R.raw.kaguya};
    private final String[] listSubTitle = {"SubTitle1", "SubTitle2", "SubTitle3"};
    private OnClickItem onClickItem;

    public BoardAdapter(OnClickItem OnClickItem) {
        this.onClickItem = OnClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
        if (position == 2) binding.button.setVisibility(View.VISIBLE);
        else binding.button.setVisibility(View.INVISIBLE);
        binding.button.setOnClickListener(view -> onClickItem.onClick(binding.button));
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemBoardBinding itemView) {
            super(binding.getRoot());
        }

        public void bind(int position) {
            binding.titleTv.setText(titles[position]);
            binding.boardIv.setAnimation(listImage[position]);
            binding.subTitleTv.setText(listSubTitle[position]);
            new Handler().postDelayed(() -> {}, 5000);
        }
    }

    public interface OnClickItem {
        void onClick(Button button);
    }
}
