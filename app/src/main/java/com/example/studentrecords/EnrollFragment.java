package com.example.studentrecords;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnrollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnrollFragment extends Fragment implements DatePickerDialog.OnDateSetListener, OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnrollFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnrollFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnrollFragment newInstance(String param1, String param2) {
        EnrollFragment fragment = new EnrollFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText roll_no, name, dob;
    private ImageButton calendar_btn;
    private Button addbutton;
    private RadioGroup radiogroup;
    private RadioButton selected_gender;
    private String gender,qualification;
    private ImageView imageview;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private Broadcast_receiver b_reciever;
    private DatabaseHelper myDB;
    private View v;
    private LinearLayout fragment_layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getView().getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_enroll, container, false);
        fragment_layout = (LinearLayout) v.findViewById(R.id.enroll_frag);
        name = (EditText) v.findViewById(R.id.name);
        imageview = (ImageView) v.findViewById(R.id.image_view);
        roll_no = (EditText) v.findViewById(R.id.roll_no);
        radiogroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        myDB = new DatabaseHelper(v.getContext());


        //Spinner for Qualifications.
        spinner = (Spinner) v.findViewById(R.id.qualifications);
        adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.qualifications,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        dob = (EditText) v.findViewById(R.id.d_o_b);
        imageview = (ImageView) v.findViewById(R.id.image_view);
        calendar_btn = (ImageButton) v.findViewById(R.id.calendar_btn);
        addbutton = v.findViewById(R.id.add);

        //setOnClickListener for DatePickerDialog.
        dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });
        calendar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

        //setOnClickListener for Image capture and view it in ImageView.
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1,0);
            }
        });

        //ADD button onClick to save the information to SQLite DB.
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                selected_gender = (RadioButton) v.findViewById(radiogroup.getCheckedRadioButtonId());
                if (
                        (name.length() == 0) ||
                        (roll_no.length() == 0) ||
                        (radiogroup.getCheckedRadioButtonId() == -1) ||
                        (qualification.equals("Select")) ||
                        (dob.length() == 0)
                ) {
                    Toast.makeText(vi.getContext(), "Sorry, You have to fill all the Fields. ", Toast.LENGTH_LONG).show();
                } else {
                    if(
                            myDB.addData(
                            name.getText().toString(),
                            roll_no.getText().toString(),
                            gender, qualification,
                            dob.getText().toString() )
                    ) {
                        name.setText("");
                        roll_no.setText("");
                        spinner.setSelection(0);
                        dob.setText("");
                        imageview.setImageBitmap(null);
                        imageview.setBackgroundResource(R.drawable.ic_launcher_foreground);
                        Toast.makeText(vi.getContext(), "Successful!!", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(vi.getContext(), "Sorry Something went wrong :(", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //To change TabLayout background for every Intent.ACTION_SCREEN_ON.
        b_reciever = new Broadcast_receiver(fragment_layout);
        v.getContext().registerReceiver(b_reciever, new IntentFilter(Intent.ACTION_SCREEN_ON));
        v.getContext().registerReceiver(b_reciever, new IntentFilter(Intent.ACTION_SCREEN_OFF));


        //Return the view.
        return v;
    }

    //To parse the date into a String to set it on the EditText.
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        dob.setText(date);
    }

    //To Display the captured image in the ImageView.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageview.setImageBitmap(bitmap);
    }

    //To parse the selected dropdown item into a String to insert into SQLite DB.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.qualification = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}