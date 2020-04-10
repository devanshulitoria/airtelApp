package com.example.airtelapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FirstFragment extends Fragment {

    TextView tv;
    ListView lv;
    EditText result;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public CollectionReference collectionReference = db.collection("deliveryData");
    public DocumentReference documentReference = db.document("deliveryData/airtelData");
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) getView().findViewById(R.id.deliver);
        lv = (ListView) getView().findViewById(R.id.listview);

        loadData();


    }
    public void loadData() {

        db.collection("deliveryData")
                .whereEqualTo("type", "D").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> listID=new LinkedList<String>();
                        List<String> listData=new LinkedList<String>();
                        List<String> timedata=new LinkedList<String>();
                        List<String> listStatus=new LinkedList<String>();
                        String data="";

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listData.add(document.getData().get("Name").toString());
                                timedata.add(document.getData().get("time").toString());
                                listID.add(document.getId());
                                listStatus.add(document.getData().get("status").toString());

                            }

                            MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(),listData,timedata,listID,listStatus);
                            //tv.setText(data);
                            lv.setAdapter(adapter);
                        } else {
                            Log.e("DEVANSHU", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}

class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List msg;
    private final List time;
    private final List id;
    private final List listStatus;
    String time_str;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MySimpleArrayAdapter(Context context, List msg,List time,List id,List listStatus) {
        super(context, -1, msg);
        this.context = context;
        this.msg = msg;
        this.time=time;
        this.id=id;
        this.listStatus=listStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView message = (TextView) rowView.findViewById(R.id.deliver);
        message.setText("Deliver to : "+msg.get(position).toString());

        TextView timestr = (TextView) rowView.findViewById(R.id.time);
        final String time_str=time.get(position).toString();
        String temp_time=time_str.substring(0,time_str.length()-18);
        timestr.setText("Expected Time : "+temp_time);

        final Button buttonDelete = (Button) rowView.findViewById(R.id.cancelButton);
        final Button uanbleToDeliverButton = (Button) rowView.findViewById(R.id.uanbleToDeliverButton);
        if (!deliver_algo(time_str))  {
            buttonDelete.setEnabled(false);
            buttonDelete.setText("Deliver");
        }
        else if(listStatus.get(position).toString().equalsIgnoreCase("F")  ){
            uanbleToDeliverButton.setBackgroundColor(Color.RED);
            uanbleToDeliverButton.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
        else if(listStatus.get(position).toString().equalsIgnoreCase("D")  ){
            buttonDelete.setBackgroundColor(Color.parseColor("#32a852"));
            buttonDelete.setEnabled(false);
            buttonDelete.setText("Delivered");
            uanbleToDeliverButton.setEnabled(false);
        }
        else {
            Log.e("list button", "inside else");
            buttonDelete.setText("Deliver");
        }

        final String ide = id.get(position).toString();
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("devanshu",ide);
                buttonDelete.setBackgroundColor(Color.parseColor("#32a852"));
                updateDocument(ide);
                uanbleToDeliverButton.setEnabled(false);
            }
        });
        uanbleToDeliverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uanbleToDeliverButton.setBackgroundColor(Color.RED);
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        updateFailedState(ide);
                                        buttonDelete.setEnabled(false);

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        return rowView;
    }
    public boolean deliver_algo(String str)  {
        String[] temp=str.split(" ");

        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            Date date1 = format.parse(temp[3]);


        Date currentTime = Calendar.getInstance().getTime();
        String [] temp1=String.valueOf(currentTime).split(" ");
        Date date2 = format.parse(temp1[3]);

        long mills = date1.getTime() - date2.getTime();
        int mins = (int) (mills / (1000*60));
            Log.e("timer",temp1[3]+"-"+temp[3]+"="+String.valueOf(mins));
        if (mins<=15 && mins>=-15)
            return true;
        else
            return false;
        }catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateDocument(String id) {
        // [START update_document]
        DocumentReference washingtonRef = db.collection("deliveryData").document(id);

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("status", "D")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("update","successfull");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("update","failed -> "+e.toString());
                    }
                });
        // [END update_document]
    }

    public void updateFailedState(String id) {
        // [START update_document]
        DocumentReference washingtonRef = db.collection("deliveryData").document(id);

        // Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("status", "F")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("update","successfull");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("update","failed -> "+e.toString());
                    }
                });
        // [END update_document]
    }

}
