package com.xml.sax2;

/**
 * asset文件中对应channel.xml的实体类
 *
 */

public class Channel {
    private String id;
    private String url;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Channel{" + "id='" + id + '\'' + ", url='" + url + '\'' + ", name='" + name + '\'' + '}'+"$$ ";
    }
}
