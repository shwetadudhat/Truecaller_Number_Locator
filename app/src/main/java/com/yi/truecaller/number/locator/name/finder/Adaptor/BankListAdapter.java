package com.yi.truecaller.number.locator.name.finder.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yi.truecaller.number.locator.name.finder.Model.BankInfo_Model;
import com.yi.truecaller.number.locator.name.finder.R;

import java.util.List;

public class BankListAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater;
    private List<BankInfo_Model> banklistmodel;
    protected final Activity context;

    public long getItemId(int i) {
        return (long) i;
    }

    static class ViewHolder {
        public ImageView imageView;
        public LinearLayout linearLayout;
        public TextView textView;

        ViewHolder() {
        }
    }

    public BankListAdapter(Activity activity, List<BankInfo_Model> list) {
        this.context = activity;
        this.banklistmodel = list;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.banklist_adapter, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.textView1);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView1);
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.main_l1);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        String bank_name = this.banklistmodel.get(i).getBank_name();
        String bank_name2 = this.banklistmodel.get(i).getBank_name();
        if (!(viewHolder2 == null || bank_name == null || bank_name2 == null)) {
            viewHolder2.textView.setText(bank_name);
            switch (i) {
                case 0:
                    viewHolder2.imageView.setImageResource(R.drawable.state_bank_of_india);
                    break;
                case 1:
                    viewHolder2.imageView.setImageResource(R.drawable.bank_of_baroda);
                    break;
                case 2:
                    viewHolder2.imageView.setImageResource(R.drawable.idbi_bank);
                    break;
                case 3:
                    viewHolder2.imageView.setImageResource(R.drawable.central_bank_of_india);
                    break;
                case 4:
                    viewHolder2.imageView.setImageResource(R.drawable.hdfc_bank);
                    break;
                case 5:
                    viewHolder2.imageView.setImageResource(R.drawable.citi_bank);
                    break;
                case 6:
                    viewHolder2.imageView.setImageResource(R.drawable.axis_bank);
                    break;
                case 7:
                    viewHolder2.imageView.setImageResource(R.drawable.kotak_bank);
                    break;
                case 8:
                    viewHolder2.imageView.setImageResource(R.drawable.yes_bank);
                    break;
                case 9:
                    viewHolder2.imageView.setImageResource(R.drawable.punjab_national_bank);
                    break;
                case 10:
                    viewHolder2.imageView.setImageResource(R.drawable.dena_bank);
                    break;
                case 11:
                    viewHolder2.imageView.setImageResource(R.drawable.canara_bank);
                    break;
                case 12:
                    viewHolder2.imageView.setImageResource(R.drawable.bank_of_india);
                    break;
                case 13:
                    viewHolder2.imageView.setImageResource(R.drawable.corporation_bank);
                    break;
                case 14:
                    viewHolder2.imageView.setImageResource(R.drawable.union_bank_of_india);
                    break;
                case 15:
                    viewHolder2.imageView.setImageResource(R.drawable.uco_bank);
                    break;
                case 16:
                    viewHolder2.imageView.setImageResource(R.drawable.vijaya_bank);
                    break;
                case 17:
                    viewHolder2.imageView.setImageResource(R.drawable.south_indian_bank);
                    break;
                case 18:
                    viewHolder2.imageView.setImageResource(R.drawable.american_express);
                    break;
                case 19:
                    viewHolder2.imageView.setImageResource(R.drawable.hsbc_bank);
                    break;
                case 20:
                    viewHolder2.imageView.setImageResource(R.drawable.federal_bank);
                    break;
                case 21:
                    viewHolder2.imageView.setImageResource(R.drawable.indian_overseas_bank);
                    break;
                case 22:
                    viewHolder2.imageView.setImageResource(R.drawable.ing_bank);
                    break;
                case 23:
                    viewHolder2.imageView.setImageResource(R.drawable.karur_vysya_bank);
                    break;
                case 24:
                    viewHolder2.imageView.setImageResource(R.drawable.abn_amro);
                    break;
                case 25:
                    viewHolder2.imageView.setImageResource(R.drawable.allhabad_bank);
                    break;
                case 26:
                    viewHolder2.imageView.setImageResource(R.drawable.andhra_bank);
                    break;
                case 27:
                    viewHolder2.imageView.setImageResource(R.drawable.anz_bank);
                    break;
                case 28:
                    viewHolder2.imageView.setImageResource(R.drawable.bank_of_maharashtra);
                    break;
                case 29:
                    viewHolder2.imageView.setImageResource(R.drawable.barclays_bank);
                    break;
                case 30:
                    viewHolder2.imageView.setImageResource(R.drawable.indian_bank);
                    break;
                case 31:
                    viewHolder2.imageView.setImageResource(R.drawable.bharatiya_mahila_bank);
                    break;
                case 32:
                    viewHolder2.imageView.setImageResource(R.drawable.punjab_and_sind_bank);
                    break;
                case 33:
                    viewHolder2.imageView.setImageResource(R.drawable.cashnet_bank);
                    break;
                case 34:
                    viewHolder2.imageView.setImageResource(R.drawable.saraswat_bank);
                    break;
                case 35:
                    viewHolder2.imageView.setImageResource(R.drawable.centurion_bank_of_punjab);
                    break;
                case 36:
                    viewHolder2.imageView.setImageResource(R.drawable.standard_chartered_bank);
                    break;
                case 37:
                    viewHolder2.imageView.setImageResource(R.drawable.state_bank_of_bikaner_and_jaipur);
                    break;
                case 38:
                    viewHolder2.imageView.setImageResource(R.drawable.deutsche_bank);
                    break;
                case 39:
                    viewHolder2.imageView.setImageResource(R.drawable.state_bank_of_travancore);
                    break;
                case 40:
                    viewHolder2.imageView.setImageResource(R.drawable.syndicate_bank);
                    break;
                case 41:
                    viewHolder2.imageView.setImageResource(R.drawable.dhanlaxmi_bank);
                    break;
                case 42:
                    viewHolder2.imageView.setImageResource(R.drawable.united_bank_of_india);
                    break;
                case 43:
                    viewHolder2.imageView.setImageResource(R.drawable.karnataka_bank);
                    break;
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return this.banklistmodel.size();
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }
}
