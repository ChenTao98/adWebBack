package com.adweb.adweb.service.Tl;

public class UserInfo {
    private String apiKey="efce599330d045589b1c35521c76aa84";
    private String userId;
    public UserInfo(String userId){
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUserId() {
        return userId;
    }
}
