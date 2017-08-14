package com.xvshu.android;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.xvshu.android.db.CalcHisrory;
import com.xvshu.android.db.SqliteManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


/**
 * Created by xvshu on 2017/7/26.
 */
public class CalcHistoryActivity extends Activity {

    private ListView lvCalc;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_history);
        lvCalc=(ListView) findViewById(R.id.LV_Calc);
        showCalchistory();
    }

    private void showCalchistory(){
        List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query(CalcHisrory.TableName, null, null," _id desc ");
        List<String> historys =new ArrayList<String>();
        while (result.cursor.moveToNext()) {
            historys.add(result.cursor.getString(result.cursor.getColumnIndex(CalcHisrory.calcValueName)));
        }
        if(historys!=null && historys.size()>0){
            for(String one : historys){
                HashMap<String,Object> item=new HashMap<String, Object>();
                item.put("item_calc",one);
                data.add(item);
            }
        }
        SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.calc_history_item,
                new String[]{"item_calc"},new int[]{R.id.item_calc});

        lvCalc.setAdapter(adapter);
    }

    public void back(View view){
        this.finish();
    }

}
