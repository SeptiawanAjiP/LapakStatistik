package com.example.ajipradana.lapak.UserInterface;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.Koneksi.JSONParser;
import com.example.ajipradana.lapak.Objek.User;
import com.example.ajipradana.lapak.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AJI PRADANA on 4/9/2016.
 */
public class DaftarUser extends AppCompatActivity {
    User user;
    EditText namaET;
    EditText emailET;
    EditText passwordET;
    RadioGroup jenis_kelaminET;
    EditText no_telpET;
    Button daftar;
    AlamatUrl ck;
    ImageButton ib;

    public final static String NAMA = "nama";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";
    public final static String JENIS_KELAMIN = "jenis_kelamin";
    public final static String NO_TELP = "no_telp";


    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_user);

        //action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        user = new User();
        ck = new AlamatUrl();




        namaET = (EditText)findViewById(R.id.nama);
        emailET = (EditText)findViewById(R.id.email);
        passwordET = (EditText)findViewById(R.id.password);
        jenis_kelaminET = (RadioGroup)findViewById(R.id.jenis_kelamin);
        no_telpET = (EditText)findViewById(R.id.no_telp);
        //tanggal_lahir = (EditText)findViewById(R.id.tanggal_lahir);
        daftar = (Button)findViewById(R.id.daftar);


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);



        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setNama(namaET.getText().toString());
                user.setEmail(emailET.getText().toString());
                user.setPassword(passwordET.getText().toString());
                switch(jenis_kelaminET.getCheckedRadioButtonId()){
                    case R.id.laki_laki:
                        user.setJenis_kelamin("L");
                        break;
                    case R.id.perempuan:
                        user.setJenis_kelamin("P");
                        break;
                }
                user.setNo_telp(no_telpET.getText().toString());
                //user.setTanggal_lahir(tanggal_lahir.getText().toString());

                if(isNoEmpty(user.getNama())){
                    if(isValidEmail(user.getEmail())){
                        if(isNoEmpty(user.getPassword())){
                            if(lengthPass(user.getPassword())){
                                if(isNoEmpty(user.getJenis_kelamin())){
                                   if (isNoEmpty(user.getNo_telp())) {
                                            if (lengthPhone(user.getNo_telp())) {
                                                if(cekKoneksi())new insertUserBaru().execute();
                                                else
                                                    Toast.makeText(DaftarUser.this, R.string.noKoneksi, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(DaftarUser.this, "Nomor Telepon Salah", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(DaftarUser.this, "Nomor Telepon Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                                        }
                                   }else{
                                    Toast.makeText(DaftarUser.this, "Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(DaftarUser.this, "Password minimal 8 Karakter", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(DaftarUser.this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(DaftarUser.this, "Format Email Salah", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DaftarUser.this, "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }

                Log.d("Nama :",user.getNama());


            }
        });
    }
    //validasi form jika isinya kosong
    public final static boolean isNoEmpty(String str){
        if(str.isEmpty()){
            return false;
        }
        return true;
    }
    //validasi form email
    public final static boolean isValidEmail(CharSequence target){
        if(target == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean lengthPass(String pass){
        if(pass.length()<8)
            return false;
        return true;
    }

    public final static boolean lengthPhone(String phone){
        if(phone.length()<11 || phone.length()>14)
            return false;
        return true;
    }

    public class insertUserBaru extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        String st=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaftarUser.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("onPreExecute","");
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(NAMA,user.getNama()));
            params.add(new BasicNameValuePair(EMAIL,user.getEmail()));
            params.add(new BasicNameValuePair(PASSWORD, user.getPassword()));
            params.add(new BasicNameValuePair(JENIS_KELAMIN,user.getJenis_kelamin()));
            params.add(new BasicNameValuePair(NO_TELP, user.getNo_telp()));
            params.add(new BasicNameValuePair("a",ck.getKodeDaftarUser()));
            Log.d("paramas :", params.toString());
            jParser.makeHttpRequest(ck.getAlamatUrl(),"POST", params);
            Log.d("udah lewat internet","");
            try{
                JSONObject json = jParser.getJSONFromUrl(ck.getAlamatUrl());
                Log.d("PHP", json.toString());
                user.setStatus( json.getString("status"));
                Log.d("st ",user.getStatus());
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("sthjh ",user.getStatus());
            if(Integer.parseInt(user.getStatus())== 0 ){

                Toast.makeText(DaftarUser.this, "Email Telah Terdaftar !!!", Toast.LENGTH_SHORT).show();
                user.setEmail("");

            }else{

                Toast.makeText(DaftarUser.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();

            }
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean cekKoneksi() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
