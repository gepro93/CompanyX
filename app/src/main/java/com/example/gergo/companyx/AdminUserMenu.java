package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminUserMenu extends AppCompatActivity {

    Button btUserCreate, btUserModify, btUserDelete, btUserStatus, btUserList, btUserMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_menu);
        init();

        btUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userList = new Intent(AdminUserMenu.this, UserList.class);
                startActivity(userList);
                finish();
            }
        });

        btUserMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminVissza = new Intent(AdminUserMenu.this, AdminMenu.class);
                startActivity(adminVissza);
                finish();
            }
        });

        btUserCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminUserMenu.this,UserAdd.class));
                finish();
            }
        });

        btUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminUserMenu.this,UserDelete.class));
                finish();
            }
        });

        btUserModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminUserMenu.this, UserModifyList.class));
                finish();
            }
        });
    }

    public void init(){
        btUserCreate = (Button) findViewById(R.id.btUserCreate);
        btUserModify = (Button) findViewById(R.id.btUserModify);
        btUserDelete = (Button) findViewById(R.id.btUserDelete);
        btUserStatus = (Button) findViewById(R.id.btUserStatus);
        btUserList = (Button) findViewById(R.id.btUserList);
        btUserMenuBack = (Button) findViewById(R.id.btUserMenuBack);
    }

}
