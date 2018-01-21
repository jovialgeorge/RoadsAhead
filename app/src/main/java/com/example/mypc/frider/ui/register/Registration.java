package com.example.mypc.frider.ui.register;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mypc.frider.R;
import com.example.mypc.frider.ui.otp.PhoneAuthActivity;

import java.util.Calendar;

public class Registration extends Activity{

    EditText name;
    EditText address;
    EditText gender;
    EditText dob;
    RadioButton r;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    EditText phone;
    EditText password;
    int year_x, month_x, day_x;
    public static  final int DIALOD_ID=0;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
      showDialogOnClick();
        initViews();

        // find the radiobutton by returned id

    }
    public  void initViews(){


        name=(EditText)findViewById(R.id.etName);
        address=(EditText)findViewById(R.id.etAddress);

        phone=(EditText)findViewById(R.id.et_phone);
        password=(EditText)findViewById(R.id.et_password);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                r=(RadioButton)findViewById(selectedId);

                String name1=name.getText().toString();
                String address1=address.getText().toString();
                String phone1=phone.getText().toString();
                String date=dob.getText().toString();
                String password1=password.getText().toString();
                String gender=r.getText().toString();
                Intent i = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                Toast.makeText(Registration.this, "Gender"+gender, Toast.LENGTH_SHORT).show();
                i.putExtra("name",name1);
                i.putExtra("address",address1);
                i.putExtra("dob",date);
                i.putExtra("gender",gender);
                i.putExtra("phone",phone1);
            i.putExtra("password",password1);
            startActivity(i);

        }
    });


}

    public void showDialogOnClick(){
        dob=(EditText)findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          showDialog(DIALOD_ID);


            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOD_ID)
            return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
        return  null;

    }

    private DatePickerDialog.OnDateSetListener dPickerListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x=year;
            month_x=month;
            day_x=dayOfMonth;
            Toast.makeText(Registration.this, "year"+year_x, Toast.LENGTH_SHORT).show();
            String db=day_x+"/"+month_x+"/"+year_x;
            dob.setText(db);

        }
    };



}


