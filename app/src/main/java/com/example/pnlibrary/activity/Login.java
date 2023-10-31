package com.example.pnlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pnlibrary.DAO.UserDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.model.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText edtUserName,edtPassWord;
    AppCompatButton btnLogin;
    UserDAO userDAO;
    User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);


        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        CheckBox chkRemember = findViewById(R.id.chkRemember);
        userDAO = new UserDAO(Login.this);
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isRemember",false);
        if(check){
//            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            String user = sharedPreferences.getString("user","");
            String pass = sharedPreferences.getString("pass","");
            chkRemember.setChecked(check);
            edtUserName.setText(user);
            edtPassWord.setText(pass);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUser = edtUserName.getText().toString();
                String inputPass = edtPassWord.getText().toString();
                if(inputUser.length()>0 && inputPass.length()>0){
                    if(checkUser(inputUser,inputPass,userDAO.getListUser())){
                        boolean isRemember = chkRemember.isChecked();
                        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isRemember",isRemember);
                        editor.putString("user",inputUser);
                        editor.putString("pass",inputPass);
                        editor.apply();
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userLogin",userLogin);
                        intent.putExtras(bundle);
                        finish();
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "username và password không bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean checkUser(String user, String pass, ArrayList<User> list){
        for(User u : list){
            if(user.equals(u.getUserName()) && pass.equals(u.getPassWord())){
                userLogin = u;
                return true;
            }
        }
        return false;
    }
}