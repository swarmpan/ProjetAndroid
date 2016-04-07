package swarm.projetandroid;

import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Swarm on 07/04/2016.
 */
public class JSONParser {

    public static JSONObject parseJson(String path, AssetManager manager) {
        JSONObject jsonFile = null;
        try {
            InputStream is = manager.open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer);
            jsonFile = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonFile;
    }
}
