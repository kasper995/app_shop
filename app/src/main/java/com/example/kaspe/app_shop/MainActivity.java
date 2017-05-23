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
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public final String productURL = "http://appshop.azurewebsites.net/api/product";
    public int amount;
    public JSONObject jsonObject;
    public ArrayList<Product> basketlist;
    public ArrayList<Product> temp;
    public JSONArray jsonArray;
    public boolean running = true;
    public String products = "";
 public Customer customer;
    private ListView lv;
    OkHttpClient client ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        basketlist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.product_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "mail send", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                basket();

            }
        });

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

    public void getUser() {
        customer = new Customer(this.getIntent().getExtras().getString("email"),this.getIntent().getExtras().getString("name"),this.getIntent().getExtras().getString("id"));

    }

    public void invokews() {
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

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void convert() {

        for (int i = 0, count = jsonArray.length(); i < count; i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                Product p = new Product(
                        Integer.valueOf(obj.getString("id")),
                        Integer.valueOf(obj.getString("category_id")),
                        obj.getString("name"),
                        obj.getString("price"),
                        obj.getString("description"), 0
                );

                MyProductList.getInstance().getProducts().add(p);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void setadapter() {
        ItemAdapter arrayAdapter = new ItemAdapter(getApplicationContext());

        lv.setAdapter(arrayAdapter);
    }

    public void basket() {
        for (int i = 0, count = MyProductList.getInstance().getProducts().size(); i < count; i++) {
            try {
                int tempi = MyProductList.getInstance().getProducts().get(i).amount;
                if ( tempi > 0) {

                    basketlist.add(MyProductList.getInstance().getProducts().get(i));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (basketlist.size() > 0) {
            productToString();
        }
        else{
            toast("no items was selected");
        }

    }

    protected void sendEmail(){


           Thread t = new Thread() {
               public void run() {
                   client =  new OkHttpClient();



                   RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), String.valueOf(jsonObject));

                   try {
                       Request request = new Request.Builder()
                               .url(productURL)
                               .post(postBody)
                               .build();


                       client.newCall(request).execute();

                   }
                   catch (IOException e) {
                       e.printStackTrace();

                   }
               }
           };
           t.start();

        toast("your order is processed");


    }

    public void productToString() {
jsonObject = new JSONObject();
        getUser();
        int totalPrice = 0;

        for (int i = 0, count = basketlist.size(); i < count; i++) {
            try {

                Product product = basketlist.get(i);
                products = products + product.name + " " + String.valueOf(product.amount) + " ";

                totalPrice = totalPrice + Integer.valueOf(product.price);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        products = "hello " + customer.getName() + " you have ordered these products: " + products + "Total price for all is :" + totalPrice + "dkk";
        try {
            jsonObject.put("email", customer.getEmail());
            jsonObject.put("body", products);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        sendEmail();
    }



}
