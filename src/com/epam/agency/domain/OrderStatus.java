package com.epam.agency.domain;

/**
 * Created by Никита on 28.01.2016.
 */
public class OrderStatus extends Entity {
    private long statusId;
    private String statusName;

    public OrderStatus() {
    }

    public OrderStatus(long statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
