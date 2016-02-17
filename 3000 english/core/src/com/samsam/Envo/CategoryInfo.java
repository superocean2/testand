package com.samsam.Envo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by NghiaTrinh on 9/24/2015.
 */
public class CategoryInfo {
    public String name;
    public Texture texture;
    public Rectangle rectangle;
    public String id;
    public boolean isLoaded;
    public String downloadUrl;

    public CategoryInfo(String name, Texture texture,Rectangle rectangle,String id,boolean isLoaded,String downloadUrl) {
        this.name = name;
        this.texture = texture;
        this.rectangle=rectangle;
        this.id=id;
        this.isLoaded=isLoaded;
        this.downloadUrl=downloadUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}

