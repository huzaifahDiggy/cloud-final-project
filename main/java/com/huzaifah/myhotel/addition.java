package com.huzaifah.myhotel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huzaifah.myhotel.classes.room;

import java.util.ArrayList;

public class addition extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference rooms;




    EditText beds;
    EditText price;
    EditText num;
    ArrayList<room> roomList;


    CheckBox tv;
    CheckBox ac;
    CheckBox wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addition);


        db=FirebaseDatabase.getInstance();
        roomList=new ArrayList<>();
        rooms=db.getReference().child("rooms");

        rooms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childs=dataSnapshot.getChildren();
                roomList.clear();
                Log.d("Data","Data Fetched");
                for (DataSnapshot child:childs) {

                    room temp = child.getValue(room.class);
                    roomList.add(temp);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        beds=findViewById(R.id.beds);
        price=findViewById(R.id.price);
        num=findViewById(R.id.num);
        tv=findViewById(R.id.tv);
        ac=findViewById(R.id.ac);
        wifi=findViewById(R.id.wifi);

    }

    public void add(View v){
        boolean acc;
        boolean wifii;
        boolean tvv;


        acc=ac.isChecked();
        wifii=wifi.isChecked();
        tvv=tv.isChecked();



        room r= new room(acc,wifii,tvv,num.getText().toString(),beds.getText().toString(),price.getText().toString(),"url1","url2","url3");
        r.setBooked(false);

        roomList.add(r);
        db.getReference().child("rooms").setValue(roomList);



    }



    @Override
    protected void onStop() {
        super.onStop();
        //finish();
    }
}
