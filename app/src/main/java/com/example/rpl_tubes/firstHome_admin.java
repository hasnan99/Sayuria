package com.example.rpl_tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class firstHome_admin extends AppCompatActivity {
    DBhelper db;
    private BottomNavigationView navigationView;
    ListView listView;
    String[] daftar2;
    protected Cursor cursor;
    public static firstHome_admin bpUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_home_admin);

        db=new DBhelper(this);

        navigationView=findViewById(R.id.navigation_adminInti);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.admin_penjuals:
                        Intent intent = new Intent(firstHome_admin.this,homeAdmin_user.class);
                        startActivity(intent);
                        return true;
                    case R.id.admin_sayur:
                        Intent intentSayur = new Intent(firstHome_admin.this,homeAdmin_sayur.class);
                        startActivity(intentSayur);
                        return true;

                }
                return false;
            }
        });
        bpUser =this;
        db=new DBhelper(this);
        lihat_pembeli_admin();

    }

    public void lihat_pembeli_admin() {
        SQLiteDatabase db1= db.getReadableDatabase();
        cursor=db1.rawQuery("select * from pembeli",null);
        daftar2 =new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            daftar2[i]=cursor.getString(1).toString();
        }
        listView=(ListView)findViewById(R.id.list1);
        listView.setAdapter(new ArrayAdapter(this,R.layout.edit_llistview ,daftar2));
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection= daftar2[position];
                final CharSequence[] dialogitem={"hapus Pembeli"};
                AlertDialog.Builder builder=new AlertDialog.Builder(firstHome_admin.this);
                builder.setTitle("pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                SQLiteDatabase del=db.getReadableDatabase();
                                del.execSQL("delete from pembeli where nama ='"+selection+"'");
                                lihat_pembeli_admin();
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }

}