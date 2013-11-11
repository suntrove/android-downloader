/*******************************************************************************
 * Copyright (C) 2013 Snowdream Mobile
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.github.snowdream.android.apps.downloader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadTask;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author snowdream <yanghui1986527@gmail.com>
 * @date Sep 29, 2013
 * @version v1.0
 */
//@EActivity(R.layout.activity_main)
public class MainActivity extends ListActivity {
    private MenuDrawer mDrawer;
    private MenuAdapter mAdapter;
    private ListView mList;
    private int mActivePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.activity_main);
        mDrawer = MenuDrawer.attach(this);
        mDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mDrawer.setDrawerIndicatorEnabled(true);

        List<Object> items = new ArrayList<Object>();
        items.add(new Item("Item 1", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 2", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 1"));
        items.add(new Item("Item 3", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 4", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 2"));
        items.add(new Item("Item 5", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 6", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 3"));
        items.add(new Item("Item 7", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 8", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 4"));
        items.add(new Item("Item 9", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 10", R.drawable.ic_action_select_all_dark));

        mList = new ListView(this);
        mAdapter = new MenuAdapter(this, items);
        mList.setAdapter(mAdapter);
        mDrawer.setMenuView(mList);
        mList.setOnItemClickListener(mItemClickListener);

        TextView content = new TextView(this);
        content.setText("This is a sample of an overlayed left drawer.");
        content.setGravity(Gravity.CENTER);
        mDrawer.setContentView(content);
        mDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mDrawer.setDrawerIndicatorEnabled(true);
        mDrawer.peekDrawer(1000, 0);

        List<String> items1;
        items1 = new ArrayList<String>();
        for (int i = 1; i <= 20; i++) {
            items1.add("Item " + i);
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items1));


        DownloadTask task = new DownloadTask(this);
        task.setUrl("http://www.appchina.com/market/d/1019394/cop.baidu_0/com.hd.explorer.apk");
        task.setPath("/mnt/sdcard/10120702.apk");
        DownloadManager.add(task);
        DownloadManager.start(task, new DownloadListener<Integer, DownloadTask>());
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mActivePosition = position;
            mDrawer.setActiveView(view, position);
            mAdapter.setActivePosition(position);
            mDrawer.closeMenu();
        }
    };

    @Override
    public void setContentView(int layoutResID) {
        // This override is only needed when using MENU_DRAG_CONTENT.
        mDrawer.setContentView(layoutResID);
        onContentChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String str = (String) getListAdapter().getItem(position);
        Toast.makeText(this, "Clicked: " + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }
}