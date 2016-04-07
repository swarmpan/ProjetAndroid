package swarm.projetandroid;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import swarm.projetandroid.R;

public class MapsActivity extends FragmentActivity {

    GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        googleMap = supportMapFragment.getMap();

        googleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {

                latLng = arg0;

                googleMap.clear();

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                markerOptions = new MarkerOptions();

                markerOptions.position(latLng);

                googleMap.addMarker(markerOptions);

                ReverseGeocodingTask task = new ReverseGeocodingTask(getBaseContext());
                task.execute(latLng);
            }
        });
    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String>{
        Context context;

        public ReverseGeocodingTask(Context context){
            super();
            this.context = context;
        }

        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(context);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;


            List<Address> addresses = null;
            String addressText="";
            String countryNameText="";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null && addresses.size() > 0 ){
                Address address = addresses.get(0);

                countryNameText = String.format(address.getCountryName());
                addressText = String.format("%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName());
            }
            Log.d("Nom du pays cliqué : ", countryNameText) ;
            Log.d("Adresse", addressText) ;
            return countryNameText;
        }

        @Override
        protected void onPostExecute(String addressText) {
            markerOptions.title(addressText);
            googleMap.addMarker(markerOptions);
            Intent intent = new Intent(MapsActivity.this, ChoiceActivity.class);
            intent.putExtra("pays", addressText);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}