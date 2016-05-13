package com.example.ajipradana.lapak.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ajipradana.lapak.Grid.GridViewAdapterHalamanUtama;
import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.Koneksi.JSONParser;
import com.example.ajipradana.lapak.Loader.ImageLoader;
import com.example.ajipradana.lapak.Notifikasi.NotificationHandler;
import com.example.ajipradana.lapak.Objek.Barang;
import com.example.ajipradana.lapak.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    String kode;
    NotificationHandler nHandler;
    GridViewWithHeaderAndFooter gv;//memungkinkan atasnya bisa disisipi layout lagi,untuk memakai GridViewWithHeaderAndFooter compile dulu gradel nya(liat gradle!)
    GridViewAdapterHalamanUtama gva;

    ImageLoader imageLoader;
    ImageView tagLine;//header
    TextView noKoneksi;

    //komponen parallax
    TextView stickyView;
    private View stickyViewSpacer;


    public static final String ID_BARANG ="id_barang";
    public static final String NAMA_BARANG = "nama_barang";
    public static final String HARGA = "harga";
    public static final String PATH_FOTO = "path_foto1";
    public static final String NAMA_PENJUAL = "nama";
    public static final String NO_TELP = "no_telp";
    public static final String KATEGORI = "kategori";
    public static final String STATUS = "status";

    AlamatUrl alamat = new AlamatUrl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nHandler = NotificationHandler.getInstance(this);

        Intent i = getIntent();
        kode = i.getStringExtra("halaman_utama");//mengambil parameter berupa angka 0 yang dilempar dari splashscreen
        Log.d("halaman_utama", kode);

        setContentView(R.layout.d_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        noKoneksi = (TextView)findViewById(R.id.noKoneksi);
        tagLine = (ImageView)findViewById(R.id.tagline);

        imageLoader = new ImageLoader(this);//digunakan untuk meload gambar pada header


        gv = (GridViewWithHeaderAndFooter)findViewById(R.id.gridView);
        stickyView = (TextView) findViewById(R.id.stickyView);

        /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.p_list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */
        //listView.addHeaderView(listHeader);\
        gv.addHeaderView(listHeader);//mengatur parallax header, tanpa ini tidak bisa parallax

        /* Handle list View scroll events */
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (gv.getFirstVisiblePosition() == 0) {
                    View firstChild = gv.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    tagLine.setY(topY * 0.5f);
                }
            }
        });



        //untuk mengecek koneksi pada saat aplikasi memasuki halaman utama
        if(cekKoneksi()){
            setInvisible(noKoneksi);
            setVisible(stickyView);
            new tampilKategoriBarang().execute();
        }else{
            setInvisible(stickyView);
            setVisible(noKoneksi);
            Toast.makeText(Navigation.this, R.string.noKoneksi, Toast.LENGTH_SHORT).show();
        }




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nHandler.createSimpleNotification(view.getContext());
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //untuk menampilkan tombol back pada toolbar
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    //digunakan untuk navigasi drawer pilihan menu disamping kiri
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action

        } else if (id == R.id.barang) {
            Intent a = new Intent(this,PilihanKategori.class);
            a.putExtra("pilihan_kategori",alamat.getKodePilihanKategori());
            startActivity(a);
        } else if (id == R.id.keranjang) {

        } else if (id == R.id.about) {

        } else if (id == R.id.daftar) {
            Intent a = new Intent(Navigation.this,DaftarUser.class);
            startActivity(a);
        } else if (id == R.id.login) {
            Toast.makeText(Navigation.this, "Hai", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //method untuk cek koneksi
    public boolean cekKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



    /*class asynctask digunakan untuk parse json yang dikembalikan oleh koneksi.php
    dan digunakan juga untuk melemparkan parameter untuk ditampilkan oleh Grid View
    pada halaman utama yang berisi barang2 terbaru dan barang favorit
     */
    public class tampilKategoriBarang extends AsyncTask<String,String,String> {
        ProgressDialog pDialog;
        ArrayList<HashMap<String,String>> arrayList;
        Barang barang= new Barang();
        JSONObject json;
        JSONArray data;
        AlamatUrl ck = new AlamatUrl();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Navigation.this);
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
            Log.d(ck.getAlamatUrl(),params.toString());
            try {
                json = jParser.getJSONFromUrl(ck.getAlamatUrl());
                data = json.getJSONArray("ayo");

                Log.d("data",data.toString());

                for (int i = 0; i < data.length(); i++) {
                    HashMap<String,String> map = new HashMap<String,String>();
                    JSONObject c = data.getJSONObject(i);

                    barang.setId_barang(c.getInt(ID_BARANG));
                    barang.setNama_barang(c.getString(NAMA_BARANG));
                    barang.setHarga(c.getInt(HARGA));
                    barang.setPath_foto1(c.getString(PATH_FOTO));
                    barang.setKeterangan(c.getString(NAMA_PENJUAL));
                    barang.setNo_telp(c.getString(NO_TELP));
                    barang.setKategori(c.getString(KATEGORI));
                    barang.setStatus(c.getString(STATUS));


                    map.put(ID_BARANG, Integer.toString(barang.getId_barang()));
                    map.put(NAMA_BARANG, barang.getNama_barang());
                    map.put(HARGA, Integer.toString(barang.getHarga()));
                    map.put(PATH_FOTO, barang.getPath_foto1());
                    map.put(NAMA_PENJUAL, barang.getKeterangan());
                    map.put(NO_TELP, barang.getNo_telp());
                    map.put(KATEGORI, barang.getKategori());
                    map.put(STATUS,barang.getStatus());

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
            /*
            Digunakan untuk mengeset gambar pada header, yang dilempar server ada code yang menandakan bahwa dia
            itu sebuah heade (tagline), yaitu status dari barang
             */
            for(int j=0;j<arrayList.size();j++){
                if(arrayList.get(j).containsValue("tagline")){
                    imageLoader.DisplayImage(arrayList.get(j).get("path_foto1"),tagLine);
                    arrayList.remove(j);
                    Log.d("jancuk", arrayList.get(j).get("path_foto1"));
                }
            }
            if(arrayList.size()>0){
                gv = (GridViewWithHeaderAndFooter)findViewById(R.id.gridView);
                gva = new GridViewAdapterHalamanUtama(Navigation.this, R.layout.grid_item_layout_kategori, arrayList);
                gv.setAdapter(gva);
                pDialog.dismiss();

            }else{;
                Toast.makeText(Navigation.this, "Kosong", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }
    }

    //method yang digunakan untuk mengeset muncul atau tidaknya suatu view (button,textview,dll)
    private void setVisible(View v){
        v.setVisibility(View.VISIBLE);
    }
    private void setInvisible(View v){
        v.setVisibility(View.GONE);
    }
}
