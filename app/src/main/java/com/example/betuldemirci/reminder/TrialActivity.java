package com.example.betuldemirci.reminder;

/**
 * Created by Betul Demirci on 3/4/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrialActivity extends AppCompatActivity {

    Button SubmitButton ;

    Button DisplayButton;

    EditText NameEditText, PhoneNumberEditText;



    // Declaring String variables to store name & phone number get from EditText.
    String NameHolder, NumberHolder;

    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://insertdata-android-examples.firebaseio.com/";

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Task_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        Firebase.setAndroidContext(TrialActivity.this);

        firebase = new Firebase(Firebase_Server_URL);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        SubmitButton = findViewById(R.id.submit);

        NameEditText = findViewById(R.id.name);

        PhoneNumberEditText = findViewById(R.id.phone_number);

        DisplayButton = findViewById(R.id.DisplayButton);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TaskDetails studentDetails = new TaskDetails();

                GetDataFromEditText();

                // Adding name into class function object.
                studentDetails.setStudentName(NameHolder);

                // Adding phone number into class function object.
                studentDetails.setStudentPhoneNumber(NumberHolder);

                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(studentDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(TrialActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

            }
        });

        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TrialActivity.this, ShowTaskActivity.class);
                startActivity(intent);

            }
        });

    }

    public void GetDataFromEditText(){

        NameHolder = NameEditText.getText().toString().trim();

        NumberHolder = PhoneNumberEditText.getText().toString().trim();

    }
}