package com.example.airtelapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThirdFragment extends androidx.fragment.app.Fragment {
    TextView tv;
    Button bt_reset;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) getView().findViewById(R.id.report);
        bt_reset= (Button) getView().findViewById(R.id.bt_resetData);
        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataReset();
            }
        });
        loadData();


    }

    public void loadData() {

        db.collection("deliveryData").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String data="";
                        int delivery_COUNTER=0;
                        int delivery_done=0;
                        int pickup_counter=0;
                        int pickup_done=0;
                        int cancelled=0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               if (document.getData().get("type").toString().equalsIgnoreCase("D")){
                                   delivery_COUNTER++;
                                   if (document.getData().get("status").toString().equalsIgnoreCase("D"))
                                       delivery_done++;
                               }

                                if (document.getData().get("type").toString().equalsIgnoreCase("P")){
                                    pickup_counter++;
                                    if (document.getData().get("status").toString().equalsIgnoreCase("P"))
                                        pickup_done++;
                                }

                                if (document.getData().get("status").toString().equalsIgnoreCase("F")) {
                                   cancelled++;
                               }
                               else{

                               }


                            }
                            data="Total delivered items completed today : "+String.valueOf(delivery_done)+"\n" + "Remaining delivery items : " +String.valueOf(delivery_COUNTER -delivery_done) + "\n\n";
                            data+="Total pickups completed today : "+String.valueOf(pickup_done)+"\n" + "Remaining Pick-up items : " +String.valueOf(pickup_counter - pickup_done) + "\n\n";
                            data+="Delivery Failed : "+String.valueOf(cancelled)+"\n" ;
                            tv.setText(data);
                        } else {
                            Log.e("DEVANSHU", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void DataReset(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("Name", "Devanshu Litoria \n Phone Number : +91-9706783069");
        docData.put("comment","");
        docData.put("status", "X");
        docData.put("time", new Timestamp(new Date(System.currentTimeMillis()+5*60*1000)));
        docData.put("type", "D");
        addData(docData);

        Map<String, Object> docData1 = new HashMap<>();
        docData1.put("Name", "Shamshad \n Phone Number : +91-9706783069");
        docData1.put("comment","");
        docData1.put("status", "X");
        docData1.put("time", new Timestamp(new Date(System.currentTimeMillis()+10*60*1000)));
        docData1.put("type", "D");
        addData(docData1);

        Map<String, Object> docData2 = new HashMap<>();
        docData2.put("Name", "Babua urf Pendicular \n Phone Number : +91-9706783069");
        docData2.put("comment","");
        docData2.put("status", "X");
        docData2.put("time", new Timestamp(new Date(System.currentTimeMillis()+15*60*1000)));
        docData2.put("type", "D");
        addData(docData2);

        Map<String, Object> docData3 = new HashMap<>();
        docData3.put("Name", "Faizal \n Phone Number : +91-9706783069");
        docData3.put("comment","");
        docData3.put("status", "X");
        docData3.put("time", new Timestamp(new Date(System.currentTimeMillis()+20*60*1000)));
        docData3.put("type", "D");
        addData(docData3);

        Map<String, Object> docData4 = new HashMap<>();
        docData4.put("Name", "Ramadhir Singh \n Phone Number : +91-9706783069");
        docData4.put("comment","");
        docData4.put("status", "X");
        docData4.put("time", new Timestamp(new Date(System.currentTimeMillis()+30*60*1000)));
        docData4.put("type", "D");
        addData(docData4);

        Map<String, Object> docData5 = new HashMap<>();
        docData5.put("Name", "Definite \n Phone Number : +91-9706783069");
        docData5.put("comment","");
        docData5.put("status", "X");
        docData5.put("time", new Timestamp(new Date(System.currentTimeMillis()+35*60*1000)));
        docData5.put("type", "D");
        addData(docData5);

        Map<String, Object> docData6 = new HashMap<>();
        docData6.put("Name", "Guddu \n Phone Number : +91-9706783069");
        docData6.put("comment","");
        docData6.put("status", "X");
        docData6.put("time", new Timestamp(new Date(System.currentTimeMillis()+60*60*1000)));
        docData6.put("type", "D");
        addData(docData6);

        Map<String, Object> docData7 = new HashMap<>();
        docData7.put("Name", "Danish \n Phone Number : +91-9706783069");
        docData7.put("comment","");
        docData7.put("status", "X");
        docData7.put("time", new Timestamp(new Date(System.currentTimeMillis()+80*60*1000)));
        docData7.put("type", "D");
        addData(docData7);

        Map<String, Object> docData8 = new HashMap<>();
        docData8.put("Name", "Kabir Singh \n Phone Number : +91-9706783069");
        docData8.put("comment","");
        docData8.put("status", "X");
        docData8.put("time", new Timestamp(new Date(System.currentTimeMillis()+5*60*1000)));
        docData8.put("type", "P");
        addData(docData8);

        Map<String, Object> docData10 = new HashMap<>();
        docData10.put("Name", "Preeti Sikha \n Phone Number : +91-9706783069");
        docData10.put("comment","");
        docData10.put("status", "X");
        docData10.put("time", new Timestamp(new Date(System.currentTimeMillis()+10*60*1000)));
        docData10.put("type", "P");
        addData(docData10);
        Toast toast = Toast.makeText(getContext(), "Data added success", Toast.LENGTH_SHORT);
        toast.show();

    }
    public void addData(Map data){
        db.collection("deliveryData")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("devanshu","Data added success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("devanshu","fAILED"+e.toString());
                    }
                });
    }
}
