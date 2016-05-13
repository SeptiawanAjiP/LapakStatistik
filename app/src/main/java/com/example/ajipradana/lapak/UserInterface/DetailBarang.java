package com.example.ajipradana.lapak.UserInterface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.ajipradana.lapak.Koneksi.AlamatUrl;
import com.example.ajipradana.lapak.Koneksi.JSONParser;
import com.example.ajipradana.lapak.Loader.ImageLoader;
import com.example.ajipradana.lapak.Objek.Barang;
import com.example.ajipradana.lapak.R;
import com.example.ajipradana.lapak.SliderImage.ChildAnimationExample;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by AJI PRADANA on 4/8/2016.
 */
public class DetailBarang extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    //untuk nama_barang, keterangan, dan nomor telpon langsung setText ke TextView
    public static final String PATH_FOTO2 ="path_foto2";
    public static final String PATH_FOTO3 ="path_foto3";
    public static final String PATH_FOTO4 ="path_foto4";
    public static final String DILIHAT ="dilihat";
    public static final String KETERANGAN ="keterangan";
    public static final String STOK = "stok";
    public static final String TERJUAL = "terjual";

    String id_barang,nama_barang;
    String path_foto1;
    //ImageLoader imgLoader;
    //ImageView gambarDetail;
    //Context context;
    TextView namaBarang, hargaV, noTelp, namaPenjualV,keterangan;
    Button beli;
    JSONParser jParser;
    AlamatUrl alamat;

    List<String> arrayList;
    ListView lv;
    private View stickyViewSpacer;






    private SliderLayout mDemoSlider;

    private static final String TAG = DetailBarang.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_detail_barang);
        Intent i = getIntent();
        //imgLoader = new ImageLoader(context);
        alamat = new AlamatUrl();


        mDemoSlider = (SliderLayout)findViewById(R.id.slider);


        //action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        namaBarang = (TextView)findViewById(R.id.nama_barang);
//        hargaV = (TextView)findViewById(R.id.harga);
//        noTelp = (TextView)findViewById(R.id.no_telp);
//        namaPenjualV = (TextView)findViewById(R.id.namaPenjual);
//        keterangan = (TextView)findViewById(R.id.keterangan);
//        beli = (Button)findViewById(R.id.beli);
        lv= (ListView)findViewById(R.id.listView);


        arrayList = new ArrayList<>();


        //mengambil id barang, id barrang digunakan untuk detail barang
        id_barang = i.getStringExtra("id_barang");
        arrayList.add("Nama Barang  : "+i.getStringExtra("nama_barang"));
        arrayList.add("Harga        : "+i.getStringExtra("harga"));
        arrayList.add("Penjual      : "+i.getStringExtra("nama"));
        arrayList.add("Telepon      : "+i.getStringExtra("no_telp"));


//        namaBarang.setText(i.getStringExtra("nama_barang"));
//        hargaV.setText("Harga   : Rp. "+i.getStringExtra("harga"));
//        namaPenjualV.setText(i.getStringExtra("nama"));
//        noTelp.setText("Telepon : "+i.getStringExtra("no_telp"));
//        //mengambil path foto
        path_foto1 = i.getStringExtra("path_foto1");
        Log.d("id_barang", id_barang);
        //mengload gambar cuk
        //imgLoader.DisplayImage(path_foto, gambarDetail);

        //gambarDetail = (ImageView)findViewById(R.id.gambar_detail);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.p_list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */
        lv.addHeaderView(listHeader);

        /* Handle list View scroll events */
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (lv.getFirstVisiblePosition() == 0) {
                    View firstChild = lv.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
//                    detail.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    mDemoSlider.setY(topY * 0.5f);
                }
            }
        });
        if(cekKoneksi()){

            new tampilDetailBarang().execute();


            //Log.d("path2",barang.getPath_foto2());



        }else{
            Toast.makeText(DetailBarang.this, R.string.noKoneksi, Toast.LENGTH_SHORT).show();
        }


//        beli.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////               if(cekKoneksi()){
////
////               }else{
////                   Toast.makeText(DetailBarang.this, R.string.noKoneksi, Toast.LENGTH_SHORT).show();
////               }
//                Intent i = new Intent(DetailBarang.this, DaftarUser.class);
//                startActivity(i);
//            }
//        });
    }

    public class tampilDetailBarang extends AsyncTask<String,String,String>{
        JSONObject json;
        JSONArray data;
        Barang barang = new Barang();
        HashMap<String,String> url_maps = new HashMap<String, String>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("id_barang", id_barang));
            param.add(new BasicNameValuePair("a", alamat.getKodeDetailBarang()));
            jParser.makeHttpRequest(alamat.getAlamatUrl(), "POST", param);
            Log.d("do", "internet");
            try {
                json = jParser.getJSONFromUrl(alamat.getAlamatUrl());
                data = json.getJSONArray("ayo");
                Log.d("do","inbackground");
                for (int i = 0; i < data.length(); i++) {

                    JSONObject c = data.getJSONObject(i);
                    Log.d("json", c.toString());

                    barang.setPath_foto2(c.getString(PATH_FOTO2));
                    barang.setPath_foto3(c.getString(PATH_FOTO3));
                    barang.setPath_foto4(c.getString(PATH_FOTO4));
                    barang.setKeterangan(c.getString(KETERANGAN));
                    barang.setDilihat(c.getString(DILIHAT));
                    barang.setTerjual(c.getString(TERJUAL));
                    barang.setStok(c.getString(STOK));

                    arrayList.add(barang.getKeterangan());
                    arrayList.add(barang.getDilihat());
                    arrayList.add(barang.getTerjual());
                    arrayList.add(barang.getStok());

                    Log.d("arrayList", arrayList.toString());

                    Log.d("path2", barang.getPath_foto2());
                    Log.d("keterangan",barang.getKeterangan());
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("POST", barang.getPath_foto2());
            //keterangan.setText(barang.getKeterangan());
            if(barang.getPath_foto2().equals("kosong") && barang.getPath_foto3().equals("kosong") && barang.getPath_foto4().equals("kosong"))
            {
                url_maps.put("1", path_foto1);
            }else if(barang.getPath_foto3().equals("kosong")&&barang.getPath_foto4().equals("kosong")){
                url_maps.put("1",path_foto1);
                url_maps.put("2",barang.getPath_foto2());
            }else if(barang.getPath_foto4().equals("kosong")){
                url_maps.put("1",path_foto1);
                url_maps.put("2",barang.getPath_foto2());
                url_maps.put("3",barang.getPath_foto3());
            }else{
                url_maps.put("1",path_foto1);
                url_maps.put("2",barang.getPath_foto2());
                url_maps.put("3",barang.getPath_foto3());
                url_maps.put("4",barang.getPath_foto4());
            }


            for(String name : url_maps.keySet()){
                TextSliderView textSliderView = new TextSliderView(DetailBarang.this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(DetailBarang.this);

//            ListViewAdapterBarang adapter = new ListViewAdapterBarang(DetailBarang.this,arrayList);
//            lv.setAdapter(adapter);
            ArrayAdapter adapter = new ArrayAdapter(DetailBarang.this,R.layout.p_list_row,arrayList);
            lv.setAdapter(adapter);
        }
    }

    public boolean cekKoneksi() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//            switch (item.getItemId()){
//                case android.R.id.home :
//                    onBackPressed();
//
//                    return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//





    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.item,menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_custom_indicator:
                mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
                break;
            case R.id.action_custom_child_animation:
                mDemoSlider.setCustomAnimation(new ChildAnimationExample());
                break;
            case R.id.action_restore_default:
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                break;
            case R.id.action_github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia/AndroidImageSlider"));
                startActivity(browserIntent);
                break;


                case android.R.id.home :
                    onBackPressed();

                    return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}


}
