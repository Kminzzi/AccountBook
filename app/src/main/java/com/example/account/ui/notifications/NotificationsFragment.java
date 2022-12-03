package com.example.account.ui.notifications;

import android.os.Bundle;

import com.example.account.R;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

public class NotificationsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        EditTextPreference signaturePreference = findPreference("signature");
    }
}