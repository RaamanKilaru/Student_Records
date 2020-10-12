package com.example.studentrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentInfoView extends AppCompatActivity {

    ImageView image;
    TextView name, rollnum, gender, qualification, dateofbirth;
    private static final String TAG = "SAI";/*"StudentInfoView";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_view);
        Log.i(TAG,"Inside onCreate() of StudentInfoView.");

        name = (TextView) findViewById(R.id.display_name);
        rollnum = (TextView) findViewById(R.id.display_roll_no);
        gender = (TextView) findViewById(R.id.display_gender);
        qualification = (TextView) findViewById(R.id.display_qualification);
        dateofbirth = (TextView) findViewById(R.id.display_dob);
        image = (ImageView) findViewById(R.id.display_image);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        //Log.i("SAI",getIntent().toString());
        if(getIntent().hasExtra("image_uri") && getIntent().hasExtra("name") && getIntent().hasExtra("roll_no") && getIntent().hasExtra("gender") && getIntent().hasExtra("qualification") && getIntent().hasExtra("d_o_b")){
            String image_uri = getIntent().getStringExtra("image_uri");
            String name = getIntent().getStringExtra("name");
            String roll_no = getIntent().getStringExtra("roll_no");
            String gender = getIntent().getStringExtra("gender");
            String qualification = getIntent().getStringExtra("qualification");
            String dob = getIntent().getStringExtra("d_o_b");

            setactivity(image_uri,name,roll_no,gender,qualification,dob);
        }
    }

    private void setactivity(String image_uri, String name, String roll_no, String gender, String qualification, String dob){
        image.setImageURI(Uri.parse(image_uri));
        this.name.setText(name);
        rollnum.setText(roll_no);
        this.gender.setText(gender);
        this.qualification.setText(qualification);
        dateofbirth.setText(dob);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"Inside onStart() of StudentInfoView.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"Inside onStop() of StudentInfoView.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"Inside onDestroy() of StudentInfoView.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"Inside onPause() of StudentInfoView.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"Inside onResume() of StudentInfoView.");
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"Inside onConfigurationChanged() of StudentInfoView.");
    }
}