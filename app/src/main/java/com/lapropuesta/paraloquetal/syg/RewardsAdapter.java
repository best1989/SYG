package com.lapropuesta.paraloquetal.syg;

/**
 * Created by Gabriel on 12/12/2015.
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RewardsAdapter extends BaseAdapter {
    private static ArrayList<Rewards> searchArrayList;

    private LayoutInflater mInflater;

    public RewardsAdapter(Context context, ArrayList<Rewards> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_listview_rewards_item, null);
            holder = new ViewHolder();
            holder.txtDescrip = (TextView) convertView.findViewById(R.id.rwdescrip);
            holder.txtPoints = (TextView) convertView
                    .findViewById(R.id.rwpoints);
            holder.txtPDate = (TextView) convertView.findViewById(R.id.rwdate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDescrip.setText(searchArrayList.get(position).getDescription());
        holder.txtPoints.setText(searchArrayList.get(position)
                .getPoints());
        holder.txtPDate.setText(searchArrayList.get(position).getPointsDate());

        return convertView;
    }

    static class ViewHolder {
        TextView txtDescrip;
        TextView txtPoints;
        TextView txtPDate;
    }
}