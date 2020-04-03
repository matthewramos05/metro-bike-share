package com.matthewramos.metrobikesharedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.esri.arcgisruntime.arcgisservices.LabelDefinition;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.UniqueValueRenderer;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MondayActivity extends AppCompatActivity {

    private MapView mMapView;
    private Callout mCallout;
    private ServiceFeatureTable mServiceFeatureTable;
    private FeatureLayer mFeatureLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

        mMapView = (MapView) findViewById(R.id.mapView);
        // create an ArcGISMap with BasemapType topo
        final ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.0552, -118.2437, 14);
        // set the ArcGISMap to the MapView
        mMapView.setMap(map);
        // get the callout that shows attributes
        mCallout = mMapView.getCallout();
        // create the service feature table
        //String url = "https://services6.arcgis.com/gL8apIToHKNgxu2m/arcgis/rest/services/bike_share_master_app/FeatureServer/0";
        String url = "https://services6.arcgis.com/gL8apIToHKNgxu2m/arcgis/rest/services/bike_share_master_app2/FeatureServer/0";
        mServiceFeatureTable = new ServiceFeatureTable(url);
        // create the feature layer using the service feature table
        //final FeatureLayer featureLayer = new FeatureLayer(mServiceFeatureTable);
        mFeatureLayer = new FeatureLayer(mServiceFeatureTable);
        // add the layer to the map
        //map.getOperationalLayers().add(featureLayer);
        //mFeatureLayer = new FeatureLayer(mServiceFeatureTable);

        //*******************
        // Peak Day Renderer
        //*******************

        // Override the renderer of the feature layer with a new unique value renderer
        UniqueValueRenderer uniqueValueRenderer = new UniqueValueRenderer();
        // Set the field to use for the unique values
        uniqueValueRenderer.getFieldNames().add(
                "peak_day"); //You can add multiple fields to be used for the renderer in the form of a list, in this case
        // we are only adding a single field

        // Create the symbols to be used in the renderer
        SimpleMarkerSymbol mondaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#15964f"), 10);
        SimpleMarkerSymbol tuesdaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#40b876"), 10);
        SimpleMarkerSymbol wednesdaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#28ed81"), 10);
        SimpleMarkerSymbol thursdaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#6fe607"), 10);
        SimpleMarkerSymbol fridaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#ebe305"), 10);
        SimpleMarkerSymbol saturdaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#eb9b34"), 10);
        SimpleMarkerSymbol sundaySymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.parseColor("#d9145f"), 10);

        List<Object> mondayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        mondayValue.add("monday");
        uniqueValueRenderer.getUniqueValues().add(
                new UniqueValueRenderer.UniqueValue("Peak day is Monday", "Monday", mondaySymbol, mondayValue));

        // Set value for tuesday
        List<Object> tuesdayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        tuesdayValue.add("tuesday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Tuesday", "Tuesday", tuesdaySymbol, tuesdayValue));

        // Set value for wednesday
        List<Object> wednesdayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        wednesdayValue.add("Wednesday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Wednesday", "Wednesday", wednesdaySymbol, wednesdayValue));

        // Set value for thursday
        List<Object> thursdayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        thursdayValue.add("Thursday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Thursday", "Thursday", thursdaySymbol, thursdayValue));

        // Set value for friday
        List<Object> fridayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        fridayValue.add("Friday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Friday", "Friday", fridaySymbol, fridayValue));

        // Set value for saturday
        List<Object> saturdayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        saturdayValue.add("Saturday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Saturday", "Saturday", saturdaySymbol, saturdayValue));

        // Set value for sunday
        List<Object> sundayValue = new ArrayList<>();
        // You add values associated with fields set on the unique value renderer.
        // If there are multiple values, they should be set in the same order as the fields are set
        sundayValue.add("Sunday");
        uniqueValueRenderer.getUniqueValues()
                .add(new UniqueValueRenderer.UniqueValue("Peak day is Sunday", "Sunday", sundaySymbol, sundayValue));

        // Set the renderer on the feature layer
        mFeatureLayer.setRenderer(uniqueValueRenderer);
        //[DocRef: END]

        // add the layer to the map
        map.getOperationalLayers().add(mFeatureLayer);
        //map.getOperationalLayers().add(mFeatureLayer);


        // set an on touch listener to listen for click events
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // remove any existing callouts
                if (mCallout.isShowing()) {
                    mCallout.dismiss();
                }
                // get the point that was clicked and convert it to a point in map coordinates
                final Point clickPoint = mMapView
                        .screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
                // create a selection tolerance
                int tolerance = 10;
                double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
                // use tolerance to create an envelope to query
                Envelope envelope = new Envelope(clickPoint.getX() - mapTolerance, clickPoint.getY() - mapTolerance,
                        clickPoint.getX() + mapTolerance, clickPoint.getY() + mapTolerance, map.getSpatialReference());
                QueryParameters query = new QueryParameters();
                query.setGeometry(envelope);
                // request all available attribute fields
                final ListenableFuture<FeatureQueryResult> future = mServiceFeatureTable
                        .queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
                // add done loading listener to fire when the selection returns
                future.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //call get on the future to get the result
                            FeatureQueryResult result = future.get();
                            // create an Iterator
                            Iterator<Feature> iterator = result.iterator();
                            // create a TextView to display field values
                            TextView calloutContent = new TextView(getApplicationContext());
                            calloutContent.setTextColor(Color.BLACK);
                            calloutContent.setSingleLine(false);
                            calloutContent.setVerticalScrollBarEnabled(true);
                            calloutContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                            calloutContent.setMovementMethod(new ScrollingMovementMethod());
                            calloutContent.setLines(5);
                            // cycle through selections
                            int counter = 0;
                            Feature feature;
                            while (iterator.hasNext()) {
                                feature = iterator.next();
                                // create a Map of all available attributes as name value pairs
                                Map<String, Object> attr = feature.getAttributes();
                                Set<String> keys = attr.keySet();
                                for (String key : keys) {
                                    Object value = attr.get(key);
                                    // format observed field value as date
                                    if (value instanceof GregorianCalendar) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                                        value = simpleDateFormat.format(((GregorianCalendar) value).getTime());
                                    }
                                    // Testing to get single attribute
                                    if (key.equals("start_station")){
                                        calloutContent.append("Station ID:" + " | " + value + "\n");
                                    }

                                    if (key.equals("start_count")) {
                                        calloutContent.append("Total Trips:" + " | " + value + "\n");
                                    }

                                    if (key.equals("peak_day")){
                                        calloutContent.append("Peak Day:" + " | " + value + "\n");
                                    }

                                    if (key.equals("station_name")) {
                                        calloutContent.append("Station Name:" + " | " + value + "\n");
                                    }
                                    // append name value pairs to TextView
                                    //calloutContent.append(key + " | " + value + "\n");
                                }
                                counter++;
                                // center the mapview on selected feature
                                Envelope envelope = feature.getGeometry().getExtent();
                                mMapView.setViewpointGeometryAsync(envelope, 200);
                                // show CallOut
                                mCallout.setLocation(clickPoint);
                                mCallout.setContent(calloutContent);
                                mCallout.show();
                            }
                        } catch (Exception e) {
                            Log.e(getResources().getString(R.string.app_name), "Select feature failed: " + e.getMessage());
                        }
                    }
                });
                return super.onSingleTapConfirmed(e);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.query:
                Intent queIntent = new Intent(MondayActivity.this, QueryActivity.class);
                startActivity(queIntent);
                return true;
            case R.id.all:
                Intent allIntent = new Intent(MondayActivity.this, MainActivity.class);
                startActivity(allIntent);
                return true;
            case R.id.tuesday:
                Intent tueIntent = new Intent(MondayActivity.this, TuesdayActivity.class);
                startActivity(tueIntent);
                return true;
            case R.id.wednesday:
                Intent wedIntent = new Intent(MondayActivity.this, WednesdayActivity.class);
                startActivity(wedIntent);
                return true;
            case R.id.thursday:
                Intent thuIntent = new Intent(MondayActivity.this, ThursdayActivity.class);
                startActivity(thuIntent);
                return true;
            case R.id.friday:
                Intent friIntent = new Intent(MondayActivity.this, FridayActivity.class);
                startActivity(friIntent);
                return true;
            case R.id.saturday:
                Intent satIntent = new Intent(MondayActivity.this, SaturdayActivity.class);
                startActivity(satIntent);
                return true;
            case R.id.sunday:
                Intent sunIntent = new Intent(MondayActivity.this, SundayActivity.class);
                startActivity(sunIntent);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }
}
