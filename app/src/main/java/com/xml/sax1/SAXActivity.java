package com.xml.sax1;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xml.Channel;
import com.xml.R;
import com.xml.Students;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * java中用的比较多
 * <p>
 * SAX原理：对文档进行顺序扫描，当扫描到文档(Document)开始于结束，元素(Element)开始于结束的地方时，就会触发方法处理事件，并由该处的方法做相应动作，直到文档结束
 */

public class SAXActivity extends AppCompatActivity {
    List<Channel> list = new ArrayList<>();//解析数据存储在list中
    TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sax2);
        tvshow = (TextView) findViewById(R.id.tv_show);

        /**
         *  channels.xml
         *  该步骤比较简单，
         *  见SAX2Activity标准步骤
         *
         */
        findViewById(R.id.btn_sax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream inputStream = null;
                try {

                    //获取文件
                    AssetManager assetManager = getResources().getAssets();
                    inputStream = assetManager.open("channels.xml");

                    //实例化SaxParserFactory对象
                    SAXParserFactory factory = SAXParserFactory.newInstance();

                    //实例化 SAXparser对象，创建XMLReader对象解析器
                    SAXParser saxParser = factory.newSAXParser();

                    ////实例化 handler事件处理器(处理具体内容),回调处理显示
                    saxParser.parse(inputStream, new SAXChannelParserHandler(callBack));

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
        });

        /**
         * students.xml
         */
        findViewById(R.id.btn_sax_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream inputStream = null;
                try {

                    //获取文件
                    AssetManager assetManager = getResources().getAssets();
                    inputStream = assetManager.open("students.xml");

                    //实例化SaxParserFactory对象
                    SAXParserFactory factory = SAXParserFactory.newInstance();

                    //实例化 SAXparser对象，创建XMLReader对象解析器
                    SAXParser saxParser = factory.newSAXParser();

                    ////实例化 handler事件处理器(处理具体内容),回调处理显示
                    saxParser.parse(inputStream, new SAXStudentsParserHandler(studentCallBack));

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
        });
    }

    /**
     * channel接口回调处理显示
     */
    private ChannelCallback callBack = new ChannelCallback() {
        @Override
        public void parseCallback(List<Channel> date) {
            if (date != null) {
                //显示
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < date.size(); i++) {
                    stringBuffer.append(date.get(i).toString());
                }
                tvshow.setText(stringBuffer.toString());
            } else
                tvshow.setText("sax解析");
        }
    };

    /**
     * Students接口回调处理显示
     */
    private StudentsCallback studentCallBack = new StudentsCallback() {
        @Override
        public void parseCallback(List<Students> date) {
            if (date != null) {
                //显示
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < date.size(); i++) {
                    stringBuffer.append(date.get(i).toString());
                }
                tvshow.setText(stringBuffer.toString());
            } else
                tvshow.setText("sax解析");
        }
    };
}
