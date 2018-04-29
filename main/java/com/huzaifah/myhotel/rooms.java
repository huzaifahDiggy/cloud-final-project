package com.huzaifah.myhotel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huzaifah.myhotel.classes.room;

import java.util.ArrayList;

public class rooms extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference rooms;

    ListView list;
    ArrayList<String> roomName;
    ArrayList<String> roomNum;
    ArrayList<room> roomList;
    ArrayAdapter adapter;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listroom);


        roomName=new ArrayList<>();
        roomNum=new ArrayList<>();
        roomList=new ArrayList<>();

        db=FirebaseDatabase.getInstance();
        rooms=db.getReference().child("rooms");

        list=findViewById(R.id.list);
        pd= new ProgressDialog(this);
        pd.setMessage("Fetching Data");
        pd.setCancelable(false);
        pd.show();


        rooms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childs=dataSnapshot.getChildren();
                roomList.clear();
                roomName.clear();
                Log.d("Data","Data Fetched");
                for (DataSnapshot child:childs) {

                    room temp = child.getValue(room.class);
                    roomList.add(temp);
                    if(temp.isBooked()==false) {
                        roomName.add("Room # " + temp.getNum());
                        roomNum.add(temp.getNum());
                    }
                }
                pd.cancel();
                updateRoomList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Error Fetching Data",Toast.LENGTH_LONG).show();
                pd.cancel();
            }
        });



        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,roomName);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
                ShowDialog(position);
            }
        });

        //33700293
    }

    private void updateRoomList() {
        adapter.notifyDataSetChanged();
    }

    public void ShowDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Mark this room as Booked ?");
        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                String num=roomNum.get(pos);
                Log.d("listSize",Integer.toString(roomList.size()));
                for(int i=0;i<roomList.size();i++){
                    if(roomList.get(i).getNum().equals(num)) {
                        roomList.get(i).setBooked(true);
                        Log.d("listRoom",roomList.get(i).getNum());
                        //firebaseDatabase.getReference().child("rooms").child(Integer.toString(i)).child("isBooked").setValue(false);
                        break;
                    }
                }
                db.getReference().child("rooms").setValue(roomList);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
