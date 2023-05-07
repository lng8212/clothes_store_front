package com.example.e_commerce.screens.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityLoginBinding;
import com.example.e_commerce.network.model.request.LoginRequest;
import com.example.e_commerce.network.model.response.LoginResponse;
import com.example.e_commerce.network.service.AuthService;
import com.example.e_commerce.utils.ProgressDialog;
import com.example.e_commerce.utils.UserManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    static final int REQUEST_CODE_SIGN_IN_GOOGLE = 111;
    @Inject
    AuthService authService;
    @Inject
    UserManager userManager;
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        progressDialog = new ProgressDialog(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
        if (!userManager.getAccessToken().equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        binding.signInButton.setOnClickListener(v -> {
            progressDialog.startDialog();
            String email = binding.usernameInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            authService.login(new LoginRequest(email, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                userManager.saveAccessToken(response.body().getTokens().getAccess());
                                userManager.saveRefreshToken(response.body().getTokens().getRefresh());
                                Log.e("Access token ", userManager.getAccessToken());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                progressDialog.dismissDialog();
                            } else {
                                progressDialog.dismissDialog();
                                Toast.makeText(LoginActivity.this, "Network error or invalid information", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            progressDialog.dismissDialog();
                        }
                    });

        });

        binding.signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnLoginGoogle.setOnClickListener(v -> {
            processLoginGoogle();
        });
    }

    private void processLoginGoogle() {
        progressDialog.startDialog();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_id_token))
                .requestEmail()
                .requestProfile()
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, options);
        startActivityForResult(signInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN_GOOGLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN_GOOGLE) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
                googleAuthForFirebase(account);

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismissDialog();
            }

        }
    }

    private void googleAuthForFirebase(GoogleSignInAccount account) {
        AuthCredential credentials = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        try {
            auth.signInWithCredential(credentials).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    JsonObject body = new JsonObject();
                    body.addProperty("token", authResult.getUser().getIdToken(false).getResult().getToken());
                    authService.loginWithGoogle(body).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                userManager.saveAccessToken(response.body().getTokens().getAccess());
                                userManager.saveRefreshToken(response.body().getTokens().getRefresh());
                                Log.e("Access token ", userManager.getAccessToken());
                                progressDialog.dismissDialog();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                auth.signOut();
                                progressDialog.dismissDialog();
                                Toast.makeText(LoginActivity.this, "An error has occurred!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            auth.signOut();
                            progressDialog.dismissDialog();
                        }
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "An error has occurred!", Toast.LENGTH_LONG).show();
        }
    }

}