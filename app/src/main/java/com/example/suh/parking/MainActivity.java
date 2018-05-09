package com.example.suh.parking;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;
    private FloatingActionButton fab_delete;
    private NumberPicker alphabet_picker;
    private NumberPicker number_picker;
    private NumberPicker undergroundcheck_picker;
    private NumberPicker floorNum_picker;
    private NumberPicker cen_text;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbClass newdb = new dbClass(getApplicationContext(),"MYLIST.db",null,1);

        String list = newdb.read();


        //DB 에서 가져와서 listview에 현재 가지고 있는 모든 차량 리스트를 뽑음
        ArrayList<String> arr = new ArrayList<>();
         String[] imsi = list.split("/");
         for(String item:imsi){
            arr.add(item);
        }


        //리스트 어뎁터 생성
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);
        // simple_list_item_1 은 리스트당 1개의 목록만 띄우는것

        //리스트 뷰에 어뎁터 적용
        listView = (ListView) findViewById(R.id.car_list);

        listView.setAdapter(adapter);
        if(adapter.getCount()==0)
            listView.setVisibility(View.INVISIBLE);
        //   listView.setVisibility(View.VISIBLE);

        //리스트뷰 선택시 일단 색깔 바꾸고 다음에는 그 selector로 인식하는걸로 하자
        //그리고 그 눌렀을때 자동차의 위치를 알려주면 되겠다.
        // 절반은 listview 나머지 절반은 자동차 위치를 알려주는 text 라던지 scroll이라던지로 하면될듯?
        listView.setClickable(true);

        listView.setSelector(new PaintDrawable(0xFF808080));  //색깔만 표시 일단 해두고



        //number picker 선택
        undergroundcheck_picker = (NumberPicker)findViewById(R.id.undergroundCheck);
        floorNum_picker= (NumberPicker)findViewById(R.id.numOfFloor);
        cen_text = (NumberPicker)findViewById(R.id.center_text);
        alphabet_picker = (NumberPicker)findViewById(R.id.alphabetpick);
        number_picker = (NumberPicker)findViewById(R.id.numberpick);

        final String[] underground_array = new String[]{"지상","지하"};
        undergroundcheck_picker.setMinValue(0);
        undergroundcheck_picker.setMaxValue(underground_array.length-1);
        undergroundcheck_picker.setDisplayedValues(underground_array);


        floorNum_picker.setMinValue(1);
        floorNum_picker.setMaxValue(10);


       final String[] center_text = new String[]{("층")};

       cen_text.setDisplayedValues(center_text);


        final String[] alphabet_array = new String[]{"A","B","C","D","E","F","G","H","I","J"
                                                ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
                                                ,"1","2","3","4","5","6","7","8","9","가","나",
        "다","라","마","바","사","아","자","차","카","타","파","하"};

        alphabet_picker.setMinValue(0);
        alphabet_picker.setMaxValue(alphabet_array.length-1);
        alphabet_picker.setDisplayedValues(alphabet_array);



        number_picker.setMinValue(0);
        number_picker.setMaxValue(99);

        final String[] nickName = {null};
        //여기다가 추가해야할듯
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  SparseBooleanArray positions = listView.getCheckedItemPositions();
                int pos = parent.getPositionForView(view);
                nickName[0] = listView.getItemAtPosition(pos).toString();
            //    Toast.makeText(MainActivity.this,listView.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();
                dataDB check_db = new dataDB(getApplicationContext(),"POSITION.db",null,1);
                String result = check_db.read(nickName[0]);
                if(result.isEmpty()){
                }else
                    Toast.makeText(getApplicationContext(),result+ "에 위치해 있음",Toast.LENGTH_LONG).show();
                    //안비어있으면 위치 띄워주기
            }

        });


        //등록버튼 활성화
        //버튼시 해당 DB에 주차 위치가 지상/지하 , 층수 , 1 - 10 이런식으로 저장됨
        btn =(Button)findViewById(R.id.submit_position);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    int undergroundcheck_picker_index = undergroundcheck_picker.getValue();
                    int floornum_picker_value = floorNum_picker.getValue();
                    int alphabet_picker_index = alphabet_picker.getValue();
                    int number_picker_value = number_picker.getValue();
      //          Toast.makeText(MainActivity.this, underground_array[undergroundcheck_picker_index], Toast.LENGTH_SHORT).show();
                if(nickName[0]==null) //이 줄은 똑바로 안되는듯?
                    Toast.makeText(MainActivity.this,"차량 선택을 먼저 해 주세요",Toast.LENGTH_LONG);
                else {
                       dataDB nick_db = new dataDB(getApplicationContext(),"POSITION.db",null,1);
                       nick_db.delete(nickName[0]);
                       nick_db.insert(nickName[0], underground_array[undergroundcheck_picker_index], floornum_picker_value
                            , alphabet_array[alphabet_picker_index], number_picker_value);
                       Toast.makeText(MainActivity.this,nickName[0]+ underground_array[undergroundcheck_picker_index]+floornum_picker_value
                               +alphabet_array[alphabet_picker_index]+ number_picker_value,Toast.LENGTH_LONG).show();
                       //저장 확인을 위한 toast
                }
            }
        });








        //floating action button 을 리스트 뷰에 적용
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                //+ 버튼 클릭시 register 페이지로 전달
            }
        });


        //floating action button 삭제 버튼
        fab_delete = (FloatingActionButton)findViewById(R.id.fab_delete);
        fab_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(nickName[0]==null) //이 줄은 똑바로 안되는듯?
                    Toast.makeText(MainActivity.this,"차량 선택을 먼저 해 주세요",Toast.LENGTH_LONG);
                else{
                        dbClass change_db = new dbClass(getApplicationContext(),"MYLIST.db",null,1);
                        change_db.delete(nickName[0]);

                    dataDB nick_db = new dataDB(getApplicationContext(),"POSITION.db",null,1);
                    nick_db.delete(nickName[0]);

                    String list = change_db.read();


                    //DB 에서 가져와서 listview에 현재 가지고 있는 모든 차량 리스트를 뽑음
                    ArrayList<String> arr = new ArrayList<>();
                    String[] imsi = list.split("/");
                    for(String item:imsi){
                        arr.add(item);
                    }


                    //리스트 어뎁터 생성
                    final ArrayAdapter<String> adapter
                            = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arr);
                         // simple_list_item_1 은 리스트당 1개의 목록만 띄우는것


                        adapter.notifyDataSetChanged();;
                        listView.setAdapter(adapter);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}


