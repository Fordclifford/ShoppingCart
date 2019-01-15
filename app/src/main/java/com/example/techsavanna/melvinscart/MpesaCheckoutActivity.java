package com.example.techsavanna.melvinscart;

import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MpesaCheckoutActivity extends AppCompatActivity {
    private static final String TAG = MpesaCheckoutActivity.class.getSimpleName();

    @BindView(R.id.editTextPhoneNumber)
    EditText editTextPhoneNumber;
    @BindView(R.id.sendButton)
    Button sendButton;

    @BindView(R.id.creditButton)
    Button creditButton;
    @BindView(R.id.totalamount)
    TextView totalamount;
    //Declare Daraja :: Global Variable
    Daraja daraja;
    private double totalCostPrice;

    private static final int MY_SOCKET_TIMEOUT_MS = 5000;
    private  static final String urlAdress="https://192.168.1.243:3000/mpesa/api/mpesa/push";

    private static final String SERVER_PATH = "Path_to_Server_To_Store_Token";

    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa_checkout);
        ButterKnife.bind(this);


        totalCostPrice = getIntent().getExtras().getDouble("TOTAL_PRICE");
        Log.d(TAG, "Price " + totalCostPrice);

        totalamount.setText("Total Price: "+totalCostPrice);


        //Init Daraja
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("NzSI5OzGLWMInqagwgp74cqujSYcIi85", "fCEZMwnXiqjnvGoE", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(MpesaCheckoutActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
               // Toast.makeText(MpesaCheckoutActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(MpesaCheckoutActivity.this.getClass().getSimpleName(), error);
            }
        });

        //TODO :: THIS IS A SIMPLE WAY TO DO ALL THINGS AT ONCE!!! DON'T DO THIS :)
        /**
         * Using Lambda here -> Added Java 8 support :)
         */

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Phone Number from User Input
                phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber)) {
                    editTextPhoneNumber.setError("Please Provide a Phone Number");
                    return;
                }

//
//
//                //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
//                LNMExpress lnmExpress = new LNMExpress(
//                        "174379",
//                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
//                        String.valueOf(totalCostPrice),
//                        "254708529798",
//                        "174379",
//                        phoneNumber,
//                        "http://mycallbackurl.com/checkout.php",
//                        "001ABC",
//                        "Goods Payment"
//                );
////This is the
//                daraja.requestMPESAExpress(lnmExpress,
//                        new DarajaListener<LNMResult>() {
//                            @Override
//                            public void onResult(@NonNull LNMResult lnmResult) {
//                                Log.i(MpesaCheckoutActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
//
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Log.i(MpesaCheckoutActivity.this.getClass().getSimpleName(), error);
//                            }
//                        }
//                );
//            }

                sendPost();
            }
        });

        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set to credit

                Intent intent=new Intent(MpesaCheckoutActivity.this,PostOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void sendPost() {

        totalamount.setText("Total Price: "+totalCostPrice);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if (conn instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                        httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                        httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                    }
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("phone", phoneNumber);
                    jsonParam.put("amount", totalCostPrice);
                    jsonParam.put("accountReference", "mnmn");
                    jsonParam.put("transactionDesc", "ndmh");

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
