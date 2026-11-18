package com.example.lab3;

import android.Manifest;
import android.app.ListActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实验1：ListView + SimpleAdapter。
 * 列表项 = 名称 + 动物图片；点击弹出 Toast，并发送一条通知。
 */
public class ListViewActivity extends ListActivity {

    private static final String CHANNEL_ID = "lab3_listview";
    private static final int REQ_NOTIFICATION = 100;
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    private static final String[] NAMES = {
            "Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"
    };
    private static final int[] IMAGES = {
            R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        setTitle(R.string.task1_title);

        createNotificationChannel();

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < NAMES.length; i++) {
            Map<String, Object> row = new HashMap<>();
            row.put(KEY_NAME, NAMES[i]);
            row.put(KEY_IMAGE, IMAGES[i]);
            data.add(row);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_animal,
                new String[]{KEY_NAME, KEY_IMAGE},
                new int[]{R.id.tv_name, R.id.iv_image});
        setListAdapter(adapter);

        // Android 13+ 需要运行时申请通知权限
        if (Build.VERSION.SDK_INT >= 33
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIFICATION);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String name = NAMES[position];

        // (3) Toast 显示选中的列表项信息
        Toast.makeText(this, "选中第 " + (position + 1) + " 项：" + name, Toast.LENGTH_SHORT).show();

        // (4) 发送通知：图标为应用图标，Title 为列表项内容
        sendNotification(name);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "实验3 列表通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("点击列表项后发送的通知");
            nm.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String name) {
        if (Build.VERSION.SDK_INT >= 33
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "未授予通知权限，仅显示 Toast", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ListViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle(name)
                .setContentText("你选中了列表项：" + name + "，点击返回列表")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify((int) System.currentTimeMillis(), builder.build());
    }
}
