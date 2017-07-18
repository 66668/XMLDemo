package com.xml.dom;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xml.model.Channel;
import com.xml.R;
import com.xml.model.Students;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * DOM方式:java中处理html和xml常用的方法
 */

public class DOMActivity extends AppCompatActivity {
    private final String TAG = "DOM";
    //
    List<Students> list;//解析数据存储在list中
    Students students;

    //
    List<Channel> channelList;
    Channel channel;
    TextView tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dom);
        tvshow = (TextView) findViewById(R.id.tv_show);
        /**
         * 解析students.xml：
         *
         */
        findViewById(R.id.btn_dom1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xmlDomParser();//解析students.xml
                show();
            }
        });

        /**
         * channel.xml：
         *
         */
        findViewById(R.id.btn_dom2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xmlDomParser2();//解析channel.xml
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
     * 具体解析students.xml的方法
     *
     * <student group="z1" id="001">
     * <name>张三</name>
     * <sex>男</sex>
     * <age>18</age>
     * <email>zhangsan@163.com</email>
     * <birthday>1987-06-08</birthday>
     * <memo>好学生</memo>
     * </student>
     */
    private void xmlDomParser() {
        InputStream inputStream = null;
        try {
            //创建factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //创建 DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();//factory可以用Xml类代替

            //获取文件
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open("students.xml");

            //创建Document对象，相当于代表xml表的对象
            Document document = builder.parse(inputStream);

            //获取根节点
            Element rootElement = document.getDocumentElement();

            //获取根节点所有student的节点
            NodeList parentNodeList = rootElement.getElementsByTagName("student");

            /**
             *  遍历所有的节点
             *
             */
            list = new ArrayList<>();
            //先遍历sutdent节点数据
            for (int i = 0; i < parentNodeList.getLength(); i++) {

                //student节点
                Element parentelement = (Element) parentNodeList.item(i);

                //获取student节点内的所有节点
                NodeList childNodeList = parentelement.getChildNodes();

                //获取<Students>标签内的参数
                String group = parentelement.getAttribute("group");
                String id = parentelement.getAttribute("id");

                students = new Students();
                students.setGroup(group);
                students.setId(id);

                //遍历student节点内的节点
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    //多标签加 Node.ELEMENT_NODE判断
                    if (childNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        //依次赋值
                        if ("name".equals(childNodeList.item(j).getNodeName())) {//注意，这里不是：childNodeList.item(j).getFirstChild().getNodeName()

                            String name = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "name =" + name);
                            students.setName(name);

                        } else if ("sex".equals(childNodeList.item(j).getNodeName())) {

                            String sex = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "sex =" + sex);
                            students.setSex(sex);

                        } else if ("age".equals(childNodeList.item(j).getNodeName())) {

                            String age = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "age =" + age);
                            students.setAge(age);

                        } else if ("email".equals(childNodeList.item(j).getNodeName())) {

                            String email = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "email =" + email);
                            students.setEmail(email);

                        } else if ("birthday".equals(childNodeList.item(j).getNodeName())) {

                            String birthday = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "birthday =" + birthday);
                            students.setEmail(birthday);

                        } else if ("memo".equals(childNodeList.item(j).getNodeName())) {

                            String memo = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "memo =" + memo);
                            students.setMemo(memo);

                        }
                    }
                }
                //添加到list中
                list.add(students);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
     * 具体解析channels.xml的方法
     */
    private void xmlDomParser2() {
        InputStream inputStream = null;
        try {
            //创建factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //创建 DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();//factory可以用Xml类代替

            //获取文件
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open("channels.xml");

            //创建Document对象，相当于代表xml表的对象
            Document document = builder.parse(inputStream);

            //获取根节点
            Element rootElement = document.getDocumentElement();

            //获取根节点所有student的节点
            NodeList nodeList = rootElement.getElementsByTagName("item");

            //遍历所有item节点
            channelList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                //item节点下没有节点，直接解析即可
                channel = new Channel();
                Element item = (Element) nodeList.item(i);
                channel.setId(item.getAttribute("id"));
                channel.setUrl(item.getAttribute("url"));
                channel.setName(item.getFirstChild().getNodeValue());

                //
                channelList.add(channel);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
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
