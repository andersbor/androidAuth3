package dk.easj.anbo.firebaseauth3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";
    private FirebaseAuth mAuth;
    private Button loginButton, logoutButton, registerButton;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.mainLoginButton);
        registerButton = findViewById(R.id.mainRegisterButton);
        logoutButton = findViewById(R.id.mainLogoutButton);
        messageView = findViewById(R.id.mainMessageTextView);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        messageView.setText((currentUser == null) ? "No user" : "Welcome " + currentUser.getEmail());
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        if (mAuth.getCurrentUser() == null) {
            logoutButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        } else {
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
        }
    }

    public void login(View view) {
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if ("".equals(email)) {
            emailView.setError("No email");
            return;
        }
        if ("".equals(password)) {
            passwordView.setError("No password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText("Welcome " + user.getEmail());
                            updateButtonVisibility();
                            // Go to another activity using an Intent
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            messageView.setText(task.getException().getMessage());
                        }
                    }
                });
    }

    public void register(View view) {
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if ("".equals(email)) {
            emailView.setError("No email");
            return;
        }
        if ("".equals(password)) {
            passwordView.setError("No password");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText("Welcome " + user.getEmail());
                            updateButtonVisibility();
                        } else {
                            messageView.setText(task.getException().getMessage());
                        }
                    }
                });
    }

    public void logout(View view) {
        mAuth.signOut();
        updateButtonVisibility();
    }
}