package com.xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xml.dom.DOMActivity;
import com.xml.pull.PullActivity;
import com.xml.sax1.SAXActivity;
import com.xml.sax2.SAX2Activity;

/**
 * 　SAX与Pull的区别：
 * SAX解析器的工作方式是自动将事件处理器进行处理，因此不能控制事件的处理主动结束；
 * 而Pull解析器的工作方式为允许应用程序代码主动从解析器中获取事件，
 * 正因为是主动获取事件，因此可以在满足了需要的条件后不再获取事件，结束解析。
 *
 *
 *  SAX与DOM的对比：
 *  DOM:将XML文件解析成树状模型并放入内存中完成解析工作的，对于文档的操作都是在树状模型上完成的。
 *  结构清晰，易于开发，可以随时访问，但是生成的文档是xml的或几倍大小，消耗内存
 *  SAX:克服DOM的缺点，可以只解析满足条件的内容，不必解析整个文档。只能顺顺解析一遍，不支持随意访问。
 *
 *                   SAX                                             DOM
 *
 *
 * 顺序读入文档并产生响应事件，可以处理任意大小的文档  |   在内存中创建文档树，不适于处理大型文档
 *
 * 只能对文档按顺序解析一遍，不支持文档随意访问       |    可以随意访问文档树的任何部分，没有次数限制
 *
 * 只能读XML文档内容，不能修改                      | 可以随意修改文档树，进而修改XML文档
 *
 * 开发上比较复杂，需要自己实现事件解析器             | 易于理解，易于开发
 *
 * 对开发人员更灵活，可以用SAX创建自己的XML对象模型   |已经在DOM基础上创建好文档树
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
         *DOM
         */
        findViewById(R.id.btn_dom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DOMActivity.class);
                startActivity(intent);
            }
        });

        /**
         * PULl解析
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
