package com.example.ajipradana.lapak.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajipradana.lapak.Loader.ImageLoader;
import com.example.ajipradana.lapak.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AJI PRADANA on 4/8/2016.
 */

/*class ini digunakan untukk menampilkan daftar barang berupa list kebawah
* penggunaan kelas ini sudah tidak digunakan lagi karena sekarang tampilan barang menggunakan GRID*/
public class ListViewAdapterBarang extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<String> barang;
    ImageLoader imageLoader;
    String resultp;

    public ListViewAdapterBarang(Context context, List<String> barang){
        this.context = context;
        this.barang = barang;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return barang.size();
    }

    @Override
    public Object getItem(int item) {
        return barang.get(item);
    }

    @Override
    public long getItemId(int id_item) {
        return id_item;
    }

    @Override
    public View getView(final int id_item, View view, ViewGroup viewGroup) {
        TextView tampil;


        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_kategori_list_barang,viewGroup,false);

        resultp = barang.get(id_item);

        tampil = (TextView) itemView.findViewById(R.id.tampil);
        tampil.setText(resultp);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultp = barang.get(id_item);

            }
        });
        return itemView;
    }

}
