package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yi.truecaller.number.locator.name.finder.Constructor.ISDCode_Constructor;
import com.yi.truecaller.number.locator.name.finder.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ISDCodeAdapter extends BaseAdapter {
    Context context;
    private ArrayList<ISDCode_Constructor> isdCodeConstructorArrayList;
    private List<ISDCode_Constructor> isdCodeConstructors = null;
    LayoutInflater layoutInflater;

    public long getItemId(int i) {
        return (long) i;
    }

    public class ViewHolder {
        TextView tv_country;
        TextView tv_rank;

        public ViewHolder() {
        }
    }

    public ISDCodeAdapter(Context context2, List<ISDCode_Constructor> list) {
        this.context = context2;
        this.isdCodeConstructors = list;
        this.layoutInflater = LayoutInflater.from(context2);
        ArrayList<ISDCode_Constructor> arrayList = new ArrayList<>();
        this.isdCodeConstructorArrayList = arrayList;
        arrayList.addAll(list);
    }

    @Override
    public int getCount() {
        return this.isdCodeConstructors.size();
    }

    @Override
    public ISDCode_Constructor getItem(int i) {
        return this.isdCodeConstructors.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.layoutInflater.inflate(R.layout.isdcode_item, (ViewGroup) null);
            viewHolder.tv_rank = (TextView) view2.findViewById(R.id.number_code);
            viewHolder.tv_country = (TextView) view2.findViewById(R.id.country_code);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_rank.setText(this.isdCodeConstructors.get(i).getEmpId());
        viewHolder.tv_country.setText(this.isdCodeConstructors.get(i).getFirstName());
        return view2;
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.isdCodeConstructors.clear();
        if (lowerCase.length() == 0) {
            this.isdCodeConstructors.addAll(this.isdCodeConstructorArrayList);
        } else {
            Iterator<ISDCode_Constructor> it = this.isdCodeConstructorArrayList.iterator();
            while (it.hasNext()) {
                ISDCode_Constructor next = it.next();
                if (next.getEmpId().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.isdCodeConstructors.add(next);
                }
                if (next.getFirstName().toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.isdCodeConstructors.add(next);
                }
            }
        }
        notifyDataSetChanged();
    }
}