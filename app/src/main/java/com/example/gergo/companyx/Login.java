package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity{

    private Database db;
    private EditText etUsername, etPassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        //Bejelentkezés
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String userName = etUsername.getText().toString();
            String userPassword = etPassword.getText().toString();

            Boolean userCheck = db.userCheck(userName,userPassword);
            Integer permCheck = db.userPermissionCheck(userName,userPassword);
            Boolean statusCheck = db.userStatusCheck(userName,userPassword);

             if(userName.equals("") && userPassword.equals("")){
                 etUsername.setError("Kérlek add meg a felhasználó neved!");
                 etPassword.setError("Kérlek add meg a jelszavad!");

             }
             else if (userName.equals("")){
                 etUsername.setError("Kérlek add meg a felhasználó neved!");
             }
             else if (userPassword.equals("")){
                 etPassword.setError("Kérlek add meg a jelszavad!");
             }else{
                if (userCheck){
                    switch (permCheck){
                        case 1:
                            if (statusCheck){
                                Intent adminBelep = new Intent(Login.this, AdminMenu.class);
                                startActivity(adminBelep);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Inaktív admin profil", Toast.LENGTH_SHORT).show();
                            }break;
                        case 2:
                            if (statusCheck){
                                Intent hrBelep = new Intent(Login.this, HRMenu.class);
                                startActivity(hrBelep);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Inaktív HR profil", Toast.LENGTH_SHORT).show();
                            }break;
                        case 3:
                            if (statusCheck){
                                Intent facilitesBelep = new Intent(Login.this, FacilitiesMenu.class);
                                startActivity(facilitesBelep);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Inaktív Facilities profil!", Toast.LENGTH_SHORT).show();
                            }break;
                        case 4:
                            if (statusCheck){
                                Intent employeeBelep = new Intent(Login.this, EmployeeMenu.class);
                                startActivity(employeeBelep);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Inaktív Employee profil!", Toast.LENGTH_SHORT).show();
                            }break;
                        default:
                            Toast.makeText(Login.this, "Státusz hiba!", Toast.LENGTH_SHORT).show();
                    }
                } else if (userCheck==false && userName.equals("admin") && userPassword.equals("admin1234")){
                    Boolean crateAdmin = db.insertAdmin();
                    Boolean createPerm = db.createPermissions();
                    if (crateAdmin && createPerm)
                    {
                        if (statusCheck){
                            Intent adminBelep = new Intent(Login.this, AdminMenu.class);
                            startActivity(adminBelep);
                            finish();
                        }else{
                            Toast.makeText(Login.this, "Profilod inaktív, lépj kapcsolatba az adminisztrátorral!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Login.this, "Hiba az admin profil létrehozásában!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(Login.this, "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });

    }

    //Inicializálás
    public void init(){
        db = new Database(this);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
    }

    //Vissza gomb megnyomásakor dialog box használata
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setCancelable(true);
        builder.setTitle("Kilépés");
        builder.setMessage("Valóban be szeretnéd zárni az alkalmazást?");
        builder.setIcon(R.drawable.ic_dialog_error);

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });
        builder.show();
    }

}
