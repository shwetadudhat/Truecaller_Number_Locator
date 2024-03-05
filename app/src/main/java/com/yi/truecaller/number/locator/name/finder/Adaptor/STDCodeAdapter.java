package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yi.truecaller.number.locator.name.finder.Constructor.STDCode_Constructor;
import com.yi.truecaller.number.locator.name.finder.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class STDCodeAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<STDCode_Constructor> stdCodeConstructors;
    private List<STDCode_Constructor> stdCodeConstructors_areacodelist = null;

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    public class ViewHolder {
        TextView tv_country;
        TextView tv_rank;

        public ViewHolder() {
        }
    }

    public STDCodeAdapter(Context context2, List<STDCode_Constructor> list) {
        this.context = context2;
        this.stdCodeConstructors_areacodelist = list;
        this.layoutInflater = LayoutInflater.from(context2);
        ArrayList<STDCode_Constructor> arrayList = new ArrayList<>();
        this.stdCodeConstructors = arrayList;
        arrayList.addAll(list);
    }

    @Override
    public int getCount() {
        return this.stdCodeConstructors_areacodelist.size();
    }

    public STDCode_Constructor getItem(int i) {
        return this.stdCodeConstructors_areacodelist.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.layoutInflater.inflate(R.layout.stdcode_item, (ViewGroup) null);
            viewHolder.tv_rank = (TextView) view2.findViewById(R.id.number_code);
            viewHolder.tv_country = (TextView) view2.findViewById(R.id.std_code);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView textView = viewHolder.tv_rank;
        textView.setText("0" + this.stdCodeConstructors_areacodelist.get(i).getAreacode());
        viewHolder.tv_country.setText(this.stdCodeConstructors_areacodelist.get(i).getAreaname());
        return view2;
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.stdCodeConstructors_areacodelist.clear();
        if (lowerCase.length() == 0) {
            this.stdCodeConstructors_areacodelist.addAll(this.stdCodeConstructors);
        } else {
            Iterator<STDCode_Constructor> it = this.stdCodeConstructors.iterator();
            while (it.hasNext()) {
                STDCode_Constructor next = it.next();
                if (next.getAreacode().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.stdCodeConstructors_areacodelist.add(next);
                }
                if (next.getAreaname().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.stdCodeConstructors_areacodelist.add(next);
                }
            }
        }
        notifyDataSetChanged();
    }
}
