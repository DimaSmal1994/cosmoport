package com.space.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ship")
public class Ship {
    private Long id;
    private String name;
    private String planet;
    private ShipType shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship(Ship ship) {
        this.id = ship.getId();
        this.name = ship.getName();
        this.planet = ship.getPlanet();
        this.shipType = ship.getShipType();
        this.prodDate = ship.getProdDate();
        this.isUsed = ship.isUsed();
        this.speed = ship.getSpeed();
        this.crewSize = ship.getCrewSize();
        this.rating = ship.getRating();
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
    }

    public Ship() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "planet")
    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    @Column(name = "prodDate")
    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    @Column(name = "isUsed")
    public Boolean isUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    @Column(name = "speed")
    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Column(name = "crewSize")
    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    @Column(name = "rating")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double computeRating() {
        Double rating;
        double coefficient = isUsed ? 0.5 : 1.0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        rating = (80 * speed * coefficient) / (3019 - calendar.get(Calendar.YEAR) + 1);

        double roundedRating = Math.round(rating*100) / 100.0;
        setRating(roundedRating);

        return roundedRating;
    }
}
