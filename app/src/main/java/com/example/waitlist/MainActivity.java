package com.example.waitlist;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private FloatingActionButton btnadd;
private RecyclerView lstvguest;
private ImageView imgempty;

Database database;
ListAdapter listAdapter;

SQLiteDatabase sqLiteDatabase;
Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd=(FloatingActionButton)findViewById(R.id.add_user);
        lstvguest=(RecyclerView)findViewById(R.id.waitlist);
        imgempty=(ImageView)findViewById(R.id.list_img);


        lstvguest.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lstvguest.setHasFixedSize(true);
        lstvguest.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        database=new Database(getApplicationContext());
        sqLiteDatabase=database.getWritableDatabase();
        cursor=database.getallguests(sqLiteDatabase);
        listAdapter=new ListAdapter(getApplicationContext(),cursor);
        lstvguest.setAdapter(listAdapter);

        if (cursor.getCount()!=0){
            imgempty.setVisibility(View.GONE);
        }
        else {
            imgempty.setVisibility(View.VISIBLE);
        }
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcustomdialog();
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int id=(int)viewHolder.itemView.getTag();
                database.delete(sqLiteDatabase,id);
                listAdapter.swapcursur(database.getallguests(sqLiteDatabase));
                if (database.getallguests(sqLiteDatabase).getCount()==0){
                    imgempty.setVisibility(View.VISIBLE);
                }
            }
        }).attachToRecyclerView(lstvguest);
    }

    public void showcustomdialog(){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addguest);
       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        final EditText txtgname=(EditText)dialog.findViewById(R.id.txtgname);
        final EditText txtgnum=(EditText)dialog.findViewById(R.id.txtgnum);
        Button btnback=(Button)dialog.findViewById(R.id.btnback);
        Button btnaddg=(Button)dialog.findViewById(R.id.btnaddg);

        btnaddg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtgname.getText().toString();
                String num=txtgnum.getText().toString();

                if (name.length()==0||num.length()==0){
                    Toast.makeText(getApplicationContext(),"Empty fields",Toast.LENGTH_LONG).show();
                }
                else{
                    int number=Integer.parseInt(num);
                    database.insert(name,number,sqLiteDatabase);
                    listAdapter.swapcursur(database.getallguests(sqLiteDatabase));
                    imgempty.setVisibility(View.GONE);
                    dialog.dismiss();

                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
            dialog.show();
    }
}
