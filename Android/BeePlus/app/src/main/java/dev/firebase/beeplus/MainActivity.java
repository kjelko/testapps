package dev.firebase.beeplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutAndUpdateUI();
            }
        });

        TextView user = findViewById(R.id.signed_in_user);
        user.setText("Signed in as " + currentUser.getDisplayName() + ", " + currentUser.getEmail());
    }

    private void signOutAndUpdateUI() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, SignInActivity.class);

        startActivity(intent);
        finish();
    }
}
