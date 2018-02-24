package com.example.harikrishnan.nedungal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public  void toBook(View view)
    {
        Boolean connection=isNetworkAvailable();
        if(connection) {

            final EditText name = (EditText) findViewById(R.id.username);
            final EditText passwordText = (EditText) findViewById(R.id.password);
            String username = name.getText().toString();
            String password = passwordText.getText().toString();

            if (TextUtils.isEmpty(username))

            {
                name.setError("UserName cannot be empty");
                //Toast.makeText(this, "Name Field cannot be empty", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(password)) {
                passwordText.setError("Password cannot be empty");
            } else {
                PostOperations postOperations = new PostOperations(context);
                postOperations.execute("login", username, password);
            }
//        Intent intent=new Intent(this,BookingActivity.class);
//        startActivity(intent);
        }
        else
        {
            Toast.makeText(context, "Network Connection not available", Toast.LENGTH_LONG).show();

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
