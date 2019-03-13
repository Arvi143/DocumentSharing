package com.example.arvind.spinner.fbaccount;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.arvind.spinner.R;
import com.example.arvind.spinner.RegisterActivity;
import com.example.arvind.spinner.User_Type;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class VerifyNumberActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);


        AccessToken accessToken = AccountKit.getCurrentAccessToken();

//        Intent intent = getIntent();
//        String str = intent.getStringExtra("message");
//        String name=intent.getStringExtra("username");
//        String pas=intent.getStringExtra("password");
        if (accessToken != null) {
            //Handle Returning User
        } else {
            //Handle new or logged out user

        }

        phoneLogin();
    }

    public void phoneLogin() {
        final Intent intent = new Intent(VerifyNumberActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
//                showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            // Get Account Kit ID
                            String accountKitId = account.getId();
                            Log.d("Arv", accountKitId);
                            // Get phone number
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            String countrycode = phoneNumber.getCountryCode();
                            String phoneNumberString = phoneNumber.toString();
                            Log.d("Arv", phoneNumberString + "    " + countrycode);

                            String device_id = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);

                            Intent intent = new Intent(getBaseContext(), User_Type.class);
                            intent.putExtra("phone", phoneNumberString);
                            intent.putExtra("deviceid", device_id);
                            startActivity(intent);

                            Log.d("Android", "Android ID : " + device_id);

                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            // Handle Error
                        }
                    });


                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }
            }


            //get country code, mobile number and device id


            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}

