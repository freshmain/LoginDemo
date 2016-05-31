package tk.mayezi.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    public static final int REQUEST_SINGUP = 0;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_passwd)
    EditText inputPasswd;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SINGUP){
            if (resultCode == RESULT_OK){
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @OnClick({R.id.input_email, R.id.input_passwd, R.id.btn_login, R.id.link_signup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_email:
                break;
            case R.id.input_passwd:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivityForResult(intent,REQUEST_SINGUP);
                break;
        }
    }
    public void login(){
        Log.d(TAG,"Login");
        if(!validate()){
            onLoginFailed();
            return;
        }
        btnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(this.getString(R.string.loading_info));
        progressDialog.show();

        String email = inputEmail.getText().toString();
        String password = inputPasswd.getText().toString();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                },3000);
    }
    public void onLoginSuccess(){
        btnLogin.setEnabled(true);
        finish();
    }
    public void onLoginFailed(){
        Toast.makeText(getBaseContext(),this.getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }
    public boolean validate(){
        boolean valid = true;
        String email = inputEmail.getText().toString();
        String passwd = inputPasswd.getText().toString();
        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError(this.getString(R.string.email_error));
            valid = false;
        }else{
            inputEmail.setError(null);
        }
        if(passwd.isEmpty()||passwd.length()<4||passwd.length()>10){
            inputPasswd.setError(this.getString(R.string.passwd_error));
            valid = false;
        }else{
            inputPasswd.setError(null);
        }
        return valid;
    }
}

