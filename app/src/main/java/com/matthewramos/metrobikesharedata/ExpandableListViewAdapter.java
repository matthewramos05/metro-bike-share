package com.matthewramos.metrobikesharedata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

class ExpandableListViewAdapter extends BaseExpandableListAdapter{

    private final Context context;
    private final List<String> mGroupList;
    private final LinkedHashMap<String, List<String>> mStatList;

    public ExpandableListViewAdapter(Context context, LinkedHashMap<String, List<String>> statList) {
        this.context = context;
        mGroupList = new ArrayList<>(statList.keySet());
        mStatList = statList;
    }

    @Override
    public Object getChild(int groupListPosition, int statListPosition) {
        return mStatList.get(mGroupList.get(groupListPosition)).get(statListPosition);
    }

    @Override
    public long getChildId(int groupPosition, int statListPosition) {
        return statListPosition;
    }

    @Override
    public View getChildView(int groupListPosition, final int statListPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        final String expandedListText = (String) getChild(groupListPosition, statListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        }
        TextView expandedListTextView = convertView.findViewById(android.R.id.text1);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return mStatList.get(mGroupList.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return mGroupList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        }
        TextView listTitleTextView = convertView.findViewById(android.R.id.text1);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
