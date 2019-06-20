package com.adweb.adweb.service.Tl;

public class MessageSend {
    private UserInfo userInfo;
    private int reqType=0;
    private Perception perception;
    public MessageSend(String inputText,String openId){
        this.userInfo=new UserInfo(openId);
        this.perception=new Perception(inputText);
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public int getReqType() {
        return reqType;
    }

    public Perception getPerception() {
        return perception;
    }
}
