package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yi.truecaller.number.locator.name.finder.Activity.SimCardActivity;
import com.yi.truecaller.number.locator.name.finder.R;

public class SimDetailsAdapter extends RecyclerView.Adapter<SimDetailsAdapter.MyViewHolder> {
    public Activity activity;
    String[] detalis = new String[8];
    private LayoutInflater layoutInflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_datailspad;
        public final TextView tv_desc;
        public final TextView tv_title;

        public MyViewHolder(SimDetailsAdapter simDetails_Adapter, SimDetailsAdapter simDetails_Adapter2, View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.txtTitle);
            this.tv_desc = (TextView) view.findViewById(R.id.txtdesc);
            this.iv_datailspad = (ImageView) view.findViewById(R.id.dialpadcall);
        }
    }

    public SimDetailsAdapter(Activity activity2, String[] strArr) {
        this.activity = activity2;
        this.layoutInflater = LayoutInflater.from(activity2);
        this.detalis = strArr;
    }

    @Override
    public int getItemCount() {
        return this.detalis.length;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(SimCardActivity.arr[i]);
        myViewHolder.tv_desc.setText(this.detalis[i]);
        myViewHolder.iv_datailspad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Activity activity = SimDetailsAdapter.this.activity;
                activity.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + myViewHolder.tv_desc.getText().toString())));
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(this, this, this.layoutInflater.inflate(R.layout.call_item, viewGroup, false));
    }
}
