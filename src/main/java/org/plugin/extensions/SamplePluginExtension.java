package org.plugin.extensions;

public class SamplePluginExtension {
    private String token;
    private long chatId;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
    public long getChatId(){
        return chatId;
    }

    public void setChatId(long chatId){
        this.chatId=chatId;
    }
}
