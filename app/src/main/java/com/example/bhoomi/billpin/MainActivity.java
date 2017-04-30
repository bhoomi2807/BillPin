package com.example.bhoomi.billpin;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.prefs.Preferences;

public class MainActivity extends Activity {

    String msgData="";
    String saveMsg="";
    int data_block =100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor=getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        float sum1=0,sum2=0,sum3=0,sum=0;
        if(cursor.moveToFirst())                                //first entry of msg ka first character
        {
            do{

                for(int i=0;i<cursor.getColumnCount();i++)//
                    msgData+=" "+ cursor.getColumnName(i) + "\n"+ cursor.getString(i);
                saveMsg=msgData;

                Button Save,Load;
                Save=(Button)findViewById(R.id.save);
                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try
                        {
                            FileOutputStream fou=openFileOutput("sms.txt",MODE_WORLD_READABLE);
                            OutputStreamWriter osw=new OutputStreamWriter(fou);
                            try{
                                osw.write(saveMsg);
                                osw.flush();
                                osw.close();
                                Toast.makeText(getBaseContext(),"Messages saved",Toast.LENGTH_LONG).show();
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }

                });
                Load=(Button)findViewById(R.id.load);
                Load.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            FileInputStream fis=openFileInput("sms.txt");
                            InputStreamReader isr=new InputStreamReader(fis);
                            char[] data=new char[data_block];
                            String final_data="";
                            int size;
                            try {
                                while((size=isr.read(data))>0)
                                {
                                    String read_data=String.copyValueOf(data,0,size);
                                    final_data=read_data;
                                    data=new char[data_block];
                                }
                                Toast.makeText(getBaseContext(),"Messaage :"+final_data,Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });
                if((msgData.toLowerCase().contains("inr"))&&(msgData.toLowerCase().contains("debit")))
                {
                    Log.i("a","================================================================ INR DEBIT PRINTING MesSaGe DATA=====================================\n"+msgData);
                    int l=msgData.toLowerCase().indexOf("inr");
                    //if(l==-1) {
                      //  l = msgData.toLowerCase().indexOf("rs ");

                    //}
                    String n1="";
                    int i;
                    for(i=l+3;;i++){
                        String ind=Integer.toString(i);
                        Log.i("a"," INR AND DEBIT INDEX");
                        Log.i("a",ind);
                        char ab=msgData.charAt(i);
                        String abb = Character.toString(ab);
                        Log.i("a"," INR AND DEBIT CHARACTER TAKING");
                        Log.i("a",abb);
                        if(msgData.charAt(i)==' ') {
                            Log.i("a","INR AND DEBIT FOR ENTERS SPACE SO BREAK");
                            break;
                        }
                        else if(msgData.charAt(i)==',') {
                            Log.i("a","INR AND DEBIT FOR ENTERS COMMA SO NO BREAK");
                            continue;
                        }
                        n1=n1+msgData.charAt(i);
                    }
                    Log.i("a","INR AND DEBIT  AMOUNT AFTER CONCATENATION OF SINGLE DIGIT");
                    Log.i("a",n1);
                    Log.i("a","INR AND DEBIT TOTAL");
                    if(n1!="")
                      sum1=sum1+Float.parseFloat(n1);

                    String summ=Float.toString(sum1);
                    Log.i("a","INR AND DEBIT TOTAL");
                    Log.i("a",summ);

                }
                if((msgData.toLowerCase().contains(" rs "))&&(msgData.toLowerCase().contains("debit")))
                {
                    int l;
                    Log.i("a","++++++++++++++++++++++++++++++++++++++++++ RUPEES AND DEBIT +++++++++++++++++++++++PRINTING MSG DATA+++++++++++++++++++++++++++++++++\n"+msgData);
                    l=msgData.toLowerCase().indexOf(" rs ");
                    //if(l==-1) {
                    //    l = msgData.toLowerCase().indexOf(" rs");
                    //    String l1 = Integer.toString(l);
                    //    Log.i("a","INDEX OF RUPEES AND DEBITED");
                    //    Log.i("a",l1);
                    //}
                    String n2="";


                    for(int i=l+4;;i++){

                        //String ind=Integer.toString(i);
                        //Log.i("a"," RUPEES AND DEBITED INDEX");
                        //Log.i("a",ind);
                        //char ab=msgData.charAt(i);
                        //String abb = Character.toString(ab);
                        //Log.i("a"," RUPEES AND DEBITED CHARACTER TAKING");
                        //Log.i("a",abb);

                        if(msgData.charAt(i)==' ')
                        {
                            Log.i("a","RUPEES AND DEBITED FOR ENTERS SPACE SO BREAK");
                            break;
                        }
                        else if(msgData.charAt(i)==',')
                        {
                            Log.i("a","RUPEES AND DEBITED FOR ENTERS FOR COMMA SO CONTINUE");
                            continue;
                        }
                        n2=n2+msgData.charAt(i);
                    }
                    Log.i("a","RUPEES AND DEBITED AMOUNT AFTER CONCATENATION OF SINGLE DIGIT");
                    Log.i("a",n2);

                    float number = new Float(n2).floatValue();
                    sum2 = sum2 + number;
                    String summ2=Float.toString(sum2);
                    Log.i("a","RUPEES AND DEBITED TOTAL");
                    Log.i("a",summ2);
                }

                if((msgData.toLowerCase().contains(" rs"))&&(msgData.toLowerCase().contains("withdrawn")))
                {
                    int l;
                    Log.i("a","++++++++++++++++++++++++++++++++++++++++++ RUPEES AND WITHDRAWN +++++++++++++++++++++++PRINTING MSG DATA+++++++++++++++++++++++++++++++++\n"+msgData);
                    //l=msgData.toLowerCase().indexOf("inr");
                    //if(l==-1) {
                        l = msgData.toLowerCase().indexOf("rs ");
                       // String l1 = Integer.toString(l);
                       // Log.i("a","RUPEES AND WITHDRAW INDEX ");
                       // Log.i("a",l1);
                    //}
                    String n3="";
                    for(int i=l+3;;i++){

                        Log.i("a","INDEX RUPEES AND WITHDRAW");
                        String ind=Integer.toString(i);
                        char ab=msgData.charAt(i);
                        String abb = Character.toString(ab);
                        Log.i("a"," RUPEES AND WITHDRAW CHARACTER TAKING");
                        Log.i("a",abb);

                        if(msgData.charAt(i)==' ')
                        {
                            Log.i("a","FOR ENTERS SPACE RUPEES AND WITHDRAW");
                            break;
                        }
                        else if(msgData.charAt(i)==',')
                        {
                            Log.i("a","FOR ENTERS FOR COMMA RUPEES AND WITHDRAW");
                            continue;
                        }
                        else if(msgData.charAt(i)=='.')
                        {
                            Log.i("a","FOR ENTERS FOR . RUPEES AND WITHDRAW BREAK");
                            break;
                        }
                        else if(msgData.charAt(i-7)=='l')
                        {
                            Log.i("a","FOR ENTERS FOR L OF AVAILABLE RUPEES AND WITHDRAW");
                            break;
                        }
                        n3=n3+msgData.charAt(i);
                    }
                    Log.i("a","RUPEES AND WITHDRAW AMOUNT");
                    Log.i("a",n3);
                    Log.i("a","TOTAL RUPEES AND WITHDRAW");
                    float number = new Float(n3).floatValue();
                    sum3 = sum3 + number;
                    String summ3=Float.toString(sum3);
                    Log.i("a","RUPEES AND WITHDRAW TOTAL");
                    Log.i("a",summ3);
                }
            }while(cursor.moveToNext());
            sum=sum1+sum2+sum3;
            Log.i("a",Float.toString(sum));
            TextView exp;
            exp = (TextView)findViewById(R.id.expense);
            exp.setText(Float.toString(sum));
        }

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
