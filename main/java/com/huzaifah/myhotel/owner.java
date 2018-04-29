package com.huzaifah.myhotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class owner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpanel);
    }
    public void AddRoom(View v){
        startActivity(new Intent(this,addition.class));
    }
    public void freeRooms(View v){
        startActivity(new Intent(this,rooms.class));
    }
    public void logout(View v){

        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.clear().commit();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login.class));
    }
    public void DeleteUsers(View v){
        startActivity(new Intent(this,UsersALl.class));
    }
    public void bookedRoom(View v){
        startActivity(new Intent(this ,nonFreerooms.class));
    }


}
