package com.example.ajipradana.lapak.Grid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajipradana.lapak.Loader.ImageLoader;
import com.example.ajipradana.lapak.R;
import com.example.ajipradana.lapak.UserInterface.KategoriBarang;
import com.example.ajipradana.lapak.UserInterface.PilihanKategori;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji on 5/1/2016.
 */
public class GridViewAdapterKategori extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    ArrayList<HashMap<String,String>> kategori;
    HashMap<String,String > resultp = new HashMap<String,String >();
    private LayoutInflater inflater;

    ImageLoader imageLoader;



    public GridViewAdapterKategori(Context context,int layoutResourceId,ArrayList<HashMap<String,String>> kategori){
        super(context, layoutResourceId, kategori);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.kategori = kategori;
        imageLoader = new ImageLoader(context);
    }
    @Override
    public Object getItem(int position) {

        return kategori.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView namaKategori;
        ImageView gambar;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.g_kategori,parent,false);

        resultp = kategori.get(position);

        namaKategori = (TextView)itemView.findViewById(R.id.grid_nama_barang);
        gambar = (ImageView)itemView.findViewById(R.id.grid_kategori);
        namaKategori.setText(resultp.get(PilihanKategori.KATEGORI));

        imageLoader.DisplayImage(resultp.get(PilihanKategori.PATH_FOTO_KATEGORI), gambar);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = kategori.get(position);

                Intent intent = new Intent(context, KategoriBarang.class);
                intent.putExtra("kodeJenis",resultp.get(PilihanKategori.ID_KATEGORI));

                context.startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


}
