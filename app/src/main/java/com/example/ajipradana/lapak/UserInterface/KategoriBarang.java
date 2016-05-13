package com.example.ajipradana.lapak.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajipradana.lapak.Grid.GridViewAdapterBarang;
import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.Koneksi.JSONParser;
import com.example.ajipradana.lapak.Objek.Barang;
import com.example.ajipradana.lapak.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AJI PRADANA on 4/7/2016.
 */
public class KategoriBarang extends AppCompatActivity {
    String kodeJenis;
    ArrayList<HashMap<String,String>> arrayList;
    TextView daftarBarang;
    TextView kosong;
    AlamatUrl ck;

    //gridview
    GridView gv;
    GridViewAdapterBarang gva;


    public static final String ID_BARANG ="id_barang";
    public static final String NAMA_BARANG = "nama_barang";
    public static final String HARGA = "harga";
    public static final String PATH_FOTO = "path_foto1";
    public static final String NAMA_PENJUAL = "nama";
    public static final String NO_TELP = "no_telp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_activity_main);
        ck = new AlamatUrl();

        //action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            kodeJenis = extras.getString("kodeJenis");
            if(cekKoneksi()){
                new tampilKategoriBarang().execute();
            }else{
                Toast.makeText(KategoriBarang.this, R.string.noKoneksi, Toast.LENGTH_SHORT).show();
            }

        }
    }


    public boolean cekKoneksi() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public class tampilKategoriBarang extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        Barang barang= new Barang();
        JSONObject json;
        JSONArray data;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            setInvisible(daftarBarang);
//            setInvisible(kosong);
            pDialog = new ProgressDialog(KategoriBarang.this);
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
            params.add(new BasicNameValuePair("id_kategori", kodeJenis));
            params.add(new BasicNameValuePair("a", ck.getKodeDaftarBarang()));
            jParser.makeHttpRequest(ck.getAlamatUrl(), "POST", params);
            try {
                json = jParser.getJSONFromUrl(ck.getAlamatUrl());
                data = json.getJSONArray("ayo");
                for (int i = 0; i < data.length(); i++) {
                    HashMap<String,String> map = new HashMap<String,String>();

                    JSONObject c = data.getJSONObject(i);

                    barang.setId_barang(c.getInt(ID_BARANG));
                    barang.setNama_barang(c.getString(NAMA_BARANG));
                    barang.setHarga(c.getInt(HARGA));
                    barang.setPath_foto1(c.getString(PATH_FOTO));
                    barang.setKeterangan(c.getString(NAMA_PENJUAL));
                    barang.setNo_telp(c.getString(NO_TELP));

                    map.put(ID_BARANG, Integer.toString(barang.getId_barang()));
                    map.put(NAMA_BARANG, barang.getNama_barang());
                    map.put(HARGA, Integer.toString(barang.getHarga()));
                    map.put(PATH_FOTO, barang.getPath_foto1());
                    map.put(NAMA_PENJUAL, barang.getKeterangan());
                    map.put(NO_TELP, barang.getNo_telp());

                    arrayList.add(map);
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
//                setVisible(daftarBarang);
//                setInvisible(kosong);
//                listV = (ListView)findViewById(R.id.listKategori);
//                adapter = new ListViewAdapterBarang(KategoriBarang.this,arrayList);
//
//                listV.setAdapter(adapter);
//                pDialog.dismiss();
                gv = (GridView)findViewById(R.id.gridView);
                gva = new GridViewAdapterBarang(KategoriBarang.this, R.layout.grid_item_layout_kategori, arrayList);
                gv.setAdapter(gva);
                pDialog.dismiss();

            }else{
//                setInvisible(daftarBarang);
//                setVisible(kosong);
//                pDialog.dismiss();
                Toast.makeText(KategoriBarang.this, "Kosong", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }

        }
    }
    private void setVisible(View v){
        v.setVisibility(View.VISIBLE);
    }
    private void setInvisible(View v){
        v.setVisibility(View.GONE);
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
}