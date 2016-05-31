package tk.mayezi.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_passwd)
    EditText inputPasswd;
    @BindView(R.id.btn_singup)
    AppCompatButton btnSingup;
    @BindView(R.id.link_login)
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_singup, R.id.link_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_singup:
                signup();
                break;
            case R.id.link_login:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void signup(){
        Log.d(TAG,"Sign up");
        if(!validate()){
            onSignupFailed();
            return;
        }
        btnSingup.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(this.getString(R.string.create_account));
        progressDialog.show();

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPasswd.getText().toString();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                },3000);
    }
    public void onSignupSuccess(){
        btnSingup.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
    }
    public void onSignupFailed(){
        Toast.makeText(getBaseContext(),this.getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
        btnSingup.setEnabled(true);
    }
    public boolean validate(){
        boolean valid = true;
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPasswd.getText().toString();
        if(name.isEmpty()||name.length()<3){
            inputName.setError(this.getString(R.string.name_error));
            valid = false;
        }else{
            inputName.setError(null);
        }
        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError(this.getString(R.string.email_error));
            valid = false;
        }else{
            inputEmail.setError(null);
        }
        if(password.isEmpty()||password.length()<4||password.length()>10){
            inputEmail.setError(this.getString(R.string.passwd_error));
            valid = false;
        }else{
            inputPasswd.setError(null);
        }
        return valid;
    }
}
