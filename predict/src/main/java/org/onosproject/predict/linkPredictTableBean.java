package org.onosproject.predict;

public class linkPredictTableBean {
    private String time;
    private String linkId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLinkLoad() {
        return linkLoad;
    }

    public void setLinkLoad(String linkLoad) {
        this.linkLoad = linkLoad;
    }

    private String linkLoad;


    public String getRealLinkLoad() {
        return realLinkLoad;
    }

    public void setRealLinkLoad(String realLinkLoad) {
        this.realLinkLoad = realLinkLoad;
    }

    private String realLinkLoad;
}
