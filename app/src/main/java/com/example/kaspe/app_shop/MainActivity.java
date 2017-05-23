package com.example.kaspe.app_shop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public final String productURL = "http://appshop.azurewebsites.net/api/product";
    public int amount;
    public  int userid;
    public ArrayList<Product> list;
    public JSONArray jsonArray;

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.product_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        list = new ArrayList<Product>();
       // getUser();
        invokews();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Product clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close_settings) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void getUser()
    {
        userid = this.getIntent().getExtras().getInt("id");

    }

    public void invokews()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(productURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    //ArrayList<Product> yourArray = new Gson().fromJson(response.toString(), new TypeToken<List<Product>>(){}.getType());
                        jsonArray = response;

                    convert();
                    setadapter();
                    //test = yourArray.get(1).toString();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    toast(e.toString());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    toast("failed to get the json objs");

            }
        });
    }
    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void convert(){

        for(int i = 0, count = jsonArray.length(); i< count; i++)
        {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                Product p = new Product(
                Integer.valueOf(obj.getString("id")),
                Integer.valueOf(obj.getString("category_id")),
                obj.getString("name"),
                obj.getString("price"),
                obj.getString("description")
                );
                list.add(p);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void setadapter(){
        ItemAdapter arrayAdapter = new ItemAdapter(getApplicationContext(), list);

        lv.setAdapter(arrayAdapter);
    }
}
