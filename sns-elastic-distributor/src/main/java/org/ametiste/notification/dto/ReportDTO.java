package org.ametiste.notification.dto;

import java.util.Map;

/**
 * Created by atlantis on 5/29/15.
 */
public class ReportDTO {

    private String id;
    private long date;
    private String type;
    private String sender;
    private Map<String, Object> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {

        return date;
    }

    public void setDate( long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }


}
