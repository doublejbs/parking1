package com.example.suh.parking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by psh on 18. 3. 15.
 */

public class RegisterActivity extends Activity{
    private Spinner spBrand = null;
    private Spinner carList = null;
    private ArrayAdapter<String> bAdapter =  null;
    private ArrayAdapter<String> cAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            String getText = null;
            setContentView(R.layout.register_page);
            super.onCreate(savedInstanceState);

            spBrand = (Spinner)findViewById(R.id.brand);
            carList = (Spinner)findViewById(R.id.car_type);
            bAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                    (String[])getResources().getStringArray(R.array.brandList));

            spBrand.setAdapter(bAdapter);

            getText = spBrand.getSelectedItem().toString();
            //계속 여기서 갱신해야할텐데 방법 찾기
            if(getText=="BMW") {
                cAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                        (String[]) getResources().getStringArray(R.array.bwm_carList));
            }else if(getText=="BENZ") {
                cAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                        (String[]) getResources().getStringArray(R.array.benz_carList));
            }


            carList.setAdapter(cAdapter);
        //spinner 이벤트
     //   spBrand.setOnItemClickListener();
    }
}
