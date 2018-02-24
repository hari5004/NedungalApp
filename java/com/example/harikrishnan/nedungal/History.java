package com.example.harikrishnan.nedungal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class History extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String vazhipadu="";
    Context context=this;


    static RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Spinner vazhipaduspinner=(Spinner)findViewById(R.id.vazhipadulist);
        vazhipaduspinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vazhipadu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vazhipaduspinner.setAdapter(adapter);





    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        switch (parent.getId()) {
            case R.id.vazhipadulist:
                vazhipadu  = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + vazhipadu, Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
public void getDb(View view){
    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_vazhipadu);
    mLinearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mRecyclerView.setAdapter(null);
    Boolean status=isNetworkAvailable();
    if(status) {
        PostOperations postOperations = new PostOperations(context);
        postOperations.execute("get", vazhipadu);
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
