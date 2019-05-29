package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipFilterRequest;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
public class ShipService implements IShipService {
    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public boolean isIdValid(Long id) {
        boolean notNumber = false;
        try {
            Long.parseLong(id.toString());
        } catch (NumberFormatException e) {
            notNumber = true;
        }

        return id > 0 && !notNumber;
    }

    @Override
    public boolean isValid(Ship ship) {
        boolean valid = true;
        if (ship.getName() == null || ship.getPlanet() == null) {
            valid = false;
        } else {
            if (ship.getName().length() > 50 || ship.getPlanet().length() > 50) valid = false;
            if (ship.getName().equals("") || ship.getPlanet().equals("")) valid = false;
        }

        Calendar calendarBefore = new GregorianCalendar();
        Calendar calendarAfter = new GregorianCalendar();
        calendarBefore.set(2800, Calendar.JANUARY, 1);
        calendarAfter.set(3019, Calendar.JANUARY, 1);
        if (ship.getProdDate() == null) {
            valid = false;
        } else {
            if (ship.getProdDate().getTime() < 0) valid = false;
            if (ship.getProdDate().after(calendarAfter.getTime())
                    || ship.getProdDate().before(calendarBefore.getTime())) valid = false;
        }

        if (ship.getSpeed() == null) {
            valid = false;
        } else {
            if (ship.getSpeed() > 0.99 || ship.getSpeed() < 0.01) {
                valid = false;
            }
        }

        if (ship.getCrewSize() == null) {
            valid = false;
        } else {
            if (ship.getCrewSize() > 9999 || ship.getCrewSize() < 1) valid = false;
        }

        return valid;
    }

    @Override
    @Transactional
    public boolean add(Ship ship) {
        ship.computeRating();
        Ship savedShip = shipRepository.save(ship);
        return savedShip.getId() != null;
    }

    @Override
    @Transactional
    public List<Ship> getList(ShipFilterRequest request) {
        Date afterDate;
        Date beforeDate;
        if (request.getAfter() != null) {
            afterDate = new Date(request.getAfter());
        } else {
            afterDate = new Date(new java.util.Date().getTime());
        }
        if (request.getBefore() != null) {
            beforeDate = new Date(request.getBefore());
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.YEAR, 1500);
            beforeDate = new Date(calendar.getTimeInMillis());
        }

        String name;
        if (request.getName() != null) {
            name = "%" + request.getName() + "%";
        } else {
            name = "%";
        }

        String planet;
        if (request.getPlanet() != null) {
            planet = "%" + request.getPlanet() + "%";
        } else {
            planet = "%";
        }

        Double minSpeed;
        if (request.getMinSpeed() != null) {
            minSpeed = request.getMinSpeed();
        } else {
            minSpeed = -1.0;
        }

        Double maxSpeed;
        if (request.getMaxSpeed() != null) {
            maxSpeed = request.getMaxSpeed();
        } else {
            maxSpeed = 2.0;
        }

        Integer minCrewSize;
        if (request.getMinCrewSize() != null) {
            minCrewSize = request.getMinCrewSize();
        } else {
            minCrewSize = -1;
        }

        Integer maxCrewSize;
        if (request.getMaxCrewSize() != null) {
            maxCrewSize = request.getMaxCrewSize();
        } else {
            maxCrewSize = 20000;
        }

        Double minRating;
        if (request.getMinRating() != null) {
            minRating = request.getMinRating();
        } else {
            minRating = -1.0;
        }

        Double maxRating;
        if (request.getMaxRating() != null) {
            maxRating = request.getMaxRating();
        } else {
            maxRating = 100.0;
        }

        List<Ship> result;
        if (request.isUsed() != null & request.getShipType() != null) {
            result = shipRepository.findFilteredShipsWithPagination(
                    name, planet, request.getShipType(), afterDate,
                    beforeDate, request.isUsed(), minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating,
                    PageRequest.of(request.getPageNumber(), request.getPageSize(),
                            Sort.by(request.getOrder().getFieldName())));
        } else if (request.isUsed() == null & request.getShipType() != null) {
            result = shipRepository.findAnyUsedFilteredShipsWithPagination(
                    name, planet, request.getShipType(), afterDate,
                    beforeDate, minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating,
                    PageRequest.of(request.getPageNumber(), request.getPageSize(),
                            Sort.by(request.getOrder().getFieldName())));
        } else if (request.isUsed() != null & request.getShipType() == null) {
            result = shipRepository.findAnyTypeFilteredShipsWithPagination(
                    name, planet, afterDate,
                    beforeDate, request.isUsed(), minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating,
                    PageRequest.of(request.getPageNumber(), request.getPageSize(),
                            Sort.by(request.getOrder().getFieldName())));
        } else {
            result = shipRepository.findAnyUsedAnyTypeFilteredShipsWithPagination(
                    name, planet, afterDate,
                    beforeDate, minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating,
                    PageRequest.of(request.getPageNumber(), request.getPageSize(),
                            Sort.by(request.getOrder().getFieldName())));
        }

        return result;
    }

    @Override
    @Transactional
    public Long getCount(ShipFilterRequest request) {
        Date afterDate;
        Date beforeDate;
        if (request.getAfter() != null) {
            afterDate = new Date(request.getAfter());
        } else {
            afterDate = new Date(new java.util.Date().getTime());
        }
        if (request.getBefore() != null) {
            beforeDate = new Date(request.getBefore());
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.YEAR, 1500);
            beforeDate = new Date(calendar.getTimeInMillis());
        }

        String name;
        if (request.getName() != null) {
            name = "%" + request.getName() + "%";
        } else {
            name = "%";
        }

        String planet;
        if (request.getPlanet() != null) {
            planet = "%" + request.getPlanet() + "%";
        } else {
            planet = "%";
        }

        Double minSpeed;
        if (request.getMinSpeed() != null) {
            minSpeed = request.getMinSpeed();
        } else {
            minSpeed = -1.0;
        }

        Double maxSpeed;
        if (request.getMaxSpeed() != null) {
            maxSpeed = request.getMaxSpeed();
        } else {
            maxSpeed = 2.0;
        }

        Integer minCrewSize;
        if (request.getMinCrewSize() != null) {
            minCrewSize = request.getMinCrewSize();
        } else {
            minCrewSize = -1;
        }

        Integer maxCrewSize;
        if (request.getMaxCrewSize() != null) {
            maxCrewSize = request.getMaxCrewSize();
        } else {
            maxCrewSize = 20000;
        }

        Double minRating;
        if (request.getMinRating() != null) {
            minRating = request.getMinRating();
        } else {
            minRating = -1.0;
        }

        Double maxRating;
        if (request.getMaxRating() != null) {
            maxRating = request.getMaxRating();
        } else {
            maxRating = 100.0;
        }

        Long result;
        if (request.isUsed() != null & request.getShipType() != null) {
            result = shipRepository.getFilteredShipsCount(
                    name, planet, request.getShipType(), afterDate,
                    beforeDate, request.isUsed(), minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating);
        } else if (request.isUsed() == null & request.getShipType() != null) {
            result = shipRepository.getAnyUsedFilteredShipsCount(
                    name, planet, request.getShipType(), afterDate,
                    beforeDate, minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating);
        } else if (request.isUsed() != null & request.getShipType() == null) {
            result = shipRepository.getAnyTypeFilteredShipsCount(
                    name, planet, afterDate,
                    beforeDate, request.isUsed(), minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating);
        } else {
            result = shipRepository.getAnyUsedAnyTypeFilteredShipsCount(
                    name, planet, afterDate,
                    beforeDate, minSpeed,
                    maxSpeed, minCrewSize, maxCrewSize,
                    minRating, maxRating);
        }

        return result;

    }

    @Override
    @Transactional
    public Ship getById(Long id) {
        Ship ship = null;
        Optional<Ship> optional = shipRepository.findById(id);
        if (optional.isPresent()) {
            ship = optional.get();
        }
        return ship;
    }

    @Override
    @Transactional
    public Ship update(Ship shipToUpdate, Ship shipParameters) {
        if (shipToUpdate == null) return null;
        if (shipParameters == null) return shipRepository.save(shipToUpdate);
        Ship testShip = new Ship(shipToUpdate);

        if (shipParameters.getName() != null) testShip.setName(shipParameters.getName());
        if (shipParameters.getPlanet() != null) testShip.setPlanet(shipParameters.getPlanet());
        if (shipParameters.getShipType() != null) testShip.setShipType(shipParameters.getShipType());
        if (shipParameters.getProdDate() != null) testShip.setProdDate(shipParameters.getProdDate());
        if (shipParameters.isUsed() != null) testShip.setUsed(shipParameters.isUsed());
        if (shipParameters.getSpeed() != null) testShip.setSpeed(shipParameters.getSpeed());
        if (shipParameters.getCrewSize() != null) testShip.setCrewSize(shipParameters.getCrewSize());

        if (isValid(testShip)) {
            shipToUpdate = testShip;
        } else {
            return null;
        }

        shipToUpdate.computeRating();

        return shipRepository.save(shipToUpdate);
    }

    @Override
    @Transactional
    public Ship delete(Long id) {
        Ship ship;
        if (id > shipRepository.getBiggestId()) {
            ship = null;
        } else {
            ship = shipRepository.getOne(id);
            shipRepository.deleteById(id);
        }

        return ship;
    }
}
