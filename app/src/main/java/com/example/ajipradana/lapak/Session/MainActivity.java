package com.example.ajipradana.lapak.Session;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ajipradana.lapak.Grid.GridViewAdapterHalamanUtama;
import com.example.ajipradana.lapak.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p/>
 * Calculate the position of the sticky header view according to the
 * position of the first item in the ListView. When the first item is already
 * reached to top, you don't need to position the sticky header view.
 *
 * @author Nilanchala
 */
public class MainActivity extends ActionBarActivity {

    private TextView stickyView;
    private GridView listView;
    private View heroImageView;

    private View stickyViewSpacer;

    private int MAX_ROWS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_activity_main);

        /* Initialise list view, hero image, and sticky view */
        listView = (GridView) findViewById(R.id.gridView);
        heroImageView = findViewById(R.id.heroImageView);
        stickyView = (TextView) findViewById(R.id.stickyView);

        /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.p_list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        /* Add list view header */


        /* Handle list View scroll events */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY * 0.5f);
                }
            }
        });


        /* Populate the ListView with sample data */
        ArrayList<HashMap<String,String>> arrayList;
        arrayList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("1", "satu");
        map.put("2", "dua");
        map.put("3", "tiga");
        map.put("4", "empat");
        map.put("5", "lima");
        map.put("6", "enam");
        map.put("7", "tujuh");

        arrayList.add(map);

       GridViewAdapterHalamanUtama adapter = new GridViewAdapterHalamanUtama(MainActivity.this, R.layout.grid_item_layout_kategori, arrayList);
        listView.setAdapter(adapter);
    }
}