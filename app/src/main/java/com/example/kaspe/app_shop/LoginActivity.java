package com.example.kaspe.app_shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener {
    final String ENDPOINT = "http://appshop.azurewebsites.net/api/customer";
    EditText etName;
    EditText etEmail;
    ProgressDialog prgDialog;
    int customerid;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log in");

        etName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.email);
        etName.setOnKeyListener(this);
        etEmail.setOnKeyListener(this);

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
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

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
           loginUser(v);
        }
        return true;
    }


    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    public void loginUser(View view){
        // Get Email Edit View Value
        String name = etName.getText().toString();
        // Get Password Edit View Value
        String email = etEmail.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();

        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(name) && Utility.isNotNull(email)){
            // When Email entered is Valid
            if(Utility.validate(email)){
                // Put Http parameter username with value of Email Edit View control
                params.put("name", name);
                // Put Http parameter password with value of Password Edit Value control
                params.put("email", email);
                // Invoke RESTful Web Service with Http parameters

                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    //JSONObject obj = response.getJSONObject(0);
                    customer = new Customer(response.getString("email"),response.getString("name"),response.getString("id"));

                    navigatetoHomeActivity();


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
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.putExtra("id", customer.getId());
        homeIntent.putExtra("name", customer.getName());
        homeIntent.putExtra("email", customer.getEmail());
        startActivity(homeIntent);
    }

}