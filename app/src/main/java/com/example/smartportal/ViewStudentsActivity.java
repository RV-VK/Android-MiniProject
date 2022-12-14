package com.example.smartportal;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {

    ImageButton refresh;
    ListView listView;
    Animation animation;
    List<String> studentIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        studentIds.add("User1");
        getSupportActionBar().hide();

        listView = findViewById(R.id.listViewStudents);
        refresh = findViewById(R.id.imageButton);

        refresh.setOnClickListener(v -> FirebaseFirestore.getInstance().collection("StudentBio").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                studentIds.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    studentIds.add(document.getId());
                }
                Log.d("TAG", studentIds.toString());
                StudentViewAdapter adapter = new StudentViewAdapter(ViewStudentsActivity.this, studentIds);
                animation = AnimationUtils.loadAnimation(this, R.anim.animation1);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((adapterView, view, i, l) -> { Intent intt=new Intent(getApplicationContext(),StudentView.class);
                intt.putExtra("usrname",studentIds.get(i));
                startActivity(intt);}
                );

//                Toast.makeText(this, studentIds.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        }));
    }
}