package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModifyList extends AppCompatActivity {

    private Database db;
    private Button btUserModBack, btUserMod;
    private ListView lwUserModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify_list);
        init();


        final ArrayList<HashMap<String,String>> userList = db.viewUsers();
        final ListAdapter adapter = new SimpleAdapter(UserModifyList.this, userList, R.layout.user_modify_row,
                new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});

        lwUserModify.setAdapter(adapter);

        lwUserModify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });

        btUserModBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos  = lwUserModify.getCheckedItemPosition();

                if (pos > -1)
                {
                    View item = lwUserModify.getChildAt(pos);
                    TextView twUser = (TextView)item.findViewById(R.id.twName);
                    TextView twPerm = (TextView)item.findViewById(R.id.twPermission);
                    TextView twStat = (TextView)item.findViewById(R.id.twStatus);
                    String userName = twUser.getText().toString();
                    String userPerm = twPerm.getText().toString();
                    String userStat = twStat.getText().toString();

                    SharedPreferences sp = getSharedPreferences("UserModify",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("username", userName);
                    editor.putString("userperm", userPerm);
                    editor.putString("userstat", userStat);

                    editor.apply();
                    editor.commit();

                    startActivity(new Intent(UserModifyList.this, AdminUserMenu.class));
                    finish();
                }else{
                    Toast.makeText(UserModifyList.this, "Módosításhoz jelölj ki egy elemet!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btUserMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserModifyList.this, UserModify.class));
            }
        });

    }

    public void init(){
        db = new Database(this);
        btUserModBack = (Button) findViewById(R.id.btUserModBack);
        btUserMod = (Button) findViewById(R.id.btUserMod);
        lwUserModify = (ListView) findViewById(R.id.lwUserModify);
    }
}
