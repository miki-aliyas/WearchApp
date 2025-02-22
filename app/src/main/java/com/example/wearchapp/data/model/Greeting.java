package com.example.wearchapp.data.model;
//挨拶に関する情報（IDと内容）を保持
public class Greeting {
    private long id;
    private String content; //  挨拶の内容（テキスト）を保持するための変数

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
