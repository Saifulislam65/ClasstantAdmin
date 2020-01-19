package com.example.classtantadmin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText sid, did, secret;
    private TextView suid;
    private Button load, register;
    private DatabaseReference getSUID, setInDevice;
    private String emailUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sid = findViewById(R.id.sid);
        did = findViewById(R.id.did);
        secret = findViewById(R.id.device_secret);
        suid = findViewById(R.id.load_suid_text);
        load = findViewById(R.id.load_suid);
        register = findViewById(R.id.register);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sid.getText().length()==9){
                    getSUID= FirebaseDatabase.getInstance().getReference("Student/1_Student_Email_UID/"+sid.getText().toString());
                    getSUID.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{
                                emailUID = dataSnapshot.getValue(String.class);
                                suid.setText(emailUID);
                                System.out.println("Inside getSUID: "+ emailUID +"/"+ emailUID.length());
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Error loading student email uid", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Write valid student ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suid.getText().length()>0){
                    System.out.println("device secret: "+secret.getText().toString().trim());
                    if(secret.getText().toString().trim().equals("device1") || secret.getText().toString().trim().equals("device2")){
                        if(did.getText().length()>0){
                            setInDevice = FirebaseDatabase.getInstance().getReference("Device/"+secret.getText().toString().trim()+"/StudentID/"+did.getText().toString());
                            setInDevice.setValue(suid.getText().toString());
                        }else {
                            Toast.makeText(getApplicationContext(), "Write valid device ID", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Write valid device Secret", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Click Load before register", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
