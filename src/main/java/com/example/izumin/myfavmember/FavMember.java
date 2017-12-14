package com.example.izumin.myfavmember;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by izumin on 2017/11/10.
 */

public class FavMember extends RealmObject {
    @PrimaryKey
    private long id;
    private int num;
    private String name;
    private String pitch;
    private String music;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
