package com.example.airtelapp;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final Button b1 = (Button)findViewById(R.id.deliver);
        final Button b2 = (Button)findViewById(R.id.pickup);
        final Button b3 = (Button)findViewById(R.id.generateReport);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Getting Deliver data from Firestore", Toast.LENGTH_SHORT);
                toast.show();

                callFragment(1);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Getting Pickup data from Firestore", Toast.LENGTH_SHORT);
                toast.show();

                callFragment(2);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Getting Report data from Firestore", Toast.LENGTH_SHORT);
                toast.show();

                callFragment(3);
            }
        });



    }


    public void callFragment(int num){
        Fragment fr;


        if (num==1){
            fr=new FirstFragment();
            Log.e("devanshu","inside first fragment");
        }
        else if (num==2){
            fr=new SecondFragment();
            Log.e("devanshu","inside second fragment");
        }
        else{
            fr=new ThirdFragment();
            Log.e("devanshu","inside third fragment");
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        tx.replace(R.id.nav_host_fragment,fr);
        tx.commit();
    }

}
