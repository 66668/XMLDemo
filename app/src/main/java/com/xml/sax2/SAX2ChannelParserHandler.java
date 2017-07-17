package com.xml.sax2;


import android.util.Log;

import com.xml.Channel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析 channel.xml 解析完后，调用.getList()方法获取数据，区别于SAXParserHandler使用回调
 * <p>
 * <p>
 * <p>
 * 这是安卓中内置的用于SAX处理XML的类，但是大多情况下我们都需要继承该类重写部分方法，才能达到处理XML数据的功能。
 * <p>
 * startDocument方法
 * 这是第一个需要重写的方法，每处理一个XML文档都会响应一次。所以这个方法里可以写需要初始化的代码。
 * <p>
 * startElement方法
 * 这是处理每个节点所触发的第一个方法，通过这个方法你可以直接当前处理的节点的名称以及属性。
 * <p>
 * characters方法
 * 这是处理每个节点所触发的第二个方法，通过这个方法你可以直接当前处理的节点中的属性。
 * <p>
 * endElement方法
 * 这是处理每个节点所触发的第三个方法，遇到一个节点的结束标签时，将会出发这个方法，并且会传递结束标签的名称
 * <p>
 * endDocument方法
 * 如果当前的XML文档处理完毕后，将会触发该方法，在此方法内你可以将最终的结果保存并且销毁不需要使用的变量。
 */

public class SAX2ChannelParserHandler extends DefaultHandler {
    private final String TAG = "SAX2";
    private List<Channel> list;//解析数据存储
    Channel channel;
    int currentState = 0;//由于在<item></item>标签中有字符，需要做标记

    public List<Channel> getList() {
        return list;
    }

    /**
     * (1)文档开始
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new ArrayList<Channel>();
        Log.d(TAG, "startDocument: ");
    }

    /**
     * (2)标签开始
     * 以channel.xml为例：
     * 标签顺序是
     * <p>
     * <channel>
     * <item
     * id=""
     * url="">百度
     * </item>
     * </channel>
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.d(TAG, "startElement: ");
        channel = new Channel();
        if (localName.equals("item")) {

            //遍历解析item标签
            for (int i = 0; i < attributes.getLength(); i++) {

                //item标签保存参数 id url （name的处理在charaters方法中）
                if (attributes.getLocalName(i).equals("id")) {
                    channel.setId(attributes.getValue(i));
                } else if (attributes.getLocalName(i).equals("url")) {
                    channel.setUrl(attributes.getValue(i));
                }
            }

            currentState = -1;
            return;
        }
        currentState = 0;
        return;
    }

    /**
     * （3）接口字符
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        Log.d(TAG, "characters: ");
        String str = new String(ch, start, length);//读取字符
        //item标签保存参数 name
        if (currentState != 0) {
            channel.setName(str);
            currentState = 0;
        }
        return;
    }

    /**
     * (4)标签结束
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d(TAG, "endElement: ");

        //标签结束后，将信息保存到list中
        if (localName.equals("item")) {
            list.add(channel);

        }

    }

    /**
     * (5)文档结束
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d(TAG, "endDocument: ");

    }
}
