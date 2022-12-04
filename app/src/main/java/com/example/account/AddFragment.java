package com.example.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.account.databinding.FragmentAccountBinding;
import com.example.account.ui.Account.AccountViewModel;


public class AddFragment extends Fragment {

    EditText edtDate, edtCard, edtClass, edtAmount, edtContent;
    Button saveBtn;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public AddFragment() {

    }

    public static AddFragment newInstance(int index) {
        AddFragment fragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_add, container, false);

        edtDate = (EditText) layout.findViewById(R.id.edtDate);
        edtCard = (EditText) layout.findViewById(R.id.edtCard);
        edtClass = (EditText) layout.findViewById(R.id.edtClass);
        edtAmount = (EditText) layout.findViewById(R.id.edtAmount);
        edtContent = (EditText) layout.findViewById(R.id.edtContent);

        saveBtn = (Button) layout.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(getActivity());
                myDb.addBook(edtDate.getText().toString().trim(), edtCard.getText().toString().trim(), edtClass.getText().toString().trim()
                        ,edtAmount.getText().toString().trim(), edtContent.getText().toString().trim());
            }
        });

        return layout;
    }
}