package com.xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xml.pull.PullActivity;
import com.xml.sax1.SAXActivity;
import com.xml.sax2.SAX2Activity;

/**
 * 　SAX与Pull的区别：
 * SAX解析器的工作方式是自动将事件处理器进行处理，因此不能控制事件的处理主动结束；
 * 而Pull解析器的工作方式为允许应用程序代码主动从解析器中获取事件，
 * 正因为是主动获取事件，因此可以在满足了需要的条件后不再获取事件，结束解析。
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *SAX
         */
        findViewById(R.id.btn_sax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SAXActivity.class);
                startActivity(intent);
            }
        });

        /**
         *SAX2
         */
        findViewById(R.id.btn_sax2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SAX2Activity.class);
                startActivity(intent);
            }
        });

        /**
         * PULl
         */
        findViewById(R.id.btn_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PullActivity.class);
                startActivity(intent);
            }
        });
    }
}
