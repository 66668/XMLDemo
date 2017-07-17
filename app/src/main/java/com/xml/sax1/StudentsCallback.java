package com.xml.sax1;

import com.xml.Students;

import java.util.List;

/**
 * Created by wuzp on 2017/6/9.
 */
public interface StudentsCallback {
    void parseCallback(List<Students> date);
}
