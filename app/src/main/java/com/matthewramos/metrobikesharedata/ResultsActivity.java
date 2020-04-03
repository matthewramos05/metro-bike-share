package com.matthewramos.metrobikesharedata;

import java.util.LinkedHashMap;
import java.util.List;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class ResultsActivity extends AppCompatActivity {

    public static String results = "";

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_expandablelistview);

        Gson gson = new Gson();

        LinkedHashMap<String, List<String>> groupedStatistics = gson
                .fromJson(results, new ResultsActivity.LinkedHashMapTypeToken().getType());

        // create expandable list view
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        ExpandableListAdapter expandableListAdapter = new ExpandableListViewAdapter(this, groupedStatistics);
        expandableListView.setAdapter(expandableListAdapter);
        // expand the first group by default
        expandableListView.expandGroup(0);
    }

    private static class LinkedHashMapTypeToken extends TypeToken<LinkedHashMap<String, List<String>>> {
    }

}
