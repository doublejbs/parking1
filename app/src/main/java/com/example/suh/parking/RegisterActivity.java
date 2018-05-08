package com.example.suh.parking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by psh on 18. 3. 15.
 */



public class RegisterActivity extends Activity{
    private Spinner spBrand = null;
    private Spinner carList = null;
    private EditText nickName = null;
    private ArrayAdapter<String> bAdapter =  null;
    private ArrayAdapter<String> cAdapter = null;
    private Button submitBtn =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            String getText = null;
            setContentView(R.layout.register_page);
            super.onCreate(savedInstanceState);

            spBrand = (Spinner)findViewById(R.id.brand);
            carList = (Spinner)findViewById(R.id.car_type);


            nickName = (EditText)findViewById(R.id.nickname);
            submitBtn= (Button)findViewById(R.id.submit);


            bAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                    (String[])getResources().getStringArray(R.array.brandList));



            spBrand.setAdapter(bAdapter);
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        cAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                                (String[])getResources().getStringArray(R.array.samsung_carList));
                        carList.setAdapter(cAdapter);
                        break;
                    case 1:
                        cAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                                (String[])getResources().getStringArray(R.array.chevolet_carList));
                        carList.setAdapter(cAdapter);
                        break;
                    case 2:
                        cAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                                (String[])getResources().getStringArray(R.array.bmw_carList));
                        carList.setAdapter(cAdapter);
                        break;
                    case 3:
                        cAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                                (String[])getResources().getStringArray(R.array.benz_carList));
                        carList.setAdapter(cAdapter);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

           //



/*
            getText = spBrand.getSelectedItem().toString();
            //계속 여기서 갱신해야할텐데 방법 찾기
            if(getText=="BMW") {
                cAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                        (String[]) getResources().getStringArray(R.array.bwm_carList));
            }else if(getText=="BENZ") {
                cAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                        (String[]) getResources().getStringArray(R.array.benz_carList));
            }
*/

 //           carList.setAdapter(cAdapter);


            submitBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    dbClass newdb = new dbClass(getApplicationContext(),"MYLIST.db",null,1);
                    newdb.insert(spBrand.getSelectedItem().toString(),carList.getSelectedItem().toString(),nickName.getText().toString());
                    Toast.makeText(RegisterActivity.this, nickName.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent1);
                }
            });
        //spinner 이벤트
     //   spBrand.setOnItemClickListener();
    }
}
