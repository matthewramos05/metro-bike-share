package com.matthewramos.metrobikesharedata;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;

    private void setupMap() {
        if (mMapView != null) {
            String itemId = "41281c51f9de45edaf1c8ed44bb10e30";
            Portal portal = new Portal("https://www.arcgis.com", false);
            PortalItem portalItem = new PortalItem(portal, itemId);
            ArcGISMap map = new ArcGISMap(portalItem);
            mMapView.setMap(map);
        }
    }

//    private void setupMap() {
//        if (mMapView != null) {
//            String itemId = "c9b2241a0e874244bcab2527a59fbcf0";
//            Portal portal = new Portal("https://www.arcgis.com", false);
//            PortalItem portalItem = new PortalItem(portal, itemId);
//            ArcGISMap map = new ArcGISMap(portalItem);
//            mMapView.setMap(map);
//        }
//    }

//    private void setupMap() {
//        if (mMapView != null) {
//            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
//            double latitude = 34.0552;
//            double longitude = -118.2437;
//            int levelOfDetail = 13;
//            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
//            mMapView.setMap(map);
//        }
//    }
//
//    private void addBikeShareLayer() {
//        String url = "https://services6.arcgis.com/gL8apIToHKNgxu2m/arcgis/rest/services/bike_share_master_end_/FeatureServer";
//        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
//        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
//        ArcGISMap map = mMapView.getMap();
//        map.getOperationalLayers().add(featureLayer);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // *** ADD ***
        mMapView = findViewById(R.id.mapView);
        setupMap();

        // *** ADD bike share layer***
        //addBikeShareLayer();
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
