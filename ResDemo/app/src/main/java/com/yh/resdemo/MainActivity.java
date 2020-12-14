package com.yh.resdemo;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        String str = getResources().getString(R.string.textValue);
        Log.d("str ", str);
        tv.setText(R.string.textValue);
        tv.setBackgroundColor(getResources().getColor(R.color.red));
        tv.setTextSize(getResources().getDimensionPixelSize(R.dimen.text));
        img = findViewById(R.id.img);
        img.setImageResource(R.drawable.img3);
        XmlResourceParser  p= getResources().getXml(R.xml.users);
        try{
            while (p.getEventType() != XmlResourceParser.END_DOCUMENT){
                if(p.getEventType() == XmlResourceParser.START_TAG){
                    if(p.getName().equals("user")){
                        String name = p.getAttributeValue(null, "name");
                        String age = p.getAttributeValue(null, "age");
                        Log.d("name", name);
                        Log.d("age", age);
                    }
                }
                p.next();
            }
        }catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }

    }
}