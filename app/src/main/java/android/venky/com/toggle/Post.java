package android.venky.com.toggle;

import java.util.Date;

public class Post {
    String uid,state;
    Date timestamp;
    long id;
    public Post() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public Post(String u,Date d,String s,long Id) {
        uid=u;
        state=s;
        timestamp=d;
        id=Id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getid(){return id;}

    public void setid(long Id){this.id=Id;}


}
