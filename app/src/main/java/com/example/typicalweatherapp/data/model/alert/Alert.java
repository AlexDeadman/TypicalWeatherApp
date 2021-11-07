package com.example.typicalweatherapp.data.model.alert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Alert {

    @SerializedName("sender_name")
    @Expose
    private String senderName;

    @SerializedName("event")
    @Expose
    private String event;

    @SerializedName("start")
    @Expose
    private Integer start;

    @SerializedName("end")
    @Expose
    private Integer end;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    public Alert(
            String senderName,
            String event,
            Integer start,
            Integer end,
            String description,
            List<String> tags
    ) {
        this.senderName = senderName;
        this.event = event;
        this.start = start;
        this.end = end;
        this.description = description;
        this.tags = tags;
    }

    public Alert() {

    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
