package com.example.mypc.frider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mypc.frider.R;

public class CashOnDelivery extends AppCompatActivity {

    Button order;
    EditText edt_name,edt_address,edt_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);
        order=(Button)findViewById(R.id.btn_order);
        edt_name=(EditText)findViewById(R.id.edt_name);
        edt_address=(EditText)findViewById(R.id.edt_address);
        edt_phone=(EditText)findViewById(R.id.edt_phone);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_name.getText().toString().equals("")||edt_address.getText().toString().equals("")||edt_phone.getText().toString().equals("")){

                    Toast.makeText(CashOnDelivery.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                }else{
                      startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                }

            }
        });
    }
}
