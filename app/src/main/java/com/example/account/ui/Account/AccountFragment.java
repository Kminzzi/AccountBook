package com.example.account.ui.Account;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.account.MainActivity;
import com.example.account.R;
import com.example.account.databinding.FragmentAccountBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class AccountFragment extends Fragment {

    private static final int MODE_PRIVATE = 1;
    private FragmentAccountBinding binding;

    CalendarView calView;
    TextView tv;
    int selectYear, selectMonth, selectDay;
    String filename;

    EditText editText, editText2;
    View dialogView;

    static int income = 0;
    static int expense = 0;
    static int balance = 0;

    myDBHelper myDBHelper;
    EditText edtName, edtNumber, edtNameResultm, edtNumberResult;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqlDB;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calView = root.findViewById(R.id.calendarView);
        tv = root.findViewById(R.id.textView);

        edtName = (EditText) root.findViewById(R.id.edtName);
        edtNumber = (EditText) root.findViewById(R.id.edtNumber);
        edtNameResultm = (EditText) root.findViewById(R.id.edtNameResult);
        edtNumberResult = (EditText) root.findViewById(R.id.edtNumberResult);
        btnInit = (Button) root.findViewById(R.id.btnInit);
        btnInsert = (Button) root.findViewById(R.id.btnInsert);
        btnSelect = (Button) root.findViewById(R.id.btnSelect);

        myDBHelper = new myDBHelper(getActivity());
//        btnInit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sqlDB = myDBHelper.getWritableDatabase();
//                myDBHelper.onUpgrade(sqlDB,1,2);
//                sqlDB.close();
//            }
//        });


        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int datOfMonth) {
                selectYear = year;
                selectMonth = month + 1;    //시스템 상에서는 month가 1 작게 나오기 때문
                selectDay = datOfMonth;


                filename = Integer.toString(selectYear) + "년"
                        + Integer.toString(selectMonth) + "월"
                        + Integer.toString(selectDay) + "일";


                dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
                editText = dialogView.findViewById(R.id.editText);
                editText2 = dialogView.findViewById(R.id.editText2);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("가계부 쓰기");
                dlg.setView(dialogView);
                dlg.setIcon(R.drawable.testicon);
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int etIncome = Integer.parseInt(editText.getText().toString());
                        int etExpense = Integer.parseInt(editText2.getText().toString());
                        income = income + etIncome;
                        expense = expense + etExpense;
                        balance = income - expense;

                        String str = "수입 합계 : " + income + "\n"
                                + "지출 합계 : " + expense + "\n"
                                + "잔액 : " + balance;

                        String writeStr = "수입 : " + etIncome + "\n" + "지출 : " + etExpense;
                        tv.setText(str);
                    }
                });
                dlg.show();
            }
        });

        return root;
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) PRIMARY KEY,gNumber INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);

        }
    }
}