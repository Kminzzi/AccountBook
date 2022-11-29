package com.example.account.ui.Account;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        calView = root.findViewById(R.id.calendarView);
        tv = root.findViewById(R.id.textView);

        //SD카드에 READ, WRITE권한 주기
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        //SD카드 경로 지정
        final String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final File myDir = new File(sdpath + "/Account");
        myDir.mkdir();	//sd카드에 Account폴더 생성

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int datOfMonth) {
                selectYear = year;
                selectMonth = month + 1;	//시스템 상에서는 month가 1 작게 나오기 때문
                selectDay = datOfMonth;


                filename = Integer.toString(selectYear) + "년"
                        + Integer.toString(selectMonth) + "월"
                        + Integer.toString(selectDay) + "일";
                String path = sdpath + "/Account/" + filename;
                File files = new File(path);
                if (files.exists()) {	//파일이 존재하는 경우 읽어오기
                    try {
                        FileInputStream fin = new FileInputStream(path);
                        byte[] txt = new byte[100];
                        fin.read(txt);
                        String str = new String(txt);
                        AlertDialog.Builder readDlg = new AlertDialog.Builder(getActivity());
                        readDlg.setTitle("가계부 읽기");
                        readDlg.setMessage(str);
                        readDlg.setIcon(R.drawable.testicon);
                        readDlg.setPositiveButton("확인", null);
                        readDlg.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {	//파일이 존재하지 않는 경우 파일 생성하기
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
                            try {
                                FileOutputStream fout = new FileOutputStream(path);
                                int etIncome = Integer.parseInt(editText.getText().toString());
                                int etExpense = Integer.parseInt(editText2.getText().toString());
                                income = income + etIncome;
                                expense = expense + etExpense;
                                balance = income - expense;

                                String str = "수입 합계 : " + income + "\n"
                                        + "지출 합계 : " + expense + "\n"
                                        + "잔액 : " + balance;

                                String writeStr = "수입 : " + etIncome + "\n" + "지출 : " + etExpense;
                                fout.write(writeStr.getBytes());
                                fout.close();
                                tv.setText(str);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dlg.show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}