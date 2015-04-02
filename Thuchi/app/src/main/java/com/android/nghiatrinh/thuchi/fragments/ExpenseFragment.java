package com.android.nghiatrinh.thuchi.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nghiatrinh.thuchi.R;
import com.android.nghiatrinh.thuchi.activitys.AddNewExpenseActivity;
import com.android.nghiatrinh.thuchi.activitys.EditExpenseActivity;
import com.android.nghiatrinh.thuchi.activitys.MainActivity;
import com.android.nghiatrinh.thuchi.helpers.Helper;
import com.android.nghiatrinh.thuchi.model.Expense;
import com.android.nghiatrinh.thuchi.model.ExpenseComparatorByDate;
import com.android.nghiatrinh.thuchi.model.ExpenseComparatorByID;
import com.android.nghiatrinh.thuchi.model.ExpenseListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * Created by NghiaTrinh on 3/2/2015.
 */
public class ExpenseFragment extends Fragment {

    ArrayList<Expense> incomes = new ArrayList<>();
    String bydate=null;
    String bymonth=null;
    String byyear=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.income_fragment_layout,container,false);
        Helper.setActiveFragment(getActivity(),false,false,true);
        ImageButton button_new = (ImageButton)view.findViewById(R.id.add_new_income);
        TextView title_list = (TextView)view.findViewById(R.id.textview_title_list);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewExpenseActivity.class);
                startActivity(intent);
            }
        });
        List<Expense> list;
        ListView listView = (ListView)view.findViewById(R.id.listview_list_income);

        double total=0;
        View line_break = view.findViewById(R.id.line_space_break);
        TextView totalTextview = (TextView)view.findViewById(R.id.textview_total);

        if (getArguments()!=null) {
            bydate = getArguments().getString("bydate");
            bymonth = getArguments().getString("bymonth");
            byyear = getArguments().getString("byyear");
        }

        title_list.setText(R.string.today);
        list = Expense.getByDate(Helper.formatDate(Calendar.getInstance(),true));
        Collections.sort(list,new ExpenseComparatorByID());
        if (bydate!=null)
        {
            title_list.setText(Helper.formatDate(bydate));
            list = Expense.getByDate(bydate);
            Collections.sort(list,new ExpenseComparatorByID());
        }
        if (bymonth!=null)
        {
            title_list.setText(bymonth);
            list = Expense.getByMonth(bymonth);
            Collections.sort(list,new ExpenseComparatorByDate());
        }
        if (byyear!=null)
        {
            title_list.setText(byyear);
            list = Expense.getByYear(byyear);
            Collections.sort(list,new ExpenseComparatorByDate());
        }
        for (Expense income:list)
        {
            total+=income.getAmount();
            incomes.add(income);
        }
        if (incomes.size()==0)
        {
            //hide
            line_break.setVisibility(View.INVISIBLE);
            totalTextview.setVisibility(View.INVISIBLE);
        }
        totalTextview.setText(Helper.formatMoney(total));
        ExpenseListAdapter adapter = new ExpenseListAdapter(getActivity(),incomes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView incomeId = (TextView)view.findViewById(R.id.itemId);
                Expense income = Expense.findById(Expense.class,Long.parseLong(incomeId.getText().toString()));
                if (income !=null)
                {
                    if (!income.getDescription().isEmpty())
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(income.getDescription())
                                .setTitle(Helper.formatDate(income.getDate()))
                                .setNegativeButton(R.string.close,null)
                                .show();
                    }
                }
            }
        });
        registerForContextMenu(listView);
        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.clearHeader();
        menu.add(0, 0, 0, R.string.edit);
        menu.add(0, 1, 1, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Expense income = incomes.get(info.position);
        switch (item.getOrder())
        {
            case 0:
                Intent intent = new Intent(getActivity(),EditExpenseActivity.class);
                intent.putExtra("incomeid", income.getId().toString());
                startActivity(intent);
                return true;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.confirm_delete)
                        .setIcon(R.drawable.ic_action_warning)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                income.delete();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                intent.putExtra("display","expense");
                                if (bydate!=null)
                                {
                                    intent.putExtra("bydate",bydate);
                                }
                                if (bymonth!=null)
                                {
                                    intent.putExtra("bymonth",bymonth);
                                }
                                if (byyear!=null)
                                {
                                    intent.putExtra("byyear",byyear);
                                }
                                getActivity().finish();
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
                return true;
        }
        return true;
    }
}
