package com.example.gergo.companyx;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionModify extends AppCompatActivity {

    private Button btPositionMod, btPositionModBack;
    private ListView lwPositionModify;
    private Database db;
    private ArrayList<String> positionListByName;
    private ArrayList<Integer>  positionListByGrade;
    private int gradeId,selectedGrade;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_modify);
        init();

        final ArrayList<HashMap<String, String>> positionList = db.viewPositions();
        final ListAdapter adapter = new SimpleAdapter(PositionModify.this, positionList, R.layout.position_modify_row,
                new String[]{"POSITION_NAME", "GRADE_NAME", "SALARY_MIN_VALUE", "SALARY_MAX_VALUE"},
                new int[]{R.id.twPositionName, R.id.twPositionGradeName, R.id.twPositionSalaryFrom, R.id.twPositionSalaryTo});

        lwPositionModify.setAdapter(adapter);

        positionListByName = db.viewPositionsByName();
        //positionListByGrade = db.viewPositionsByGrade();

        lwPositionModify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btPositionMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionModify(positionList,adapter);
                lwPositionModify.setAdapter(adapter);
                ((SimpleAdapter) adapter).notifyDataSetChanged();
            }
        });


        btPositionModBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionModify.this,PositionMenu.class));
                finish();
            }
        });

    }

    public void init(){
        btPositionMod = (Button) findViewById(R.id.btPositionMod);
        btPositionModBack = (Button) findViewById(R.id.btPositionModBack);
        lwPositionModify = (ListView) findViewById(R.id.lwPositionModify);
        db = new Database(this);
    }

    public void positionModify(ArrayList<HashMap<String,String>> arrayList, ListAdapter listAdapter){
        int pos  = lwPositionModify.getCheckedItemPosition();

        if (pos > -1)
        {
            String positionName = positionListByName.get(pos);
            int gradeIdItem = positionListByGrade.get(pos);

            AlertDialog.Builder alert = new AlertDialog.Builder(PositionModify.this);

            alert.setMessage("Beosztás megnevezése:");
            alert.setTitle("Módosítás");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(PositionModify.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //Edit Text létrehozása
            final EditText position = new EditText(PositionModify.this);
            position.setHint("Beosztás");
            position.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            position.setGravity(Gravity.CENTER);
            position.setPadding(0,0,0,60);
            position.setText(positionName);
            layout.addView(position); //Edit Text hozzáadása layouthoz

            //Spinner létrehozása
            final Spinner grade = new Spinner(PositionModify.this,Spinner.MODE_DROPDOWN);

            //Spinner tartalmának elkészítése listában
            final List<String> grades = new ArrayList<>();
            grades.add("Grade 1");
            grades.add("Grade 2");
            grades.add("Grade 3");
            grades.add("Grade 4");

            //Grade lista adapter létrehozása
            ArrayAdapter<String> spinnerDataAdapter;
            spinnerDataAdapter = new ArrayAdapter(PositionModify.this, android.R.layout.simple_spinner_item, grades);
            spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            grade.setAdapter(spinnerDataAdapter);
            grade.setSelection(gradeIdItem);
            layout.addView(grade); //Spinner hozzáadása layouthoz

            layout.setPadding(0,30,0,30);
            alert.setView(layout);

            grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getItemAtPosition(i).equals("Grade 1")) {
                        gradeId = 1;
                    } else {
                        gradeId = adapterView.getSelectedItemPosition();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String etPosition = position.getText().toString();
                    boolean positionCheck = db.positionCheck(etPosition);
                    int pos  = lwPositionModify.getCheckedItemPosition();
                    String positionName = positionListByName.get(pos);

                    if (etPosition.equals("")){
                        position.setError("A mező kitöltése kötelező!");
                    }else if(positionCheck) {
                        position.setError("A beosztás már létezik!");
                    }else{
                        boolean positionModify = db.positionModify(positionName,etPosition,selectedGrade);
                        if (positionModify){
                            new Task1().execute();
                        }else Toast.makeText(PositionModify.this, "Adatbázis hiba módosításkor!", Toast.LENGTH_SHORT).show();
                    }
            }});
            alert.show();
        }
    }

    class Task1 extends AsyncTask<Void, Void, String> {

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progress.incrementProgressBy(1);
            }
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(PositionModify.this);
            progress.setMax(100);
            progress.setMessage("Felhasználó létrehozása folyamatban...");
            progress.setTitle("Létrehozás");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                while (progress.getProgress() <= progress.getMax()) {
                    Thread.sleep(40);
                    handler.sendMessage(handler.obtainMessage());
                    if(progress.getProgress() == progress.getMax()){
                        progress.dismiss();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(PositionModify.this, "Sikeres létrehozás!", Toast.LENGTH_LONG).show();
        }

    }
}
