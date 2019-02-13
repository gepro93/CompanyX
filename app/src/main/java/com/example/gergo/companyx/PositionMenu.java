package com.example.gergo.companyx;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PositionMenu extends AppCompatActivity {

    private Button btPositionCreate, btPositionModify, btPositionDelete, btPositionList, btPositionMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_menu);
        init();

        btPositionCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPermission();
            }
        });

    }

    public void init(){
        btPositionCreate = (Button) findViewById(R.id.btPositionCreate);
        btPositionModify = (Button) findViewById(R.id.btPositionModify);
        btPositionDelete = (Button) findViewById(R.id.btPositionDelete);
        btPositionList = (Button) findViewById(R.id.btPositionList);
        btPositionMenuBack = (Button) findViewById(R.id.btPositionMenuBack);
    }

    public void createPermission(){
        AlertDialog.Builder alert = new AlertDialog.Builder(PositionMenu.this);

        alert.setMessage("Beosztás megnevezése:");
        alert.setTitle("Létrehozás");

        LinearLayout layout = new LinearLayout(PositionMenu.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText position = new EditText(PositionMenu.this);
        position.setHint("Beosztás");
        position.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        position.setGravity(Gravity.CENTER);
        position.setPadding(0,0,0,60);
        layout.addView(position);

        final Spinner grade = new Spinner(PositionMenu.this,Spinner.MODE_DROPDOWN);
        final List<String> grades = new ArrayList<>();
        grades.add(0, "Fizetés besorolás");
        grades.add("Grade1");
        grades.add("Grade2");
        grades.add("Grade3");
        grades.add("Grade4");


        //Grade lista adapter létrehozása
        ArrayAdapter<String> statusDataAdapter;
        statusDataAdapter = new ArrayAdapter(PositionMenu.this, android.R.layout.simple_spinner_item, grades);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(statusDataAdapter);
        layout.addView(grade);

        layout.setPadding(0,30,0,30);
        alert.setView(layout);

        alert.setPositiveButton("Mentés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PositionMenu.this);

        builder.setCancelable(true);
        builder.setTitle("Kilépés");
        builder.setMessage("Valóban be szeretnéd zárni az alkalmazást?");

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
