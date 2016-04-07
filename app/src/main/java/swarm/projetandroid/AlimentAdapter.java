package swarm.projetandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlimentAdapter extends ArrayAdapter<Aliment> {
    Aliment[] aliments = null;
    Context context;
    List<CheckBox> checkBoxes;
    
    public AlimentAdapter(Context context, Aliment[] resource) {
        super(context, R.layout.activity_choices, resource);
        this.context = context;
        this.aliments = resource;
        checkBoxes = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.aliment_item, parent, false);

        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
        cb.setText(aliments[position].getName());
        cb.setChecked(aliments[position].isChecked());
        checkBoxes.add(cb);

        return convertView;
    }

    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }
}
