package com.example.studentrecords;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private View v;
    List<StudentInfo> myList;
    DatabaseHelper myDB;
    //Cursor listCursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);

        myList = new ArrayList<>();
        myDB = new DatabaseHelper(v.getContext());
        Cursor listCursor = myDB.getListContents();

        if(listCursor.getCount() == 0){
            Toast.makeText(v.getContext(),"The DataBase is Empty :(",Toast.LENGTH_LONG).show();
        } else {
            while (listCursor.moveToNext()){
                myList.add(new StudentInfo(
                        listCursor.getString(1),
                        listCursor.getString(2),
                        listCursor.getString(3),
                        listCursor.getString(4),
                        listCursor.getString(5),
                        listCursor.getString(6)
                ));
            }
        }

        RecyclerView myRv = (RecyclerView) v.findViewById(R.id.recycler_view_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(v.getContext(), myList);
        myRv.setLayoutManager(new GridLayoutManager(v.getContext(),3));
        myRv.setAdapter(myAdapter);
        return v;
    }
}