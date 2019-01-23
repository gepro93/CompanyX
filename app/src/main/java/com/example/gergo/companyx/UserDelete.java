package com.example.gergo.companyx;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UserDelete extends AppCompatActivity {

    private Database db;
    private ListView lwUserDelete;
    private Button btUserDeleteExec, btUserDeleteBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);
        init();

        final ArrayList<HashMap<String,String>> userList = db.viewUsers();
        final ListAdapter adapter = new SimpleAdapter(UserDelete.this, userList, R.layout.user_delete_row,new String[]{"USER_NAME","PERMISSION_NAME","USER_STATUS"}, new int[]{R.id.twName, R.id.twPermission, R.id.twStatus});
        lwUserDelete.setAdapter(adapter);



        btUserDeleteExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos  = lwUserDelete.getCheckedItemPosition();

                if (pos > -1)
                {
                    //Törlés adapterből
                    LinkedList<Integer> list = new LinkedList<Integer>();
                    for(int i=0;i<adapter.getCount();i++){
                        if(checks.get(i)==true)
                        {
                            list.addFirst(i);
                        }
                    }

                }
            }
        });

        btUserDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDelete.this,AdminUserMenu.class));
            }
        });

    }
    public void init(){
        db = new Database(this);
        lwUserDelete = (ListView) findViewById(R.id.lwUserDelete);
        btUserDeleteExec = (Button) findViewById(R.id.btUserDeleteExec);
        btUserDeleteBack = (Button) findViewById(R.id.btUserDeleteBack);
    }

}
