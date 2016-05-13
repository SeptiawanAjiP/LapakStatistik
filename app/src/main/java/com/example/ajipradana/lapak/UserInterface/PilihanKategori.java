package com.example.ajipradana.lapak.UserInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ajipradana.lapak.Grid.GridViewAdapterKategori;
import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.Koneksi.JSONParser;
import com.example.ajipradana.lapak.Objek.Kategori;
import com.example.ajipradana.lapak.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Septiawan Aji on 5/1/2016.
 */
public class PilihanKategori extends AppCompatActivity {

    public final static String ID_KATEGORI ="id_kategori";
    public final static String KATEGORI ="kategori";
    public final static String PATH_FOTO_KATEGORI ="path_foto_kategori";

    GridView gv;
    GridViewAdapterKategori gva;

    String kode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.grid_menu_kategori);
        Intent i = getIntent();

        kode = i.getStringExtra("pilihan_kategori");

        new MenuBarang().execute();




    }
    public class MenuBarang extends AsyncTask<String,String,String> {
        ProgressDialog pDialog;
        ArrayList<HashMap<String,String>> arrayList;
        Kategori kategori = new Kategori();
        JSONObject json;
        JSONArray data;
        AlamatUrl ck = new AlamatUrl();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PilihanKategori.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
            arrayList = new ArrayList<HashMap<String, String>>();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("a", kode));
            jParser.makeHttpRequest(ck.getAlamatUrl(), "POST", params);
            Log.d(ck.getAlamatUrl(), params.toString());
            try {
                json = jParser.getJSONFromUrl(ck.getAlamatUrl());
                data = json.getJSONArray("ayo");

                Log.d("data",data.toString());

                for (int i = 0; i < data.length(); i++) {
                    HashMap<String,String> map = new HashMap<String,String>();
                    JSONObject c = data.getJSONObject(i);

                    kategori.setId_kategori(c.getString(ID_KATEGORI));
                    kategori.setKategori(c.getString(KATEGORI));
                    kategori.setPath_foto_kategori(c.getString(PATH_FOTO_KATEGORI));

                    map.put(ID_KATEGORI, kategori.getId_kategori());
                    map.put(KATEGORI, kategori.getKategori());
                    map.put(PATH_FOTO_KATEGORI,kategori.getPath_foto_kategori());


                    arrayList.add(map);
                    Log.d("arrayList ",arrayList.toString());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(arrayList.size()>0){
                gv = (GridView)findViewById(R.id.grid_kategori);

                gva = new GridViewAdapterKategori(PilihanKategori.this, R.layout.g_kategori, arrayList);
                gv.setAdapter(gva);
                pDialog.dismiss();

            }else{;
                Toast.makeText(getApplicationContext(), "Kosong", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //tombol back pada action barr
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

}
