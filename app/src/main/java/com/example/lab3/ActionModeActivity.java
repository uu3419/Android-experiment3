package com.example.lab3;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实验4：ListView + ActionMode（MultiChoiceMode）。
 * 长按列表项进入上下文操作模式，顶部显示已选数量，点击垃圾桶图标删除选中项。
 */
public class ActionModeActivity extends ListActivity {

    private final List<String> mItems = new ArrayList<>(Arrays.asList(
            "One", "Two", "Three", "Four", "Five"));

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionmode);
        setTitle(R.string.task4_title);

        mAdapter = new ArrayAdapter<>(this, R.layout.item_actionmode, R.id.tv_label, mItems);
        setListAdapter(mAdapter);

        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // ActionMode 标题显示已选数量，如 "1 selected"
                int count = getListView().getCheckedItemCount();
                mode.setTitle(count + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_actionmode, menu);
                mode.setTitle("0 selected");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    deleteSelected();
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // 退出上下文模式后清空选中状态
                getListView().clearChoices();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /** 删除所有被勾选的列表项（从后往前删，避免索引错位） */
    private void deleteSelected() {
        SparseBooleanArray checked = getListView().getCheckedItemPositions();
        int removed = 0;
        for (int i = checked.size() - 1; i >= 0; i--) {
            if (checked.valueAt(i) && checked.keyAt(i) < mItems.size()) {
                mItems.remove(checked.keyAt(i));
                removed++;
            }
        }
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "已删除 " + removed + " 项", Toast.LENGTH_SHORT).show();
    }
}
