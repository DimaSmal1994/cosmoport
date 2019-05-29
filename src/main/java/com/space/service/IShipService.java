package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipFilterRequest;

import java.util.List;

public interface IShipService {
    boolean isValid(Ship ship);

    boolean isIdValid (Long id);

    boolean add(Ship ship);

    List<Ship> getList(ShipFilterRequest request);

    Long getCount(ShipFilterRequest request);

    Ship delete(Long id);

    Ship getById(Long id);

    Ship update(Ship shipToUpdate, Ship shipParameters);
}
