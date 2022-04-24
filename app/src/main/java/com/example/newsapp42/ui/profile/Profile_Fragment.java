package com.example.newsapp42.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.newsapp42.databinding.FragmentProfileBinding;
import com.example.newsapp42.utils.Prefs;

public class Profile_Fragment extends Fragment {
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> addPhoto;
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    private Uri uri;
    private Prefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (prefs.getImageUri() != null) {
            uri = Uri.parse(prefs.getImageUri());
        }
        Glide.with(requireContext()).load(uri).circleCrop().into(binding.ivProfile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadText();
        binding.ivProfile.setOnClickListener(view1 -> loadAvatar());
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        uri = result.getData().getData();
                        prefs.saveImage(String.valueOf(uri));
                        binding.ivProfile.setImageURI(uri);
                    }
                }
            });


    private void saveAvatar() {
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("KEYING141", binding.ivProfile.getId());
        editor.apply();
    }

    private void loadAvatar() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    private void saveText() {
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("KEYING14", binding.nickname.getText().toString());
        editor.apply();
    }

    private void loadText() {
        mSettings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String savedText = mSettings.getString("KEYING14", "");
        binding.nickname.setText(savedText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveAvatar();
        saveText();
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(requireContext()).load(uri).centerCrop().into(binding.ivProfile);
    }
}