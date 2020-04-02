package com.example.s3adoon.gp_v;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationListener;
        import android.os.Build;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.EditorInfo;
        import android.widget.AdapterView;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.PopupWindow;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.PendingResult;
        import com.google.android.gms.common.api.ResultCallback;
        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.LatLngBounds;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.gms.location.places.*;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.prefs.Preferences;

public class MapPsActivity extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener



{
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 99;
    private static final float DEFAULT_ZOOM = 15;
    private int PROXIMITY_RADIUS = 1000;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136)
    );
    private PlaceInfo mplace;
    private AutoCompleteTextView searchText;

    // Vars
    private boolean mLocationPermissionGranted = false;
    private boolean mSearchFlag = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    LatLng latLng;
    double latitude,longitude;
    double mLatitude,mLongitude;
    private static String unit;
    Marker mCurrLocationMarker;
    Location mLastLocation;
    private PlaceAutocompleteAdapter mplaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Marker marker;
    private ImageView info;
    View mapView;
    Button btnHospital;
    Button btnMedical;
    Button btnPharmacy;
    Button btnClinic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mappss);

        searchText = findViewById(R.id.input_search);

        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        int userLocationChoice = bundle.getInt("userLocationChoice");

        if(userLocationChoice == 0){
            Toast.makeText(this, "User Current Location", Toast.LENGTH_SHORT).show();
            RelativeLayout relativeLayout  = findViewById(R.id.relLayout1);
            ImageView imageView = findViewById(R.id.ic_search);
            AutoCompleteTextView autoCompleteTextView = findViewById(R.id.input_search);

            imageView.setVisibility(View.GONE);
            autoCompleteTextView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            ///***************************************************

        }
        else if(userLocationChoice ==1){
            mSearchFlag =true;
            Toast.makeText(this, "User Specific Location", Toast.LENGTH_SHORT).show();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getLocationPermission();
        }




    }

    // Getting Device Location
    private void getDeviceLocation() {

        System.out.println("Getting Device Location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            System.out.println("Entered the Try block");
            if (mLocationPermissionGranted) {

                Task location = mFusedLocationProviderClient.getLastLocation();
                System.out.println("Passed the first if condition");
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        System.out.println("Entered onComplete method");
                        if (task.isSuccessful()) {
                            System.out.println("passed the second if condition");
                            boolean flag;
                            System.out.println("Location Found");
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation == null){
                                Toast.makeText(MapPsActivity.this, "GPS is turned off", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                System.out.println("Setting latitude and longitude");
                                latitude = currentLocation.getLatitude();
                                longitude = currentLocation.getLongitude();
                                mLatitude = currentLocation.getLatitude();
                                mLongitude = currentLocation.getLongitude();
                                latLng= new LatLng(latitude,longitude);
                                System.out.println("Get device Location from main method" + longitude + "=====" + latitude);
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(new LatLng(latitude,longitude));
                                markerOptions.title("Current Position");
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mCurrLocationMarker = mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
                                System.out.println("Method end");
                            }
                        } else {
                            Toast.makeText(MapPsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

        } catch (SecurityException se) {
            System.out.println(se.getMessage());
        }

    }

    // Initialize the map
    private void initMap() {
        Log.d(TAG, "initMap: initializing Map");
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(MapPsActivity.this);
    }

    //Moving the Camera of the map
   /* protected void moveCamera(LatLng latLng, float zoom) {
        System.out.println("moving camera to : Latitude: " + latLng.latitude + " Longitude: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
    }*/

    // Checking Permissions Explicitly
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Getting Location Permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);

            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Called");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResults:  Permission Failed");
                            Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResults:  Permission Granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapRead: map is ready here");
        mMap = googleMap;

        if (mLocationPermissionGranted) {

            getDeviceLocation();

            //If permission is not granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }


            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            init();

            //***** Changing the Position of the Current Location Button
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            rlp.setMargins(0, 180, 180, 0);


            //*******Category Search Buttons*****************
            btnHospital = findViewById(R.id.hospital);
            btnMedical = findViewById(R.id.lab);
            btnPharmacy = findViewById(R.id.pharmacy);
            btnClinic = findViewById(R.id.clinic);

            btnHospital.setOnClickListener(this);
            btnClinic.setOnClickListener(this);
            btnMedical.setOnClickListener(this);
            btnPharmacy.setOnClickListener(this);

        }
    }

    //*****************************************

    private void nearByUnits(String unit){

        Log.d("onClick", "Button is Clicked");
        mMap.clear();

        if(mSearchFlag){
            latitude = mLatitude;
            longitude = mLongitude;
        }

        String url = getUrl(latitude, longitude, unit);
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = mMap;
        DataTransfer[1] = url;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
        Toast.makeText(MapPsActivity.this,"Nearby "+ unit, Toast.LENGTH_LONG).show();

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        String key = getString(R.string.google_maps_key);
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        System.out.println(latitude+"--- before calling device location---- " +longitude);
        if (latitude == 0.0 && longitude == 0.0)
        {
            getDeviceLocation();
            latitude = this.latitude;
            longitude = this.longitude;

        }
        System.out.println(latitude+"++++++ After calling device location +++++++++++++++"+longitude);
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + key);
        Log.d("getUrl", googlePlacesUrl.toString());
        return googlePlacesUrl.toString();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.hospital:
                unit = "hospital";
                nearByUnits(unit);
                break;
            case R.id.clinic:
                unit = "doctor";  // can be "doctor"
                nearByUnits(unit);
                break;
            case R.id.lab:
                unit="Laboratory";
                nearByUnits(unit);
                break;
            case R.id.pharmacy:
                nearByUnits(unit);
                unit="pharmacies";
                break;

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //getDeviceLocation();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "GPS is turned off", Toast.LENGTH_SHORT).show();
    }

    //**************************Search Using Specific Location
    private void init(){
        Log.d(TAG,"init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();
        searchText.setOnItemClickListener(mAutocompleteClickListener);

        mplaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,LAT_LNG_BOUNDS,null);

        searchText.setAdapter(mplaceAutocompleteAdapter);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent KeyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || KeyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || KeyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    //here we'll execute search method
                    geoLocate();

                }
                return false;
            }
        });


    }
    private  void geoLocate(){
        Log.d(TAG,"geoLocate: geoLocating");
        String searchString = searchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapPsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);

        }catch(IOException e){
            Log.e(TAG,"geoLocate: IOException: " + e.getMessage());
        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found  a location: "+address.toString());
            //Toast.makeText(this,address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),address.getAddressLine(0),DEFAULT_ZOOM);





        }
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final AutocompletePrediction item = mplaceAutocompleteAdapter.getItem(i);

            final String placeId = item.getPlaceId();


            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi

                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if(!places.getStatus().isSuccess()){

                Log.d(TAG, "onResult: Place query did not complete successfully: "+ places.getStatus().toString());
                places.release();

                return;
            }
            final Place place = places.get(0);

            try{

                mplace = new PlaceInfo();
                mplace.setName(place.getName().toString());
                mplace.setAddress(place.getAddress().toString());
                //mplace.setAttributions(place.getAttributions().toString());
                mplace.setPhoneNumber(place.getPhoneNumber().toString());
                mplace.setRating(place.getRating());
                mplace.setId(place.getId());
                // mplace.setLating(place.getLatLng());
                //mplace.setWebsiteUri(place.getWebsiteUri());

                Log.d(TAG,"onResult: place details: " + mplace.toString());

            }catch (NullPointerException e){
                Log.e(TAG,"onResult: NullPointerException " + e.getMessage());
            }

            mLatitude = place.getViewport().getCenter().latitude;
            mLongitude = place.getViewport().getCenter().longitude;
            LatLng mLatlng = new LatLng(mLatitude,mLongitude);
            //  moveCamera(new LatLng(mLatitude,mLongitude),"",DEFAULT_ZOOM);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatlng, DEFAULT_ZOOM));
            places.release();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mLatlng);
            markerOptions.title("Searched Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

            Toast.makeText(MapPsActivity.this, "Now you are Transfered", Toast.LENGTH_SHORT).show();


        }
    };
    protected void moveCamera(LatLng latLng, float zoom,PlaceInfo place) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: "+latLng.latitude+ ", lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapPsActivity.this));

        if(place != null){
            try{

                String snippet = "Address : " + place.getAddress()+ "\n" +
                        "Phone Number : " + place.getPhoneNumber()+ "\n" +
                        "Website : " + place.getWebsiteUri()+ "\n" +
                        "Place Rating : " + place.getRating()+ "\n" ;

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(place.getName())
                        .snippet(snippet);
                mMap.addMarker(options);
                marker = mMap.addMarker(options);

            }catch(NullPointerException e){
                Log.e(TAG,"moveCamera: NullPointerException: "+ e.getMessage());
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

    //Moving the Camera of the map
    protected void moveCamera(LatLng latLng,String title, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: "+latLng.latitude+ ", lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);

    }


}
