package com.example.macbook.getref;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by macbook on 11.04.16.
 */
public class ExpListAdapter extends BaseExpandableListAdapter {
    ArrayList<ArrayList<String>> mGroups;
    Context mContext;
    public ExpListAdapter(Context context, ArrayList<ArrayList<String>> groups){
        mGroups=groups;
        mContext=context;
    }
    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_button, null);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        Button button = (Button) convertView.findViewById(R.id.HomeButtonChild);
        button.setText(mGroups.get(groupPosition).get(childPosition));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setDifficulty(mGroups.get(groupPosition).get(childPosition));
            }
        });
        return convertView;
    }
    void setDifficulty(String mode){
        Toast.makeText(mContext,"now to next screen, "+mode,Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
