package com.example.lab3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 实验2：AlertDialog.Builder.setView() 加载自定义布局。
 */
public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setTitle(R.string.task2_title);

        Button open = findViewById(R.id.btn_show_dialog);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInDialog();
            }
        });
    }

    private void showSignInDialog() {
        View content = getLayoutInflater().inflate(R.layout.dialog_signin, null);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(content)
                .create();

        final EditText username = content.findViewById(R.id.et_username);
        final EditText password = content.findViewById(R.id.et_password);

        content.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        content.findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pwd = password.getText().toString();
                if (TextUtils.isEmpty(user)) {
                    username.setError("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    password.setError("请输入密码");
                    return;
                }
                Toast.makeText(DialogActivity.this,
                        "登录成功：" + user, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
