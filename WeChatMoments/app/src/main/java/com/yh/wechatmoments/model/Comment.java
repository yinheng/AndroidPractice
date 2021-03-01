package com.yh.wechatmoments.model;

public class Comment {
    private String content;
    private Sender sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", sender=" + sender +
                '}';
    }
}
