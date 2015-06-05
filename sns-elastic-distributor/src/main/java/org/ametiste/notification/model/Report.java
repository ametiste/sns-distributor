package org.ametiste.notification.model;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by atlantis on 5/29/15.
 */
public class Report implements Serializable {

    private UUID id;
    private long date;
    private String type;
    private String sender;
    private Map<String, Object> content;

    public Report(UUID id, long timestamp, String type, String sender, Map<String, Object> content) {
        this.id = id;

        this.date = timestamp;
        this.type = type;
        this.sender = sender;
        this.content = content;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

   public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public Map<String, Object> getContent() {
        return content;
    }



}
