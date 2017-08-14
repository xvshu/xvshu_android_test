package com.xvshu.android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.xvshu.android.db.CalcHisrory;
import com.xvshu.android.db.SqliteManage;
import com.xvshu.android.service.HelloService;

import java.util.Stack;


/**
 * Created by xvshu on 2017/7/26.
 */
public class CalcActivity extends Activity {

    private TextView printTv = null;
    //声明两个参数。接收tvResult前后的值
    double num1=0,num2=0;

    int op=0;//判断操作数，
    boolean isClickEqu=false;//判断是否按了“=”按钮


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);
        printTv=(TextView) findViewById(R.id.print_show);
    }

    public void keyClick(View v) {
        String sbcalc=printTv.getText().toString();
        System.out.println(sbcalc);
        if (v == null) return;
        if (printTv.getText().toString().indexOf("=")>=0) {
            System.out.println("hava = ");
            sbcalc="";
        }
        switch (v.getId()) {
            case R.id.key_clean:
                System.out.println("R.id.key_clean");
                sbcalc="";
                break;
            case R.id.key_C:
                System.out.println("R.id.key_C");
                try {
                    sbcalc=sbcalc.substring(0, sbcalc.length()-1);
                } catch (Exception e) {
                    sbcalc="";
                }
                break;
            case R.id.key_hisrory:
                System.out.println("R.id.key_hisrory");
                showCalcHistory();
                return;
            default:
                break;
        }

        switch (v.getId()) {
            case R.id.key_0:
                System.out.println("R.id.key_0");
                if (sbcalc.toString().length() != 0) {
                    sbcalc+="0";
                }
                break;
            case R.id.key_1:
                System.out.println("R.id.key_1");
                sbcalc+="1";
                break;
            case R.id.key_2:
                System.out.println("R.id.key_2");
                sbcalc+="2";
                break;
            case R.id.key_3:
                System.out.println("R.id.key_3");
                sbcalc+="3";
                break;
            case R.id.key_4:
                System.out.println("R.id.key_4");
                sbcalc+="4";
                break;
            case R.id.key_5:
                System.out.println("R.id.key_5");
                sbcalc+="5";
                break;
            case R.id.key_6:
                System.out.println("R.id.key_6");
                sbcalc+="6";
                break;
            case R.id.key_7:
                System.out.println("R.id.key_7");
                sbcalc+="7";
                break;
            case R.id.key_8:
                System.out.println("R.id.key_8");
                sbcalc+="8";;
                break;
            case R.id.key_9:
                System.out.println("R.id.key_9");
                sbcalc+="9";
                break;
            case R.id.key_point:
                System.out.println(" . ");
                if (sbcalc.length() != 0)
                    sbcalc+=".";
                break;

            //加减乘除
            case R.id.key_add:
                System.out.println(" + ");
                sbcalc+=" + ";
                break;

            case R.id.key_sub:
                System.out.println(" - ");
                sbcalc+=(" - ");
                break;
            case R.id.key_mul:
                System.out.println(" x ");
                sbcalc+=(" x ");
                break;
            case R.id.key_div:
                System.out.println(" ÷ ");
                sbcalc+=(" ÷ ");
                break;
            case R.id.key_print:
                System.out.println(" = ");
                sbcalc=(sbcalc+"="+compute(sbcalc));
                addHistroy(sbcalc);
                break;
            default:
                break;
        }
        printTv.setText(sbcalc);
    }

    private String compute(String input)//即1237 的 样例
    {
        String str[];
        str = input.split(" ");
        Stack<Double> s = new Stack<Double>();
        double m = Double.parseDouble(str[0]);
        s.push(m);
        for(int i=1;i<str.length;i++)
        {
            if(i%2==1)
            {
                if(str[i].compareTo("+")==0)
                {
                    double help = Double.parseDouble(str[i+1]);
                    s.push(help);
                }

                if(str[i].compareTo("-")==0)
                {
                    double help = Double.parseDouble(str[i+1]);
                    s.push(-help);
                }

                if(str[i].compareTo("x")==0)
                {
                    double help = Double.parseDouble(str[i+1]);
                    double ans = s.peek();//取出栈顶元素
                    s.pop();//消栈
                    ans*=help;
                    s.push(ans);
                }

                if(str[i].compareTo("÷")==0)
                {
                    double help = Double.parseDouble(str[i+1]);
                    double ans = s.peek();
                    s.pop();
                    ans/=help;
                    s.push(ans);
                }
            }
        }
        double ans = 0d;
        while(!s.isEmpty())
        {
            ans+=s.peek();
            s.pop();
        }
        String result = String.valueOf(ans);
        return result;
    }

    private void addHistroy(String calcValue){
        ContentValues valuesIn = new ContentValues();
        valuesIn.put(CalcHisrory.calcValueName, calcValue);
        SqliteManage.getInstance(this).insertItem(CalcHisrory.TableName, valuesIn);
    }

    public void showCalcHistory(){
        Intent intent_calc = new Intent(this, CalcHistoryActivity.class);
        startActivity(intent_calc);
    }
    public void back(View view){
        this.finish();
    }

}
