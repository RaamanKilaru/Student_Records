package com.example.studentrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentInfoView extends AppCompatActivity {

    ImageView image;
    TextView name, rollnum, gender, qualification, dateofbirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_view);
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
}