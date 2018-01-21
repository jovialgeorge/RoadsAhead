package com.example.mypc.frider.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.permisions.BaseActivity;
import com.example.mypc.frider.ui.HomeActivity;
import com.example.mypc.frider.ui.register.Registration;
import com.example.mypc.frider.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    TextView registertext;
    EditText name;
    EditText password;
    Button submit;
    String ip = "";
    public static String url = "";
    GlobalPreference globalPreference;
    String name1;
    String password1;

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        globalPreference = new GlobalPreference(getApplicationContext());

        globalPreference.addIP("levivaguez.com/rider");
        name = (EditText) findViewById(R.id.edt_uname);
        password = (EditText) findViewById(R.id.edt_pwd);
        submit = (Button) findViewById(R.id.login_button);

        name1 = name.getText().toString();
        password1 = password.getText().toString();

        ip = globalPreference.RetriveIP();
        url = "http://" + ip + "/login.php";
        Log.d(TAG, "onCreate: " + url);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        registertext = (TextView) findViewById(R.id.txt1);
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));

            }
        });
    }

    public void login() {
        name1 = name.getText().toString();

        password1 = password.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);

                if (response.equals("failed")) {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    JsonParsing(response);
                    //     Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                    // startActivity(intent);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name1);
                params.put("password", password1);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void JsonParsing(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String password = jsonObject.getString("password");
                globalPreference.SaveCredentials(name, password);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}