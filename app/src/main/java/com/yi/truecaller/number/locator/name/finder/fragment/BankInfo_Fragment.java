package com.yi.truecaller.number.locator.name.finder.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.yi.truecaller.number.locator.name.finder.Adaptor.BankDataBaseAdapter;
import com.yi.truecaller.number.locator.name.finder.Adaptor.BankListAdapter;
import com.yi.truecaller.number.locator.name.finder.Model.BankInfo_Model;
import com.yi.truecaller.number.locator.name.finder.R;

import java.util.ArrayList;
import java.util.List;

public class BankInfo_Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /* access modifiers changed from: private */
    public List<BankInfo_Model> bankmodellist;
    Context context;
    Cursor cursor;
    BankDataBaseAdapter db_adpater;
    private GridView gridView;
    ImageView iv_back;
    protected OnFragmentInteractionListener listener;
    protected String sg_param1;
    protected String sg_param2;
    View view;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static Fragment newInstance() {
        return new BankInfo_Fragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.sg_param1 = getArguments().getString(ARG_PARAM1);
            this.sg_param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = viewGroup;
        try {
            this.view = layoutInflater.inflate(R.layout.bank_list_fragment, viewGroup, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }

        this.context = getActivity();
        this.bankmodellist = new ArrayList();
        BankDataBaseAdapter bankDataBase_Adapter = new BankDataBaseAdapter(this.context);
        this.db_adpater = bankDataBase_Adapter;
        bankDataBase_Adapter.createDatabase();
        try {
            this.db_adpater.open();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.cursor = this.db_adpater.get_all();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        Cursor cursor2 = this.cursor;
        if (cursor2 != null && cursor2.getCount() > 0) {
            int count = this.cursor.getCount();
            System.out.println("Length " + count);
            this.cursor.moveToFirst();
            while (count > 0) {
                count--;
                this.bankmodellist.add(new BankInfo_Model(this.cursor.getString(0).trim(), this.cursor.getString(1).trim(), this.cursor.getString(2).trim(), this.cursor.getString(3).trim(), this.cursor.getString(4).trim()));
                this.cursor.moveToNext();
            }
        }
        this.gridView = (GridView) this.view.findViewById(R.id.gridView);
        this.gridView.setAdapter(new BankListAdapter(getActivity(), this.bankmodellist));
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(BankInfo_Fragment.this.getActivity(), CheckBankBalanceActivity.class);
                intent.putExtra("pos", i);
                intent.putExtra("enquiry", ((BankInfo_Model) BankInfo_Fragment.this.bankmodellist.get(i)).getBank_inquiry());
                intent.putExtra("customer", ((BankInfo_Model) BankInfo_Fragment.this.bankmodellist.get(i)).getBank_care());
                intent.putExtra("image", ((BankInfo_Model) BankInfo_Fragment.this.bankmodellist.get(i)).getBank_img());
                intent.putExtra("bankName", ((BankInfo_Model) BankInfo_Fragment.this.bankmodellist.get(i)).getBank_name());
                BankInfo_Fragment.this.startActivity(intent);
            }
        });
        return this.view;
    }

    @Override
    public void onDestroyView() {
        ViewGroup viewGroup;
        super.onDestroyView();
        View view2 = this.view;
        if (view2 != null && (viewGroup = (ViewGroup) view2.getParent()) != null) {
            Log.d("TAG", "destroy on device");
            viewGroup.removeAllViews();
        }
    }

    @Override
    public void onAttach(Context context2) {
        super.onAttach(context2);
        if (context2 instanceof OnFragmentInteractionListener) {
            this.listener = (OnFragmentInteractionListener) context2;
            return;
        }
        throw new RuntimeException(context2.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
