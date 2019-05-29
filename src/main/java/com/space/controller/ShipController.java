package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipFilterRequest;
import com.space.model.ShipType;
import com.space.service.IShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class ShipController {

    private IShipService shipService;

    @Autowired
    public void setShipService(IShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "/rest/ships")
    public @ResponseBody
    List<Ship> getAllShips(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating,
            @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        ShipFilterRequest filterRequest = new ShipFilterRequest(
                name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                minRating, maxRating, order, pageNumber, pageSize);

        return shipService.getList(filterRequest);
    }

    @GetMapping(value = "/rest/ships/count")
    public @ResponseBody
    Long getShipsCount(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating
    ) {

        return shipService.getCount(new ShipFilterRequest(
                name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                minRating, maxRating, ShipOrder.ID, 0, 3));
    }

    @PostMapping(value = "/rest/ships")
    public @ResponseBody
    Ship createShip(@RequestBody Ship ship) {
        Ship result;
        if (ship == null || !shipService.isValid(ship)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            Ship shipToAdd = new Ship(ship);
            if (shipToAdd.isUsed() == null) shipToAdd.setUsed(Boolean.FALSE);
            shipService.add(shipToAdd);
            result = shipToAdd;
        }
        return result;
    }

    @GetMapping(value = "/rest/ships/{id}")
    public @ResponseBody
    Ship getShipById(@PathVariable Long id) {
        Ship shipToReturn;
        if (!shipService.isIdValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            shipToReturn = shipService.getById(id);

            if (shipToReturn == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }

        return shipToReturn;
    }

    @PostMapping(value = "/rest/ships/{id}")
    public @ResponseBody
    Ship updateShip(@PathVariable Long id, @RequestBody Ship shipParameters) {
        Ship updatedShip;
        if (!shipService.isIdValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            Ship shipToUpdate = shipService.getById(id);
            if (shipToUpdate == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                updatedShip = shipService.update(shipToUpdate, shipParameters);
                if (updatedShip == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
        }

        return updatedShip;
    }

    @DeleteMapping(value = "/rest/ships/{id}")
    public ResponseEntity deleteShip(@PathVariable Long id) {
        if (!shipService.isIdValid(id)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            Ship shipToDelete = shipService.delete(id);
            if (shipToDelete == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(HttpStatus.OK);
            }
        }

    }
}
