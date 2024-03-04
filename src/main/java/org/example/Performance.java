package org.example;

public class Performance implements Cloneable{
    private String title;
    private String type;

    public Performance() {}

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
