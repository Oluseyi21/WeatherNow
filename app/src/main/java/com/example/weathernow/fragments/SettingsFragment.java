package com.example.weathernow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.widget.Toast;
import com.example.weathernow.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.weathernow.BuildConfig;
import com.example.weathernow.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.appVersion.setText(
                getString(R.string.app_version, BuildConfig.VERSION_NAME)
        );

        // Send Feedback
        binding.feedbackButton.setOnClickListener(view -> {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@weathernow.com"));
            emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "WeatherNow App Feedback"
            );

            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        requireContext(),
                        "No email application found.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // View on GitHub
        binding.websiteButton.setOnClickListener(view -> {

            Intent websiteIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/Oluseyi21/WeatherNow.git")
            );

            try {
                startActivity(websiteIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        requireContext(),
                        "No browser application found.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        // Share App
        binding.shareButton.setOnClickListener(view -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out WeatherNow, your personal city weather app!"
            );
            Intent chooser = Intent.createChooser(
                    shareIntent,
                    "Share WeatherNow"
            );
            try {
                startActivity(chooser);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        requireContext(),
                        "No sharing application found.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
