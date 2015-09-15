package com.radomar.les19;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.radomar.les19.adapters.NotificationRecyclerAdapter;
import com.radomar.les19.db.DatabaseHelper;
import com.radomar.les19.model.NotificationModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private DatabaseHelper mDbHelper;
    private NotificationRecyclerAdapter mAdapter;
    private List<NotificationModel> mData;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        mDbHelper = new DatabaseHelper(this);

        mData = readNotificationsFromDatabase();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvList_AM);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationRecyclerAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_main, _menu);

        return super.onCreateOptionsMenu(_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case R.id.delete_all_notifications:
                mData.clear();
                mAdapter.notifyDataSetChanged();
                mDbHelper.dropTable();
                break;
        }

        return super.onOptionsItemSelected(_item);
    }


    private void getToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    Log.d(TAG, "Token: " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tbToolbar_AM);
        setSupportActionBar(mToolbar);
    }

    public ArrayList<NotificationModel> readNotificationsFromDatabase() {

        ArrayList<NotificationModel> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DatabaseHelper.NOTIFICATIONS_TABLE_NAME;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                if (cursor.moveToFirst()) {
                    do {
                        NotificationModel notificationModel = new NotificationModel();
                        notificationModel.title = cursor.getString(0);
                        notificationModel.subTitle = cursor.getString(1);
                        notificationModel.message = cursor.getString(2);

                        list.add(notificationModel);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

}

