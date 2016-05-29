package com.example.rogith.deltaappdevtask1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    TextView counterTextView;
    RelativeLayout relativeLayout;
    public static final String MyPREFERENCES = "MyPrefs";
    int colorIndex=0;
    Context mContext;
    public static final String COUNTER="MyCounter";

    SharedPreferences localPreferences;
    private final List<Integer>ColorList=new ArrayList<Integer>(){
        {
            add(R.color.BkgColor1);
            add(R.color.BkgColor2);
            add(R.color.BkgColor3);
            add(R.color.BkgColor4);
        }
    };


    int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        counterTextView =(TextView) findViewById(R.id.textView);
        relativeLayout= (RelativeLayout) findViewById(R.id.MainLayoutID) ;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("colorIndex",colorIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        colorIndex = savedInstanceState.getInt("colorIndex",0);
        relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext,ColorList.get(colorIndex)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int groupID = 1;

        menu.add(groupID, Menu.FIRST,Menu.FIRST,"Reset Counter");
        menu.add(groupID,Menu.FIRST+1,Menu.FIRST,"Clear Local Files");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
                counter = 0;
                writeToFile(counter);
                counterTextView.setText(String.valueOf(counter));
                Toast.makeText(mContext,"Counter Reset",Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                SharedPreferences.Editor ed = localPreferences.edit();
                ed.remove(COUNTER);
                ed.apply();
                Toast.makeText(mContext,"Local Files Cleared",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        counter = getFromFile();
        counterTextView.setText(String.valueOf(counter));
    }


    @Override
    protected void onStop() {
        super.onStop();
        writeToFile(counter);
    }


    public void onBtn1Click(View v){

        counter++;
        colorIndex = colorIndex+1>=ColorList.size()?0:colorIndex+1;
        counterTextView.setText(String.valueOf(counter));
        relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext,ColorList.get(colorIndex)));
    }

    private void writeToFile(int value){
        SharedPreferences.Editor editor=localPreferences.edit();
        editor.putInt(COUNTER,value);
        editor.apply();
    }


    private int getFromFile(){
        localPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        if(localPreferences.contains(COUNTER))
            return localPreferences.getInt(COUNTER,0);
        else return 0;
    }
}
