package com.example.ajipradana.lapak.Grid;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajipradana.lapak.Loader.ImageLoader;
import com.example.ajipradana.lapak.R;
import com.example.ajipradana.lapak.UserInterface.DetailBarang;
import com.example.ajipradana.lapak.UserInterface.KategoriBarang;
import com.example.ajipradana.lapak.UserInterface.Navigation;

import java.util.ArrayList;
import java.util.HashMap;

public class GridViewAdapterHalamanUtama extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private LayoutInflater inflater;



    private ArrayList<HashMap<String,String>> barang;
    ImageLoader imageLoader;
    HashMap<String,String > resultp = new HashMap<String,String >();

    public GridViewAdapterHalamanUtama(Context context, int layoutResourceId, ArrayList<HashMap<String, String>> barang) {
        super(context, layoutResourceId, barang);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.barang = barang;
        imageLoader = new ImageLoader(context);
    }


    @Override
    public Object getItem(int position) {
        return barang.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView nama_barang;
        TextView harga;
        TextView kategori;
        ImageView gambar;


        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.grid_item_layout_halaman_utama,parent,false);

        resultp = barang.get(position);

        nama_barang = (TextView)itemView.findViewById(R.id.grid_nama_barang);
        harga = (TextView)itemView.findViewById(R.id.grid_harga_barang);
        gambar = (ImageView)itemView.findViewById(R.id.grid_image);
        kategori = (TextView)itemView.findViewById(R.id.kategori);

        Log.d("path foto cuk :", resultp.get(Navigation.PATH_FOTO));
        Log.d("nama barang :", resultp.get(Navigation.NAMA_BARANG));


            if(resultp.get(Navigation.NAMA_BARANG).length()>15){
                nama_barang.setText(resultp.get(Navigation.NAMA_BARANG).substring(0, 15));
                harga.setText("Rp. "+resultp.get(Navigation.HARGA));
                kategori.setText(resultp.get(Navigation.KATEGORI));
                imageLoader.DisplayImage(resultp.get(Navigation.PATH_FOTO), gambar);
            }else{
                nama_barang.setText(resultp.get(Navigation.NAMA_BARANG));
                harga.setText("Rp. "+resultp.get(Navigation.HARGA));
                kategori.setText(resultp.get(Navigation.KATEGORI));
                imageLoader.DisplayImage(resultp.get(Navigation.PATH_FOTO), gambar);
            }






        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultp = barang.get(position);

                Intent intent = new Intent(context, DetailBarang.class);
                intent.putExtra("id_barang", resultp.get(Navigation.ID_BARANG));
                intent.putExtra("nama_barang", resultp.get(Navigation.NAMA_BARANG));
                intent.putExtra("harga", resultp.get(Navigation.HARGA));
                intent.putExtra("path_foto1",resultp.get(Navigation.PATH_FOTO));
                intent.putExtra("nama",resultp.get(Navigation.NAMA_PENJUAL));
                intent.putExtra("no_telp",resultp.get(Navigation.NO_TELP));

                context.startActivity(intent);

            }
        });
        return itemView;
    }

}