package com.example.gergo.companyx;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class UserList extends AppCompatActivity {

    private Database db;
    private ListView lwUserList;
    private Button btUserListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();

        ArrayList<HashMap<String,String>> userList = db.viewUsers();
        ListAdapter adapter = new SimpleAdapter(UserList.this, userList, R.layout.user_list_row,new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});
        lwUserList.setAdapter(adapter);

        btUserListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserListBack = new Intent(UserList.this,AdminUserMenu.class);
                startActivity(UserListBack);
                finish();
            }
        });
    }

    public void init(){
        db = new Database(this);
        lwUserList = (ListView) findViewById(R.id.lwUserList);
        btUserListBack = (Button) findViewById(R.id.btUserListBack);
    }
}
