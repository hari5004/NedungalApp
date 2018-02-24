package com.example.harikrishnan.nedungal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,AdapterView.OnItemSelectedListener {
    static String star="";
    static String vazhipadu="";
    String nameString="";
    String addressString="";
    String mobile="";
    String dateString="";
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Spinner spinner = (Spinner) findViewById(R.id.star);
        Spinner vazhipaduspiner=(Spinner)findViewById(R.id.vazhipadu);
        vazhipaduspiner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stars, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        spinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> vazhipaduadapter = ArrayAdapter.createFromResource(this,
                R.array.vazhipadu, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        vazhipaduadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        vazhipaduspiner.setAdapter(vazhipaduadapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        switch (parent.getId()) {
            case R.id.star:
                String item = parent.getItemAtPosition(pos).toString();

                // Showing selected spinner item
                // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                star=item;
                break;
            case R.id.vazhipadu:
                vazhipadu  = parent.getItemAtPosition(pos).toString();

                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + vazhipadu, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.booking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_vazhipadu) {
            Intent intent=new Intent(this,History.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void addToDb(View view)
    {
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText address=(EditText)findViewById(R.id.address);
        final EditText phnumer=(EditText)findViewById(R.id.contact);
        final EditText date=(EditText)findViewById(R.id.date);
        nameString=name.getText().toString();
        addressString=address.getText().toString();
        dateString=date.getText().toString();
        mobile=phnumer.getText().toString();
        if(TextUtils.isEmpty(nameString))

        {
            name.setError("Name cannot be empty");
            //Toast.makeText(this, "Name Field cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if(addressString.matches("")){
        Toast.makeText(this, "Address Field cannot be empty", Toast.LENGTH_LONG).show();
    }
    else if(TextUtils.isEmpty(addressString))
        {
            //Toast.makeText(this, "MobileNumber Field cannot be empty", Toast.LENGTH_LONG).show();
            address.setError("Address cannot be empty");
        }
        else if(TextUtils.isEmpty(mobile))
        {
            //Toast.makeText(this, "MobileNumber Field cannot be empty", Toast.LENGTH_LONG).show();
            phnumer.setError("Address cannot be empty");
        }
        else {

            Boolean status=isNetworkAvailable();
            if(status) {
                PostOperations postOperations = new PostOperations(context);
                postOperations.execute("get", vazhipadu);
            }
            else
            {
                Toast.makeText(context, "Network Connection not available", Toast.LENGTH_LONG).show();
            }
            PostOperations postOperations = new PostOperations(context);
            postOperations.execute("book", nameString, star, vazhipadu, addressString, mobile, dateString);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
