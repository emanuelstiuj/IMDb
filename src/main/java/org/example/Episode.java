package org.example;

public class Episode implements Cloneable{
    private String episodeName;
    private String duration;

    public String getEpisodeName() {
        return episodeName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
