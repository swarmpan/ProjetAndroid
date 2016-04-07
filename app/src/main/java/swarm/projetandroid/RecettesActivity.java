package swarm.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecettesActivity extends AppCompatActivity {

    List<String> alimentsChecked;
    JSONObject recettesJson;
    JSONArray nomsRecettes;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recettes);

        Intent intent = getIntent();
        String pays = intent.getStringExtra("pays");
        alimentsChecked = intent.getStringArrayListExtra("aliments");
        try {
            recettesJson = JSONParser.parseJson("recettes.json", getAssets()).getJSONObject(pays);
            if (recettesJson != null) {
                nomsRecettes = recettesJson.names();
                prepareListData();
                listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
                expListView = (ExpandableListView) findViewById(R.id.expandableListView);
                expListView.setAdapter(listAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView text = (TextView) findViewById(R.id.paysText);
        text.setText("Recettes de " + pays);
    }

    /*
  * Preparing the list data
  */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        try {
            for (int i = 0; i < nomsRecettes.length(); i++) {
                listDataHeader.add(nomsRecettes.getString(i));

                List<String> ingredients = new ArrayList<>();
                JSONArray ingredientsJson = recettesJson.getJSONArray(nomsRecettes.getString(i));
                for (int j = 0; j < ingredientsJson.length(); j++) {
                    ingredients.add(ingredientsJson.getString(j));
                }

                listDataChild.put(listDataHeader.get(i), ingredients);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
