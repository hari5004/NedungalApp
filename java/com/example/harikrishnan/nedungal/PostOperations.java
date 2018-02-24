package com.example.harikrishnan.nedungal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostOperations extends AsyncTask<String,Void,String>{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pDialog;
    OkHttpClient client;
    String json2;
    private Context mcontext;
    String Operations="";

    private ListAdapter mAdapter;
    public PostOperations(Context context)
    {
        mcontext=context;
    }
     @Override
    protected void onPreExecute()

    {  // pDialog.show();
        pDialog=ProgressDialog.show(mcontext, "", "Please Wait", false);
    }
    @Override
    protected String doInBackground(String... params) {
        JSONObject post_data = new JSONObject();
        try {
            Operations=params[0];
            if(params[0]=="book") {
                post_data.put("name", params[1]);
                post_data.put("star", params[2]);
                post_data.put("vazhipadu", params[3]);
                post_data.put("address", params[4]);
                post_data.put("mobile", params[5]);
                post_data.put("date", params[6]);
            }
            else if(params[0]=="get")
            {
                post_data.put("vazhipadu",params[1]);
            }
            else if(params[0]=="login")
            {
                post_data.put("username",params[1]);
                post_data.put("password",params[2]);
            }
        } catch (JSONException e) {
        }
        final String jsonString = post_data.toString();
        client = new OkHttpClient();
        try {
            RequestBody body = RequestBody.create(JSON, jsonString);
            if(params[0]=="book") {
                Request request = new Request.Builder()
                        .url("https://stormy-stream-98963.herokuapp.com/book/bookvazhipadu")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                json2 = response.body().string();
            }
            else if(params[0]=="get") {
                Request request = new Request.Builder()
                        .url("https://stormy-stream-98963.herokuapp.com/book/getvazhipadu")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                json2 = response.body().string();
            }
            else if(params[0]=="login")
            {
                Request request = new Request.Builder()
                        .url("https://stormy-stream-98963.herokuapp.com/users/login")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                json2 = response.body().string();
            }

        } catch (IOException e) {
        }
        return json2;

    }
    @Override
    protected void onPostExecute(String result) {
        boolean Authority;
        pDialog.hide();
        try{

           // Toast.makeText(mcontext,result,Toast.LENGTH_LONG).show();
            if(Operations.matches("book"))
            {
                JSONObject jsonResponse=new JSONObject(result);
                String resultS=jsonResponse.getString("result");
                Toast.makeText(mcontext,resultS,Toast.LENGTH_LONG).show();
                if(resultS.matches("Successfully added"))
                {   //Toast.makeText(mcontext,"Successfully Booked",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(mcontext,BookingActivity.class);
                    mcontext.startActivity(intent);
                }
            }
            if(Operations=="get")
            {
                    JSONArray resultJson = new JSONArray(result);

                if(resultJson.length()==0)
                {
                    Toast.makeText(mcontext,"No record found",Toast.LENGTH_LONG).show();
                }
                else {
                    List<Bookie> listdata = new ArrayList<>();
                    listdata.clear();
                    for (int i = 0; i < resultJson.length(); i++) {
                        JSONObject json_data = resultJson.getJSONObject(i);
                        Bookie bookie = new Bookie();
                        bookie.nameBooked = json_data.getString("name");
                        bookie.address = json_data.getString("address");
                        bookie.mobile = json_data.getString("mobile");
                        listdata.add(bookie);
                    }
                    mAdapter = new ListAdapter(listdata);
                    History.mRecyclerView.setAdapter(mAdapter);
                }
            }
            if(Operations.matches("login"))
            {
                JSONObject jsonResponse=new JSONObject(result);
                String resultS=jsonResponse.getString("result");
                if(resultS.matches("Success"))
                {
                    Intent intent=new Intent(mcontext,BookingActivity.class);
                    mcontext.startActivity(intent);
                }
                else
                {
                    Toast.makeText(mcontext,"Invalid Creditionals",Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e){
            Toast.makeText(mcontext,"We are currently unable to process the request",Toast.LENGTH_LONG).show();
        }
    }
}
