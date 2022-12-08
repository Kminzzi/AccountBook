package com.example.account;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddFragment extends Fragment {

    EditText edtDate, edtAmount, edtContent;
    Spinner edtCard, edtClass;
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
        edtCard = (Spinner) layout.findViewById(R.id.edtCard);
        edtClass = (Spinner) layout.findViewById(R.id.edtClass);
        edtAmount = (EditText) layout.findViewById(R.id.edtAmount);
        edtContent = (EditText) layout.findViewById(R.id.edtContent);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Card, android.R.layout.simple_spinner_dropdown_item);
        //R.array.test는 저희가 정의해놓은 1월~12월 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtCard.setAdapter(monthAdapter); //어댑터에 연결해줍니다.

        ArrayAdapter ClassAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Class, android.R.layout.simple_spinner_dropdown_item);
        //R.array.test는 저희가 정의해놓은 1월~12월 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        ClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtClass.setAdapter(ClassAdapter); //어댑터에 연결해줍니다.

        saveBtn = (Button) layout.findViewById(R.id.saveBtn);

        Spinner edtCard = (Spinner) layout.findViewById(R.id.edtCard);
        String date = edtCard.getSelectedItem().toString();

        Spinner edtClass = (Spinner) layout.findViewById(R.id.edtClass);
        String classfication = edtClass.getSelectedItem().toString();



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(getActivity());
                myDb.addBook(Date.valueOf(edtDate.getText().toString().trim()), date, classfication
                        ,Integer.parseInt(edtAmount.getText().toString().trim()), edtContent.getText().toString().trim());
            }
        });


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        edtClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return layout;
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

//        EditText edtDate = (EditText)findViewById(R.id.edtDate);
        edtDate.setText(sdf.format(myCalendar.getTime()));
    }
}