package com.example.growthmaster.db;

public class Resource {

    private int id;

    private String title;

    private String author;

    private String publishTime;

    private String content;

    private int imageId;

    public Resource(String title, String author, String publishTime, int imageId) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.imageId = imageId;
        this.content = "缓解青少年心理压力有以下几个方法：\n" +
                "1、首先要保持好的心态，积极面对难题，正确认识自己：对自己的身体素质、知识才能、社会适应力等要有自知之明，尽量避免做一些力所不及的事情，引起不必要的压力。\n" +
                "2、如果压力太大，可以学会自我调节，加强自身修养。以适当方式宣泄自己内心的不快和抑郁，以解除心理压抑和精神紧张。善于自我调节，有张有弛。\n" +
                "3、具体的可以做感兴趣的事，如看电影、旅游、聊天，听音乐，释放压力。或是找个没人的地方大声地喊叫。做一些运动。\n" +
                "4、平时多听音乐，让优美的乐曲来化解精神的疲惫。轻快、舒畅的音乐能使人的精神得到有效放松。\n" +
                "5、开怀大笑是消除精神压力的最佳方法，可以忘掉忧虑。\n" +
                "6、有意识的放慢生活节奏，冷静地处理各种纷繁复杂的事情，即使做错了事，也不要责备自已，这有利于人的心理平衡，同时也有助于舒缓人的精神压力。";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
