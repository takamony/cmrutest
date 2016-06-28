package com.example.livecmru.cmrutest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicil
    private EditText nameEditText, passwordEditText, userEditText;
    private RadioGroup radioGroup;
    private RadioButton avata0RadioButton, avata1RadioButton, avata2RadioButton, avata3RadioButton, avata4RadioButton;
    private String namestring, userstring, passwordstring, avatastring;
    private static final String urlPHP = "http://swiftcodingthai.com/cmru/add_user_master.php";
    //String urlPHP = "http://www.cmrutv.cmru.ac.th/add_user_taky.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        radioGroup = (RadioGroup) findViewById(R.id.radAvata);
        avata0RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton5);

        //radio dcntrol
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        avatastring = "0";
                        break;
                    case R.id.radioButton2:
                        avatastring = "1";
                        break;
                    case R.id.radioButton3:
                        avatastring = "2";
                        break;
                    case R.id.radioButton4:
                        avatastring = "3";
                        break;
                    case R.id.radioButton5:
                        avatastring = "4";
                        break;
                }
            }//oncheked
        });

    } //Main method

    public void clickSignUpSign(View view) {
        namestring = nameEditText.getText().toString().trim();
        userstring = userEditText.getText().toString().trim();
        passwordstring = passwordEditText.getText().toString().trim();
        //Check Space
        if (namestring.equals("") || userstring.equals("") || passwordstring.equals("")) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรอกให้ครบ");
        } else if (checkChooseAvata()) {
            //cheched
            confirmData();
        } else {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "คุณยังไม่ได้เลือก Avata","กรุณาเลือก Avata");
        }
    }

    private void confirmData() {
        MyData myData = new MyData();
        int[] avataInts = myData.getAvataInts();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(avataInts[Integer.parseInt(avatastring)]);
        builder.setTitle(namestring);
        builder.setMessage("User = " + userstring + "\n" + "Password = " + passwordstring);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadUserToServer();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void uploadUserToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", namestring)
                .add("User", userstring)
                .add("Password", passwordstring)
                .add("Avata", avatastring)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                finish();
            }
        });
    }

    private boolean checkChooseAvata() {
        boolean status = true;
        if (avata0RadioButton.isChecked() ||
                avata1RadioButton.isChecked() ||
                avata2RadioButton.isChecked() ||
                avata3RadioButton.isChecked() ||
                avata4RadioButton.isChecked()) {
            status = true;
        } else {

            status = false;
        }

        return status;
    }
} //Main Class
