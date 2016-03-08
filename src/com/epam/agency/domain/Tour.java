package com.epam.agency.domain;

import java.sql.Date;

/**
 * Created by Никита on 28.01.2016.
 */
public class Tour extends Entity {
    private long tourId;
    private Date begDate;
    private Date endDate;
    private TourType tourType;
    private Resort resort;
    private double cost;
    private String discription;
    private TourOperator tourOperator;
    private int numberOfSeats;
    private int tourStatus;
    private int bit;

    public Tour() {
    }

    public Tour( Date begDate, Date endDate, TourType tourType, Resort resort, double cost,
                 String discription, TourOperator tourOperator, int numberOfSeats, int tourStatus) {
        this.begDate = begDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.cost = cost;
        this.discription = discription;
        this.resort = resort;
        this.tourOperator = tourOperator;
        this.numberOfSeats = numberOfSeats;
        this.tourStatus = tourStatus;
    }

    public Tour(long tourId, Date begDate, Date endDate, TourType tourType, Resort resort, double cost,
                String discription, TourOperator tourOperator, int numberOfSeats, int tourStatus) {
        this.tourId = tourId;
        this.begDate = begDate;
        this.endDate = endDate;
        this.tourType = tourType;
        this.cost = cost;
        this.discription = discription;
        this.resort = resort;
        this.tourOperator = tourOperator;
        this.numberOfSeats = numberOfSeats;
        this.tourStatus = tourStatus;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TourType getTourType() {
        return tourType;
    }

    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }

    public TourOperator getTourOperator() {
        return tourOperator;
    }

    public void setTourOperator(TourOperator tourOperator) {
        this.tourOperator = tourOperator;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(int tourStatus) {
        this.tourStatus = tourStatus;
    }
}
