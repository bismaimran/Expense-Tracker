package com.example.expensemanage;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.database.Cursor;
 import android.os.Bundle;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.TableLayout;
 import android.widget.TableRow;
import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

 import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<String> data=new ArrayList<String>();
    private ArrayList<String> data1=new ArrayList<String>();
    private ArrayList<String> data2=new ArrayList<String>();
    private ArrayList<String> data3=new ArrayList<String>();
    private ArrayList<String> data4=new ArrayList<String>();

    private TableLayout table;
    EditText ed1,ed2,ed3,ed4,ed5,ed6;
    Button b1;
    SharedPreferences sharedPreferences;
    public static final String mypreference="MYPREFS";
    private int counter;
    DBHelper DB;
    MainActivity MA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences=getSharedPreferences(mypreference,0);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        ed4=findViewById(R.id.txtsub);
        ed5=findViewById(R.id.txtpay);
        ed6=findViewById(R.id.txtbal);
        b1=findViewById(R.id.btn1);
        DB= new DBHelper(this);

        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int subtotal=Integer.parseInt(ed4.getText().toString());
                int pay=Integer.parseInt(ed5.getText().toString());
                int bal=pay-subtotal;
                ed6.setText(String.valueOf(bal));



            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });

    }
    public void add(){
        int tot;
        String prodname=ed1.getText().toString();
        int price=Integer.parseInt(ed2.getText().toString());
        int qty=Integer.parseInt(ed3.getText().toString());
        tot=price*qty;
        data.add(prodname);
        data1.add(String.valueOf(price));
        data2.add(String.valueOf(qty));
        data3.add(String.valueOf(tot));
        TableLayout table=(TableLayout) findViewById(R.id.tb1);
        TableRow row=new TableRow(this);
        TextView t1=new TextView(this);
        TextView t2=new TextView(this);
        TextView t3=new TextView(this);
        TextView t4=new TextView(this);
        String total;
        int sum=0;
        for (int i=0;i<data.size();i++){
            String pname=data.get(i);
            String prc=data1.get(i);
            String qtyy=data2.get(i);
            total=data3.get(i);

            t1.setText(pname);
            t2.setText(prc);
            t3.setText(qtyy);
            t4.setText(total);
            sum=sum+Integer.parseInt(data3.get(i).toString());
        }
        row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);
            table.addView(row);

        ed4.setText(String.valueOf(sum));
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");
        ed1.requestFocus();
        Boolean checkinsertdata=DB.insertproductdata(prodname,String.valueOf(price),String.valueOf(qty));
        if (checkinsertdata==true)
            Toast.makeText(HomeActivity.this,"New Entry Inserted",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(HomeActivity.this,"New Entry Not Inserted",Toast.LENGTH_SHORT).show();




    }
    public void get(View view) {
        Cursor res=DB.getdata();
        if (res.getCount()==0){
            Toast.makeText(HomeActivity.this,"No Entry Exists",Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer= new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Prodname:"+res.getString(0)+" ");
            buffer.append("Price:"+res.getString(1)+" ");
            buffer.append("Quantity:"+res.getString(2)+"\n\n");
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Product Entries");
        builder.setMessage(buffer.toString());
        builder.show();

        }

}
