package com.example.mypc.frider.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.mypc.frider.R;
import com.example.mypc.frider.ui.login.LoginActivity;
import com.example.mypc.frider.util.GlobalPreference;


public class IPActivity extends AppCompatActivity {

    final Context c = this;
    GlobalPreference mGlobalPreference;
    public static final String TAG=IPActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        getIP();

    }

    public void getIP(){


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);
        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        userInputDialogEditText.setText(mGlobalPreference.RetriveIP());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        mGlobalPreference.addIP(userInputDialogEditText.getText().toString());
                        userInputDialogEditText.setText(userInputDialogEditText.getText().toString());
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
