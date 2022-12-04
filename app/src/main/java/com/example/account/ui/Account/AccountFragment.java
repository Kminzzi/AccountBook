package com.example.account.ui.Account;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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

import com.example.account.AddActivity;
import com.example.account.MyDatabaseHelper;
import com.example.account.R;
import com.example.account.databinding.FragmentAccountBinding;


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

    EditText edtDate, edtCard, edtClass, edtAmount, edtContent;
    Button btnInit, btnInsert, btnSelect, button;


    MyDatabaseHelper database;
    SQLiteDatabase sql;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calView = root.findViewById(R.id.calendarView);
        tv = root.findViewById(R.id.textView);

//        edtResult = (EditText) root.findViewById(R.id.edtResult);
        button = (Button) root.findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int datOfMonth) {
                selectYear = year;
                selectMonth = month + 1;    //시스템 상에서는 month가 1 작게 나오기 때문
                selectDay = datOfMonth;


                filename = Integer.toString(selectYear) + "년"
                        + Integer.toString(selectMonth) + "월"
                        + Integer.toString(selectDay) + "일";


                database = new MyDatabaseHelper(getActivity());
                sql = database.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM account_user;", null);

                String strDate = "날짜" + "\r\r\n" + "-------" + "\r\r\n";
                String strCard = "자산" + "\r\r\n" + "-------" + "\r\r\n";
                String strClass = "분류" + "\r\r\n" + "-------" + "\r\r\n";
                String strAmount = "금액" + "\r\r\n" + "-------" + "\r\r\n";
                String strContent = "내용" + "\r\r\n" + "-------" + "\r\r\n";

                while (cursor.moveToNext()) {
                    strDate += cursor.getString(0) + "\r\n";
                    strCard += cursor.getString(1) + "\r\n";
                    strClass += cursor.getString(2) + "\r\n";
                    strAmount += cursor.getString(3) + "\r\n";
                    strContent += cursor.getString(4) + "\r\n";
                }

                edtDate.setText(strDate);
                edtCard.setText(strCard);
                edtClass.setText(strClass);
                edtDate.setText(strAmount);
                edtContent.setText(strContent);

                cursor.close();
                sql.close();

            }
        });



//        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int datOfMonth) {
//                selectYear = year;
//                selectMonth = month + 1;    //시스템 상에서는 month가 1 작게 나오기 때문
//                selectDay = datOfMonth;
//
//
//                filename = Integer.toString(selectYear) + "년"
//                        + Integer.toString(selectMonth) + "월"
//                        + Integer.toString(selectDay) + "일";
//
//
//                dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
//                editText = dialogView.findViewById(R.id.editText);
//                editText2 = dialogView.findViewById(R.id.editText2);
//                edtSuResultm = dialogView.findViewById(R.id.edtSuBun);
//                edtJiResult = dialogView.findViewById(R.id.edtJiBun);
//
//                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
//                dlg.setTitle("가계부 쓰기");
//                dlg.setView(dialogView);
//                dlg.setIcon(R.drawable.testicon);
//                dlg.setNegativeButton("취소", null);
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        int etIncome = Integer.parseInt(editText.getText().toString());
//                        int etExpense = Integer.parseInt(editText2.getText().toString());
//                        String etSuBun = new String(edtSuResultm.getText().toString());
//                        String etJiBun = new String(edtJiResult.getText().toString());
//
//                        ContentValues values = new ContentValues();
//                        values.put("int","etIncome");
////                        db.insert("mytable",null,values);
//
//                        income = income + etIncome;
//                        expense = expense + etExpense;
//                        balance = income - expense;
//
//
////                        String str = "수입 합계 : " + income + "\n"
////                                + "지출 합계 : " + expense + "\n"
////                                + "잔액 : " + balance + "\n"
////                                + "분류 : " + etSuBun + "\n"
////                                + "분류 : " + etJiBun;
//
//                        String str = "수입액 : " + etIncome + "\n"
//                                + "분류 : " + etSuBun + "\n"
//                                + "지출액 : " + etExpense + "\n"
//                                + "분류 : " + etJiBun;
//
//                        String writeStr = "수입 : " + etIncome + "\n" + "지출 : " + etExpense;
//                        tv.setText(str);
//                    }
//                });
//                dlg.show();
//            }
//        });

        return root;
    }

}