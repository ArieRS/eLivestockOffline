package com.ui.listfragment;

import com.ui.elivestock.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListDataSapiFragment extends ListFragment {
	String[] month ={
			"January", 
			"February", 
			"March", 
			"April",
			"May", 
			"June", 
			"July", 
			"August",
			"September", 
			"October", 
			"November", 
			"December"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListAdapter myListAdapter = new ArrayAdapter<String>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				month);
		setListAdapter(myListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_item_crime, container, false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(
				getActivity(), 
				getListView().getItemAtPosition(position).toString(), 
				Toast.LENGTH_LONG).show();
	}
}

/*
 ArrayList<ListDataSapi> listDataSapiModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listDataSapiModel = new ArrayList<ListDataSapi>();
		
		listDataSapiModel = DummyDataListDataSapi();
        DataSapiAdapter adapter = new DataSapiAdapter(listDataSapiModel);
        setListAdapter(adapter);
	}

	private class DataSapiAdapter extends ArrayAdapter<ListDataSapi> {
	     
		public DataSapiAdapter(ArrayList<ListDataSapi> crimes) {
            super(getActivity(), android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_crime, null);
            }

            // configure the view for this Crime
            ListDataSapi c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getNIT());
            TextView dateTextView =
                (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getLokasi());
//            CheckBox solvedCheckBox =
//                (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
//            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
	}
	
	public ArrayList<ListDataSapi> DummyDataListDataSapi(){
		ArrayList<ListDataSapi> tempListDataSapiModel = new ArrayList<ListDataSapi>();
		ListDataSapi newItemListDataSapi = new ListDataSapi();
		
		newItemListDataSapi.setNO("0");
		newItemListDataSapi.setNIT("999");
		newItemListDataSapi.setLokasi("Budi");
		tempListDataSapiModel.add(newItemListDataSapi);
		
		newItemListDataSapi.setNO("1");
		newItemListDataSapi.setNIT("123");
		newItemListDataSapi.setLokasi("Udina");
		tempListDataSapiModel.add(newItemListDataSapi);
		
		return tempListDataSapiModel;
	}
 
 
 
 * */
