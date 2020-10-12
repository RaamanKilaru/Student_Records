    package com.example.studentrecords;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static String TAG = "SAI";/*"SearchFragment";*/
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Inside onCreate() of SearchFragment.");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private View v;
    private List<StudentInfo> myList;
    private DatabaseHelper myDB;
    private FloatingActionButton fab;
    private Spinner sort_spinner;
    //private ArrayAdapter<CharSequence> sort_adapter;
    private Cursor listCursor;
    public String sorter = "";
    int i = 0;
    public String[] sortlist = {"Added Order", "Name", "Age"};

    RecyclerView myRv;
    RecyclerViewAdapter myAdapter;

    //Cursor listCursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        Log.i(TAG,"Inside onCreateView() of SearchFragment.");

        myRv = (RecyclerView) v.findViewById(R.id.recycler_view_id);

        //Spinner for Qualifications.
        sort_spinner = (Spinner) v.findViewById(R.id.sort_filter);
        sort_spinner.setOnItemSelectedListener(this);
        ArrayAdapter sort_adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,sortlist);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spinner.setAdapter(sort_adapter);
        /*sort_adapter = ArrayAdapter.createFromResource(
                getContext(),
                sortlist,
                android.R.layout.simple_spinner_item);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort_spinner.setAdapter(sort_adapter);*/


        return v;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG,"Inside onAttach() of SearchFragment.");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"Inside onActivityCreated() of SearchFragment.");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"Inside onStart() of SearchFragment.");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"Inside onResume() of SearchFragment.");
        myList = new ArrayList<>();
        myDB = new DatabaseHelper(v.getContext());
        /*fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
            }
        });*/
        final SwipeRefreshLayout swipe_refresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        listCursor = myDB.getListContents();

        if(sorter.equals("Added Order")){
            listCursor = myDB.getListContents();
            addTomyList(listCursor);
        }else if(sorter.equals("Name")){
            listCursor = myDB.getListContents_name_sorted();
            addTomyList(listCursor);
        }else if(sorter.equals("Age")){
            listCursor = myDB.getListContents_age_sorted();
            addTomyList(listCursor);
        }

        myAdapter = new RecyclerViewAdapter(getActivity(), myList);
        myRv.setLayoutManager(new GridLayoutManager(v.getContext(),3));
        myRv.setAdapter(myAdapter);

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                //myAdapter.notifyDataSetChanged();
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    public void addTomyList(Cursor listCursor){
        if (listCursor.getCount() == 0) {
            if (i == 0) {
                Toast.makeText(v.getContext(), "The DataBase is Empty :(", Toast.LENGTH_LONG).show();
                i++;
            }
        } else {
            while (listCursor.moveToNext()) {
                myList.add(new StudentInfo(
                        listCursor.getString(1),
                        listCursor.getString(2),
                        listCursor.getString(3),
                        listCursor.getString(4),
                        listCursor.getString(5),
                        listCursor.getString(6),
                        listCursor.getString(7)
                ));
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //this.sorter = parent.getItemAtPosition(position).toString();
        this.sorter = sortlist[position];
        Log.i("sorter",sorter);
            onResume();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"Inside onPause() of SearchFragment.");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"Inside onStop() of SearchFragment.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"Inside onDestroyView() of SearchFragment.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Inside onDestroy() of SearchFragment.");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"Inside onDetach() of SearchFragment.");
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"Inside onConfigurationChanged() of Search Fragment.");
    }
}