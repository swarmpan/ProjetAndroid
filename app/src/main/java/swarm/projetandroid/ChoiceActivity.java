package swarm.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class ChoiceActivity extends AppCompatActivity {
    ListView listView;
    Aliment[] aliments;
    AlimentAdapter adapter;
    String pays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        listView = (ListView) findViewById(android.R.id.list);


        JSONObject jsonFile = JSONParser.parseJson("aliments.json", getAssets());

        JSONArray names = jsonFile.names();
        aliments = new Aliment[names.length()];
        try {
            for (int i = 0; i < names.length(); i++) {
                aliments[i] = new Aliment(names.getString(i), false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new AlimentAdapter(this, aliments);
        listView.setAdapter(adapter);
        pays = getIntent().getStringExtra("pays");

        Button searchButton = (Button) findViewById(R.id.button);
    }

    public void onSearchButtonClick(View v) {
        Intent intent = new Intent(ChoiceActivity.this, RecettesActivity.class);
        intent.putExtra("pays", this.pays);

        ArrayList<String> alimentsChecked = new ArrayList<>();
        for (CheckBox cb : adapter.getCheckBoxes()) {
            if (cb.isChecked())
                alimentsChecked.add(cb.getText().toString());
        }
        intent.putStringArrayListExtra("aliments", alimentsChecked);

        startActivity(intent);
    }
}