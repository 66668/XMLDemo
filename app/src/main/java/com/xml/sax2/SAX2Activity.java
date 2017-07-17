package com.xml.sax2;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xml.Channel;
import com.xml.R;
import com.xml.Students;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 标准步骤
 * java中用的比较多
 * <p>
 * SAX原理：对文档进行顺序扫描，当扫描到文档(Document)开始于结束，元素(Element)开始于结束的地方时，就会触发方法处理事件，并由该处的方法做相应动作，直到文档结束
 */

public class SAX2Activity extends AppCompatActivity {
    List<Channel> list = new ArrayList<>();//解析数据存储在list中
    List<Students> studentsList = new ArrayList<>();
    TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sax2);
        tvshow = (TextView) findViewById(R.id.tv_show);
        /**
         * 标准解析步骤：
         *
         * 实例一个工厂SAXParserFactory
         * 实例化SAXParser对象，创建XMLReader解析器
         * 实例化handler对象
         * 解析器注册一个事件
         * 读取流文件
         * 解析文件
         *
         */
        /**
         * 解析channels.xml
         */
        findViewById(R.id.btn_sax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saxParser1();
            }
        });

        /**
         * 解析students.xml
         */
        findViewById(R.id.btn_sax_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saxParser2();
            }
        });
    }

    /**
     * 解析 channels.xml
     */
    private void saxParser1() {
        InputStream inputStream = null;
        try {
            //实例化SaxParserFactory对象
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //实例化 SAXparser对象，创建XMLReader对象解析器
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            //实例化 handler事件处理器(处理具体内容)
            SAX2ChannelParserHandler handler = new SAX2ChannelParserHandler();

            //解析器注册事件
            xmlReader.setContentHandler(handler);

            //读取channel文件（放在assets下 或者res/raw下,可以切换测试）
            //方式1：放在assets下读取
            AssetManager assetManager = getResources().getAssets();
            inputStream = assetManager.open("channels.xml");

            //方式2：res/raw下读取
            //                     InputStream inputStream = getResources().openRawResource(R.raw.channels);
            InputSource inputSource = new InputSource(inputStream);

            //解析文件
            xmlReader.parse(inputSource);
            list = handler.getList();

            //显示
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i).toString());
            }
            tvshow.setText(stringBuffer.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
     *
     */
    private void saxParser2() {
        InputStream inputStream = null;
        try {
            //实例化SaxParserFactory对象
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //实例化 SAXparser对象，创建XMLReader对象解析器
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            //实例化 handler事件处理器(处理具体内容)
            SAX2StudentsParserHandler handler = new SAX2StudentsParserHandler();

            //解析器注册事件
            xmlReader.setContentHandler(handler);

            //读取channel文件（放在assets下 或者res/raw下,可以切换测试）
            //方式1：放在assets下读取
            AssetManager assetManager = getResources().getAssets();
            inputStream = assetManager.open("students.xml");

            //方式2：res/raw下读取
            //                     InputStream inputStream = getResources().openRawResource(R.raw.students);
            InputSource inputSource = new InputSource(inputStream);

            //解析文件
            xmlReader.parse(inputSource);
            studentsList = handler.getList();

            //显示
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < studentsList.size(); i++) {
                stringBuffer.append(studentsList.get(i).toString());
            }
            tvshow.setText(stringBuffer.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
