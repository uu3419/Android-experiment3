package com.example.lab3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 实验3：使用 XML 定义菜单。
 * 字体大小：小 10sp / 中 16sp / 大 20sp；普通菜单项 Toast；字体颜色：红 / 黑。
 */
public class MenuActivity extends AppCompatActivity {

    private TextView mTestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle(R.string.task3_title);
        mTestText = findViewById(R.id.tv_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.font_small) {
            mTestText.setTextSize(10);
            return true;
        } else if (id == R.id.font_medium) {
            mTestText.setTextSize(16);
            return true;
        } else if (id == R.id.font_large) {
            mTestText.setTextSize(20);
            return true;
        } else if (id == R.id.color_red) {
            mTestText.setTextColor(getResources().getColor(R.color.red));
            return true;
        } else if (id == R.id.color_black) {
            mTestText.setTextColor(getResources().getColor(R.color.black));
            return true;
        } else if (id == R.id.menu_normal) {
            Toast.makeText(this, R.string.normal_menu_toast, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
