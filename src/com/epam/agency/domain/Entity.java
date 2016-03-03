package com.epam.agency.domain;

import java.io.Serializable;

/**
 * Created by Никита on 28.01.2016.
 */
public abstract class Entity implements Serializable, Cloneable {
    private long id;

    public Entity() {
    }
    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
