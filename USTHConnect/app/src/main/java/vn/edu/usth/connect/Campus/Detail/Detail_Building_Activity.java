package vn.edu.usth.connect.Campus.Detail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationManager;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.Arrays;
import java.util.Optional;

import vn.edu.usth.connect.Campus.Date_adapter;
import vn.edu.usth.connect.R;

public class Detail_Building_Activity extends AppCompatActivity {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_detail_building.xml
        setContentView(R.layout.activity_detail_building);

        // Set text for RecyclerView
        setup_text_recyclerview();

        // Function for backbutton
        setup_function();
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    // Set text building's name in Header and building's location
    private void setup_text_recyclerview(){
        TextView building_name = findViewById(R.id.detail_building_name);
        TextView building_locate = findViewById(R.id.detail_building_locate);

        String name = getIntent().getStringExtra("Building Name");
        String locate = getIntent().getStringExtra("Building Locate");

        building_name.setText(name);
        building_locate.setText(locate);

        // Retrieve coordinates
        String latitude = "21.048152";
        String longitude = "105.8011776";

        // Set the static map with marker
        ImageView mapImageView = findViewById(R.id.mapImage);

        // Create the Static Map URL using the  latitude, longitude, and access token
        String mapboxUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/" +
                longitude + "," + latitude + ",17,0/800x600?access_token=" + getString(R.string.mapbox_access_token) +
                "&markers=" + longitude + "," + latitude + "&marker-color=%23FF0000"; // Red marker

        // Load static map into ImageView using Glide
        Glide.with(this)
                .load(mapboxUrl)
                .into(mapImageView);

//        mapView = findViewById(R.id.mapImage);
//        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
//            mapboxMap = mapView.getMapboxMap();
//
//            // Set the camera to building's location
//            mapboxMap.setCamera(new CameraOptions.Builder()
//                    .center(Point.fromLngLat(Double.parseDouble(longitude), Double.parseDouble(latitude)))
//                    .pitch(0.0)
//                    .zoom(15.0)
//                    .build());

            // Add custom marker after the style is loaded
//            addCustomMarker(latitude, longitude);
//        });
    }

//    private void addCustomMarker(String latitude, String longitude) {
//        bitmapFromDrawables(R.drawable.red_marker) // Use your drawable resource
//                .ifPresent(bitmap -> {
//                    PointAnnotationManager pointAnnotationManager = mapView.getMapboxMap()
//                            .getAnnotationPlugin()
//                            .createPointAnnotationManager(mapView);
//
//                    // Set options for the point annotation
//                    PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
//                            .withPoint(Point.fromLngLat(Double.parseDouble(longitude), Double.parseDouble(latitude)))
//                            .withIconImage(bitmap);
//
//                    // Add the point annotation to the map
//                    pointAnnotationManager.create(pointAnnotationOptions);
//                });
//    }

    // Helper method to convert drawable resource to bitmap
//    private Optional<Bitmap> bitmapFromDrawables(int resourceId) {
//        Drawable drawable = AppCompatResources.getDrawable(this, resourceId);
//        return convertDrawableToBitmap(drawable);
//    }
//
//    private Optional<Bitmap> convertDrawableToBitmap(Drawable drawable) {
//        if (drawable == null) {
//            return java.util.Optional.empty();
//        }
//        if (drawable instanceof BitmapDrawable) {
//            return java.util.Optional.of(((BitmapDrawable) drawable).getBitmap());
//        } else {
//            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
//            return java.util.Optional.of(bitmap);
//        }
//    }


//     BackPress: backto BuildingFragment
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}