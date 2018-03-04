package com.example.betuldemirci.reminder;

import android.os.Bundle;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

    //ui textview
    TextView responseText;
    private Button button;

    private FloatingActionButton DisplayButton;


    // Declaring String variable ( In which we are storing firebase server URL ).
    public static final String Firebase_Server_URL = "https://insertdata-android-examples.firebaseio.com/";

    // Declaring String variables to store name & phone number get from EditText.
    String TaskHolder, TimeHolder;

    Firebase firebase;

    DatabaseReference databaseReference;

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "Task_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText =  findViewById(R.id.response_text);

        responseText.setText("Merhaba");

        button =  this.findViewById(R.id.button);
        DisplayButton =  this.findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                try{
                    startActivityForResult(intent,200);
                }catch (ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Intent problem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Firebase.setAndroidContext(MainActivity.this);

        firebase = new Firebase(Firebase_Server_URL);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ShowTaskActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                responseText.setText(result.get(0));


                String arr[] = result.get(0).trim().split(" ");

                for(int i = 0; i<arr.length; i++){

                    if(i == arr.length-1){
                        TimeHolder = arr[i];
                    }else{
                        TaskHolder += arr[i].trim()+" ";
                    }
                }

                TaskDetails taskDetails = new TaskDetails();
                // Adding name into class function object.
                taskDetails.setStudentName(TaskHolder);

                // Adding phone number into class function object.
                taskDetails.setStudentPhoneNumber(TimeHolder);

                // Getting the ID from firebase database.
                String StudentRecordIDFromServer = databaseReference.push().getKey();

                // Adding the both name and number values using student details class object using ID.
                databaseReference.child(StudentRecordIDFromServer).setValue(taskDetails);

                // Showing Toast message after successfully data submit.
                Toast.makeText(MainActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void GetDataFromEditText() {

        TaskHolder = "dene";

        TimeHolder = "15";

    }

}