package com.example.gergo.companyx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserAdd extends AppCompatActivity {

    private Spinner spPermission, spStatus;
    private EditText etUsername_add, etPassword_add_1, etPassword_add_2;
    private Button btUserAdd, btUserAddBack;
    private Integer selectedPermisson;
    private Integer selectedStatus;
    private Database db;
    private ProgressDialog progress;
    private Boolean userStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        init();

        //Jogosultság lista létrehozása
        List<String> permission = new ArrayList<>();
        permission.add(0,"Válassz hozzáférést!");
        permission.add("Admin");
        permission.add("HR");
        permission.add("Beszerzés");
        permission.add("Dolgozó");

        //Státusz lista létrehozás
        final List<String> status = new ArrayList<>();
        status.add(0,"Válassz profil státuszt!");
        status.add("Aktív");
        status.add("Inaktív");

        //Jogosultság lista adapter létrehozása
        ArrayAdapter<String> permissionDataAdapter;
        permissionDataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,permission);
        permissionDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPermission.setAdapter(permissionDataAdapter);

        //Státusz lista adapter létrehozása
        ArrayAdapter<String> statusDataAdapter;
        statusDataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(statusDataAdapter);



        spPermission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Válassz hozzáférést!"))
                {
                    selectedPermisson = 0;
                }
                else
                {
                    selectedPermisson = adapterView.getSelectedItemPosition();
                    Toast.makeText(adapterView.getContext(),"Selected: " + selectedPermisson, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Válassz profil státuszt!"))
                {
                    selectedStatus = 0;
                }
                else
                {
                    selectedStatus = adapterView.getSelectedItemPosition();
                    Toast.makeText(adapterView.getContext(), "Selected: "+ selectedStatus, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etUsername_add.getText().toString();
                String userPassword1 = etPassword_add_1.getText().toString();
                String userPassword2 = etPassword_add_2.getText().toString();

                switch (selectedStatus){
                    case 1: userStatus = true; break;
                    case 2: userStatus = false; break;
                    default: break;
                }


                //Adatbázis lekérdezések
                Boolean userCheck = db.userNameCheck(userName);

                if(etUsername_add.equals("") || etPassword_add_1.equals("") || etPassword_add_2.equals("") || selectedPermisson == 0 || selectedStatus == 0)
                {
                    Toast.makeText(UserAdd.this, "Hoppá, valamit nem töltöttél ki!", Toast.LENGTH_SHORT).show();
                }
                else if (!userPassword1.equals(userPassword2))
                {
                    etPassword_add_1.setError("A jelszavaknak meg kell egyezniük!");
                    etPassword_add_2.setError("A jelszavaknak meg kell egyezniük!");
                }
                else
                {
                    if (!userCheck)
                    {
                        Boolean userCreation = db.insertUser(userName,userPassword1,userStatus,selectedPermisson);
                        if (userCreation)
                        {
                            progressDialog();
                            etUsername_add.getText().clear();
                            etPassword_add_1.getText().clear();
                            etPassword_add_2.getText().clear();
                            spPermission.setSelection(0);
                            spStatus.setSelection(0);
                        }
                        else
                        {
                            Toast.makeText(UserAdd.this, "Hiba a létrehozáskor!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(UserAdd.this, "Ilyen felhasználó már létezik!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btUserAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserAdd.this,AdminUserMenu.class));
                finish();
            }
        });
    }

    public void init(){
        spPermission = (Spinner) findViewById(R.id.spPermission);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        etUsername_add = (EditText) findViewById(R.id.etUsername_add);
        etPassword_add_1 = (EditText) findViewById(R.id.etPassword_add_1);
        etPassword_add_2 = (EditText) findViewById(R.id.etPassword_add_2);
        btUserAdd = (Button) findViewById(R.id.btUserAdd);
        btUserAddBack = (Button) findViewById(R.id.btUserAddBack);
        db = new Database(this);
    }

    public void progressDialog(){
        progress = new ProgressDialog(UserAdd.this);
        progress.setMax(100);
        progress.setMessage("Felhasználó létrehozása folyamatban...");
        progress.setTitle("Létrehozás");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(progress.getProgress() <= progress.getMax()){
                        Thread.sleep(40);
                        handler.sendMessage(handler.obtainMessage());
                        if(progress.getProgress() == progress.getMax()){
                            progress.dismiss();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.incrementProgressBy(1);
        }
    };
}
