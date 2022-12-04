package com.example.account;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.account.databinding.FragmentAccountBinding;
import com.example.account.ui.Account.AccountViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddFragment extends Fragment {

    EditText edtDate, edtCard, edtClass, edtAmount, edtContent;
    Button saveBtn;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };



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
                myDb.addBook(Date.valueOf(edtDate.getText().toString().trim()), edtCard.getText().toString().trim(), edtClass.getText().toString().trim()
                        ,Integer.parseInt(edtAmount.getText().toString().trim()), edtContent.getText().toString().trim());
            }
        });


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return layout;
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

//        EditText edtDate = (EditText)findViewById(R.id.edtDate);
        edtDate.setText(sdf.format(myCalendar.getTime()));
    }
}