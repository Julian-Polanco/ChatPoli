package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SendData implements Serializable{

    private String ipAddres,user,message;
    private boolean Connected;

    
    private Set<String> ipList = new HashSet<>();

    public Set<String> getIpList() {
        return ipList;
    }

    public void setIpList(Set<String> ipList) {
        this.ipList = ipList;
    }
    
    
    public boolean isConnected() {
        return Connected;
    }

    public void setConnected(boolean Connected) {
        this.Connected = Connected;
    }

    public String getIpAddres() {
        return ipAddres;
    }

    public void setIpAddres(String ipAddres) {
        this.ipAddres = ipAddres;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
