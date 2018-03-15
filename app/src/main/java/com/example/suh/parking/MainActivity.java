package com.example.suh.parking;
//씨발 깃허브
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startActivity(new Intent(getApplicationContext(),SplashActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //리스트 목록 생성
        String[] listString = {"list1","list2","list3"};

        //리스트 어뎁터 생성
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listString);
        // simple_list_item_1 은 리스트당 1개의 목록만 띄우는것

        //리스트 뷰에 어뎁터 적용
        listView = (ListView) findViewById(R.id.car_list);
        listView.setAdapter(adapter);

        //floating action button 을 리스트 뷰에 적용
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
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
