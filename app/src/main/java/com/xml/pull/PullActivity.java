package com.xml.pull;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.xml.R;
import com.xml.model.Students;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * java中用的比较多
 * <p>
 * 事件类型主要有五种
 * <p>
 * START_DOCUMENT：xml头的事件类型
 * END_DOCUMENT：xml尾的事件类型
 * START_TAG：开始节点的事件类型
 * END_TAG：结束节点的事件类型
 * TEXT：文本节点的事件类型
 */

public class PullActivity extends AppCompatActivity {
    private final String TAG = "PULL";
    List<Students> list;//解析数据存储在list中
    Students students;
    TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pull);
        tvshow = (TextView) findViewById(R.id.tv_show);
        /**
         * 解析步骤：
         *
         */
        findViewById(R.id.btn_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xmlPullParser();
                show();
            }
        });
    }

    //显示数据
    private void show() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i).toString());
        }
        tvshow.setText(buffer.toString());
    }

    private void xmlPullParser() {
        InputStream inputStream = null;
        try {


            //创建factory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //创建 XmlPullParser对象
            XmlPullParser pullParser = Xml.newPullParser();//factory可以用Xml类代替

            //获取文件
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open("students.xml");

            //解析格式
            pullParser.setInput(inputStream, "UTF-8");

            //解析详情：
            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {//文档标签
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://文档标签
                        //可以做初始化list操作
                        list = new ArrayList<>();
                        break;

                    case XmlPullParser.START_TAG://元素标签
                        String tagName = pullParser.getName();//获取标签名称
                        switch (tagName) {
                            case "student":
                                students = new Students();
                                //获取students标签下的参数 id,group
                                students.setId(pullParser.getAttributeValue(1));
                                students.setGroup(pullParser.getAttributeValue(0));//getAttributeName是获取key名称，getAttributeValue是获取value值
                                break;
                            case "name":
                                if (students != null) {
                                    students.setName(pullParser.getText());
                                    Log.d(TAG, "xmlPullParser: name=" + pullParser.getText());
                                }
                                break;
                            case "sex":
                                if (students != null) {
                                    students.setSex(pullParser.getText());
                                    Log.d(TAG, "xmlPullParser: sex=" + pullParser.getText());
                                }
                                break;
                            case "age":
                                if (students != null) {
                                    students.setAge(pullParser.getText());
                                }
                                break;
                            case "email":
                                if (students != null) {
                                    students.setEmail(pullParser.getText());
                                }
                                break;
                            case "birthday":
                                if (students != null) {
                                    students.setBirthday(pullParser.getText());
                                }
                                break;
                            case "memo":
                                if (students != null) {
                                    students.setMemo(pullParser.getText());
                                }
                                break;

                        }
                        break;
                    case XmlPullParser.END_TAG://元素标签 结束，保存到list中
                        String name = pullParser.getName();
                        switch (name) {
                            case "student":
                                if (students != null) {
                                    list.add(students);
                                }
                                break;
                        }
                        break;


                }
                eventType = pullParser.next();///调用的是next的方法 来获取当前的状态

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
