package com.xml.pull;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xml.R;
import com.xml.model.Channel;
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

    List<Channel> channelList;
    Channel channel;
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
        //students.xml解析
        findViewById(R.id.btn_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xmlPullParser();
                show();
            }
        });

        //channels.xml解析
        findViewById(R.id.btn_channel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xmlPullParser2();
                show2();
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

    //显示数据
    private void show2() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < channelList.size(); i++) {
            buffer.append(channelList.get(i).toString());
        }
        tvshow.setText(buffer.toString());
    }

    /**
     * students.xml解析：
     * <student group="z1" id="001">
     * <name>张三</name>
     * <sex>男</sex>
     * <age>18</age>
     * <email>zhangsan@163.com</email>
     * <birthday>1987-06-08</birthday>
     * <memo>好学生</memo>
     * </student>
     */
    private void xmlPullParser() {
        InputStream inputStream = null;
        try {
            //创建factory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //创建 XmlPullParser对象
            // 方式一:使用工厂类XmlPullParserFactory
            // 方式二:使用Android提供的实用工具类android.util.Xml
            XmlPullParser pullParser = factory.newPullParser();//factory 可以用 Xml 类代替

            //获取文件
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open("students.xml");

            //解析格式
            pullParser.setInput(inputStream, "UTF-8");

            /**
             * 解析详情：
             *
             * xml的数据可能只有几个是所需的，所以可以根据case筛选需要的参数值
             *
             */

            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {//文档标签
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://文档标签
                        //可以做初始化list操作
                        list = new ArrayList<>();
                        break;
                    // 触发开始元素事件
                    case XmlPullParser.START_TAG:
                        String tagName = pullParser.getName();//获取标签名称
                        Log.d(TAG, "START_TAG:" + tagName);

                        //获取students标签下的参数 id,group
                        if (tagName.equals("student")) {
                            students = new Students();
                            students.setId(pullParser.getAttributeValue(1));
                            students.setGroup(pullParser.getAttributeValue(0));//getAttributeName是获取key名称，getAttributeValue是获取value值
                        }
                        /**
                         * student标签下的标签，用if else判断代替case方式（不喜欢嵌套case）
                         *
                         * 易错地方：
                         * （1）name sex age等所有标签，可以根据需求筛选一个两个保存。
                         * （2）但是一定不可以在一个if中调用两次nextText()方法，会出bug(可以用下边的log测试)
                         *
                         */
                        if (students != null) {
                            if ("name".equals(tagName)) {
                                //Log.d(TAG, tagName + "--" + pullParser.nextText());
                                students.setName(pullParser.nextText());
                            }
                            if ("sex".equals(tagName)) {
                                students.setSex(pullParser.nextText());
                            }
                            if ("age".equals(tagName)) {
                                students.setAge(pullParser.nextText());
                            }
                            if ("email".equals(tagName)) {
                                students.setEmail(pullParser.nextText());
                            }
                            if ("birthday".equals(tagName)) {
                                students.setBirthday(pullParser.nextText());
                            }
                            if ("memo".equals(tagName)) {
                                students.setMemo(pullParser.nextText());
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG://元素标签 结束，保存到list中
                        String name = pullParser.getName();
                        Log.d(TAG, "END_TAG:" + name);
                        if (name.equals("student")) {
                            if (students != null) {
                                list.add(students);
                            }
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT://文档标签
                        break;
                    default:
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

    /**
     * channels.xml解析:
     *
     * 易错地方：经测试，item下没有子标签，导致没有XmlPullParser.END_TAG的触发，所以保存list数据的操作不能在该条件下进行
     *
     * <p>
     * <item
     * id="0"
     * url="http://www.baidu.com">百度
     * </item>
     *
     */
    private void xmlPullParser2() {
        InputStream inputStream2 = null;
        try {
            //创建factory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //创建 XmlPullParser对象
            // 方式一:使用工厂类XmlPullParserFactory
            // 方式二:使用Android提供的实用工具类android.util.Xml
            XmlPullParser pullParser2 = factory.newPullParser();//factory 可以用 Xml 类代替

            //获取文件
            AssetManager assetManager = getAssets();
            inputStream2 = assetManager.open("channels.xml");

            //解析格式
            pullParser2.setInput(inputStream2, "UTF-8");

            /**
             * 解析详情：
             *
             * xml的数据可能只有几个是所需的，所以可以根据case筛选需要的参数值
             *
             */

            int eventType = pullParser2.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {//文档标签
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://文档标签
                        //可以做初始化list操作
                        channelList = new ArrayList<>();
                        break;
                    // 触发开始元素事件
                    case XmlPullParser.START_TAG:
                        String tagName = pullParser2.getName();//获取标签名称
                        Log.d(TAG, "START_TAG:" + tagName);
                        //获取item标签下的参数 id,group
                        if (tagName.equals("item")) {
                            channel = new Channel();
                            Log.d(TAG, "tagName:" + tagName + "--" + pullParser2.getAttributeValue(0) + "--" + pullParser2.getAttributeValue(1));
                            channel.setId(pullParser2.getAttributeValue(0));
                            channel.setUrl(pullParser2.getAttributeValue(1));//getAttributeName是获取key名称，getAttributeValue是获取value值
                            channel.setName(pullParser2.nextText());

                            channelList.add(channel);//保存到list中
                            channel = null;
                        }
                        break;

                    case XmlPullParser.END_TAG://元素标签 结束，
                        break;
                    default:
                        break;

                }
                eventType = pullParser2.next();///调用的是next的方法 来获取当前的状态
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
