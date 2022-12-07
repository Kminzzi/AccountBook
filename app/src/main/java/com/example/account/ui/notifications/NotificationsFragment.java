package com.example.account.ui.notifications;

import android.os.Bundle;

import com.example.account.CustomAdapterSetting;
import com.example.account.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NotificationsFragment extends Fragment {

    ListView lst;
    String name[] = {"About Mango Bank", "Rate Me", "Refer Mango Bank To Your Friend", "Email Your Feedback", "Report a bug"};
    String num[] = {"Version 1.0", "Application", "Share this app with friends", "Tell me your suggestions", "Tell me if you found any problem"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        v = inflater.inflate(R.layout.fragment_notifications, container, false);

        lst = v.findViewById(R.id.listView);
        CustomAdapterSetting adapter = new CustomAdapterSetting(getActivity(), name, num);
        lst.setAdapter(adapter);

        return v;
    }
}