package com.example.pnlibrary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pnlibrary.DAO.CallCardDAO;
import com.example.pnlibrary.DAO.UserDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.fragment.AddLibrarianFragment;
import com.example.pnlibrary.fragment.BookFragment;
import com.example.pnlibrary.fragment.ChangePassWordFragment;
import com.example.pnlibrary.fragment.ClassifyBookFragment;
import com.example.pnlibrary.fragment.CustomerFragment;
import com.example.pnlibrary.fragment.FragmentListCallCard;
import com.example.pnlibrary.fragment.StatisticalFragment;
import com.example.pnlibrary.fragment.ThongKeDoanhThuFragment;
import com.example.pnlibrary.fragment.ThongKeTop10Fragment;
import com.example.pnlibrary.model.User;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    DrawerLayout drawerLayout;
    RelativeLayout rltView;
    NavigationView navigationView;
    User userCurrent;
    FragmentManager fragmentManager;
    Fragment fragment;
    Calendar calendar;
    CallCardDAO callCardDAO;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        rltView = findViewById(R.id.rltView);
        navigationView = findViewById(R.id.drawView);
        drawerLayout = findViewById(R.id.drawerlayout);
        callCardDAO = new CallCardDAO(MainActivity.this);
        userDAO = new UserDAO(MainActivity.this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userCurrent = (User)bundle.getSerializable("userLogin");

        if(userCurrent.getClassify()==1){
            navigationView.inflateMenu(R.menu.menu_1);
        }else{
            navigationView.inflateMenu(R.menu.menu_2);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("xin chào " + userCurrent.getUserName());
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        fragmentManager = getSupportFragmentManager();
        fragment = new FragmentListCallCard(userCurrent);
        fragmentManager.beginTransaction().replace(R.id.rltView,fragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.listCallCard:
                        fragment = new FragmentListCallCard(userCurrent);
                        break;
                    case R.id.Customer:
                        fragment = new CustomerFragment();
                        break;
                    case R.id.ClassifyBook:
                        fragment = new ClassifyBookFragment();
                        break;
                    case R.id.Book:
                        fragment = new BookFragment();
                        break;
                    case R.id.menuTop10:
                        fragment = new ThongKeTop10Fragment();
                        break;
                    case R.id.menuDoanhThu:
                        fragment = new ThongKeDoanhThuFragment();
                        break;
                    case R.id.addLibrarian:
                        addLibrarian();
                        break;
                    case R.id.changePassWord:
                        showDialogChangePassWord();
                        break;
                    case R.id.signOut:
                        userCurrent = null;
                        Intent intent1 = new Intent(MainActivity.this, Login.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent1);
                }
                item.setCheckable(true);
                fragmentManager.beginTransaction().replace(R.id.rltView,fragment).commit();
                toolbar.setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){


            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialogChangePassWord(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setNegativeButton("cập nhật",null).setPositiveButton("hủy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password,null);
        EditText edtOldPass = view.findViewById(R.id.edtOldPassword);
        EditText edtNewPass = view.findViewById(R.id.edtNewPassword);
        EditText edtRePass = view.findViewById(R.id.edtRePassword);

        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String olpPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String rePass = edtRePass.getText().toString();

                if(olpPass.length()==0 || newPass.length()==0 || rePass.length()==0){
                    Toast.makeText(MainActivity.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if((newPass.equals(rePass))){
                        UserDAO userDAO = new UserDAO(MainActivity.this);
                        int check = userDAO.changePassword(userCurrent.getUserName(),olpPass,newPass);
                        if(check == 1){
                            Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(MainActivity.this, Login.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent1);
                        }else if(check == 0){
                            Toast.makeText(MainActivity.this, "mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "nhập mật khẩu mới không trùng", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        MenuInflater inf = getMenuInflater();
//        inf.inflate(R.menu.long_click_item,menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//        int index = info.position;
//        if(item.getItemId()==R.id.meuEdit){
//            Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
//        }
//        if(item.getItemId()==R.id.menuDelete){
//            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
//        }
//        return super.onContextItemSelected(item);
//    }

    public void addLibrarian(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setNegativeButton("Thêm",null).setPositiveButton("hủy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_librarian,null);
        EditText edtUserName = view.findViewById(R.id.edtUserName);
        EditText edtPassWord = view.findViewById(R.id.edtPassWord);
        RadioButton rdoAdmin = view.findViewById(R.id.rdoAdmin);
        RadioButton rdoLibrarian = view.findViewById(R.id.rdoLibrarian);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUserName.getText().toString();
                String passWord = edtPassWord.getText().toString();
                int classify = 1;


                if(userName.length()==0 || passWord.length()==0){
                    Toast.makeText(MainActivity.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    for(User u : userDAO.getListUser()){
                        if(userName.equals(u.getUserName())){
                            Toast.makeText(MainActivity.this, "tài khoản này đã tồn tại", Toast.LENGTH_SHORT).show();
                        }else{
                            if(rdoAdmin.isSelected()){
                                classify = 1;
                            }else{
                                classify = 0;
                            }
                            boolean check = userDAO.addUser(userName,passWord,classify);
                            if(check){
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });
    }
}