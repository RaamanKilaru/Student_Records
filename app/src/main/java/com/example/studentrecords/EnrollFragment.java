package com.example.studentrecords;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.os.Environment;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnrollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//@RequiresApi(api = Build.VERSION_CODES.O)
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
    public int age;
    public LocalDate bod,cod;
    private LinearLayout fragment_layout;
    public String currentPhotoPath;
    public Uri contentUri = null;
    static final int REQUEST_TAKE_PHOTO = 1;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_enroll, container, false);
        fragment_layout = (LinearLayout) v.findViewById(R.id.enroll_frag);
        verifyStoragePermissions(getActivity());
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
                dispatchTakePictureIntent();
            }
        });

        //ADD button onClick to save the information to SQLite DB.
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                selected_gender = (RadioButton) v.findViewById(radiogroup.getCheckedRadioButtonId());
                //Log.i("SAI",selected_gender.getText().toString());
                //Log.i("SAI", "Saved Path : " + contentUri.toString());
                if (
                        (name.length() == 0) ||
                        (roll_no.length() == 0) ||
                        (radiogroup.getCheckedRadioButtonId() == -1) ||
                        (qualification.equals("Select")) ||
                        (dob.length() == 0) ||
                        (contentUri == null)
                ) {
                    Toast.makeText(vi.getContext(), "Sorry, You have to fill all the Fields. ", Toast.LENGTH_LONG).show();
                } else {
                    if(
                            myDB.addData(
                            name.getText().toString(),
                            roll_no.getText().toString(),
                            selected_gender.getText().toString(),
                            qualification,
                            dob.getText().toString(),
                            contentUri.toString(),
                            age )
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        bod = LocalDate.of(year, (month+1), dayOfMonth);
        cod = LocalDate.now();
        age = Period.between(bod,cod).getYears();
        dob.setText(date);
    }



    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        contentUri = Uri.fromFile(f);
        Log.i("SAI","galleryAddPic() : " + contentUri.toString());
        mediaScanIntent.setData(contentUri);
        v.getContext().sendBroadcast(mediaScanIntent);
        Log.i("SAI","Done with galleryAddPic().");
        //imageview.setImageURI(contentUri);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageview.getWidth();
        int targetH = imageview.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageview.setImageBitmap(bitmap);
    }

    //Method that creates the Intent to capture image and initiates the method startActivityForResult().
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create the File where the photo should be saved.
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.i("SAI","Inside dispatchPictureIntent() call and got an IOException");
            Toast.makeText(v.getContext(), "Error Creating Image File :(", Toast.LENGTH_SHORT).show();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Log.i("SAI","photoFile.toString() : " + photoFile.toString());
            Uri photoURI = FileProvider.getUriForFile(
                    v.getContext(),
                    "com.example.studentrecords.fileprovider",
                    photoFile);
            Log.i("SAI", "photoURI : " + photoURI.toString());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    //To Display the captured image in the ImageView.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Log.i("SAI", "Inside onActivityResult() : " + requestCode + ", " + resultCode + ", " + RESULT_OK);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            galleryAddPic();      /* Saving the picture to storage is done in onClick of Add button. */
            setPic();  // Captured pictured is set to the image view with this method.
        }
    }

    //Creates a file in the internal storage to save the picture that is going to be captured.
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //Creates a new file at storageDir with <imageFileName>.jpg as name of the file.
        File image = new File(storageDir, imageFileName + ".jpg");
        /*Log.i("SAI", "storageDir : " + storageDir.toString());
        Log.i("SAI","imageFileName : " + imageFileName);
        Log.i("SAI","image.toString() : " + image.toString());*/
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    //To parse the selected dropdown item into a String to insert into SQLite DB.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.qualification = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Storage Access Permissions.
    /*Even though we have given permissions in the manifest file we will have to give permission to
     *write int the mobile storage during run-time. The below method makes sure if we have the
     *required permissions to write. If we dont have them we will be promted to provide the permissions.
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}