package com.example.flutterwavetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOne, btnTwo;
    final int amount_1 = 5000;
    final int amount_2 = 2500;
    String email = "example@gmail.com";
    String fName = "FirstName";
    String lName = "LastName";
    String narration = "payment for food";
    String txRef;
    String country = "KE";
    String currency = "KSH";

    final String publicKey = "FLWPUBK_TEST-1f235daa5f13fd75560cffea284b17ad-X";
    final String encryptionKey = "FLWSECK_TESTaa8e6adbd7aa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btn_one);
        btnTwo = findViewById(R.id.btn_two);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                makePayment(amount_1);
                break;
            case R.id.btn_two:
                makePayment(amount_2);
                break;
        }
    }

    private void makePayment(int amount) {
        txRef = email + " " + UUID.randomUUID().toString();

        //instance of the ravePayment
         new RavePayManager(this)
                .setAmount(amount)
                .setCurrency(currency)
                .setEmail(email)
                 .setfName(fName)
                 .setlName(lName)
                 .setNarration(narration)
                 .setEncryptionKey(encryptionKey)
                 .setTxRef(txRef)
                 .acceptCardPayments(true)
                 .acceptAccountPayments(true)
                 .acceptMpesaPayments(true)
                 .acceptGHMobileMoneyPayments(false)
                 .onStagingEnv(false)
                 .allowSaveCardFeature(true)
                 .withTheme(com.flutterwave.raveandroid.R.style.DefaultTheme)
                .initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS" + message, Toast.LENGTH_SHORT).show();
            }

            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED" + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}