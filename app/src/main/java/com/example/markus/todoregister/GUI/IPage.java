package com.example.markus.todoregister.GUI;

import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Markus on 21.4.2017.
 * Interface for different pages
 */

public interface IPage {

     void delete(BaseAdapter adapter, int id);
     void setAdapter(ListView listView);
     void getAdapter();
     void fillListView(IRead read);
     void getListView(int resourceID);
     void registerContextMenu(ListView listView);


}
