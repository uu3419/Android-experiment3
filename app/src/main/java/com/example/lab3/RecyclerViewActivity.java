package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实验5：RecyclerView + Adapter + ViewHolder。
 * 单击列表项显示名称；点击“增加一条记录”后列表立即更新。
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private static final String[] NAMES = {
            "Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"
    };
    private static final int[] IMAGES = {
            R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant
    };

    private final List<AnimalAdapter.Animal> mData = new ArrayList<>();
    private AnimalAdapter mAdapter;
    private TextView mCountView;
    private int mAddedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        setTitle(R.string.task5_title);

        for (int i = 0; i < NAMES.length; i++) {
            mData.add(new AnimalAdapter.Animal(NAMES[i], IMAGES[i]));
        }

        mCountView = findViewById(R.id.tv_count);
        updateCount();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AnimalAdapter(mData, new AnimalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AnimalAdapter.Animal animal) {
                // 单击显示名称
                Toast.makeText(RecyclerViewActivity.this,
                        "点击了：" + animal.name, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);

        Button add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
            }
        });
    }

    /** 增加一条记录，并用 notifyItemInserted 让列表立即更新 */
    private void addRecord() {
        mAddedCount++;
        int index = mAddedCount % NAMES.length;
        String name = NAMES[index] + " #" + mAddedCount;
        mData.add(new AnimalAdapter.Animal(name, IMAGES[index]));
        mAdapter.notifyItemInserted(mData.size() - 1);

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.scrollToPosition(mData.size() - 1);
        updateCount();
    }

    private void updateCount() {
        mCountView.setText("共 " + mData.size() + " 条记录");
    }
}
