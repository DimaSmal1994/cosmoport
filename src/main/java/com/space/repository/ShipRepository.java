package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {
    @Query(value = "select s from Ship s where s.name like :name and s.planet like :planet " +
            "and s.shipType = :shipType and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.used = :isUsed and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    List<Ship> findFilteredShipsWithPagination(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("shipType") ShipType shipType,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("isUsed") Boolean isUsed,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable pageable);

    @Query(value = "select s from Ship s where s.name like :name and s.planet like :planet " +
            "and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.used = :isUsed and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    List<Ship> findAnyTypeFilteredShipsWithPagination(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("isUsed") Boolean isUsed,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable pageable);

    @Query(value = "select s from Ship s where s.name like :name and s.planet like :planet " +
            "and s.shipType = :shipType and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    List<Ship> findAnyUsedFilteredShipsWithPagination(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("shipType") ShipType shipType,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable pageable);

    @Query(value = "select s from Ship s where s.name like :name and s.planet like :planet " +
            "and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    List<Ship> findAnyUsedAnyTypeFilteredShipsWithPagination(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            Pageable pageable);

    @Query(value = "select count (s) from Ship s where s.name like :name and s.planet like :planet " +
            "and s.shipType = :shipType and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.used = :isUsed and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    long getFilteredShipsCount(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("shipType") ShipType shipType,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("isUsed") Boolean isUsed,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating);

    @Query(value = "select count (s) from Ship s where s.name like :name and s.planet like :planet " +
            "and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.used = :isUsed and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    long getAnyTypeFilteredShipsCount(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("isUsed") Boolean isUsed,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating);

    @Query(value = "select count (s) from Ship s where s.name like :name and s.planet like :planet " +
            "and s.shipType = :shipType and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    long getAnyUsedFilteredShipsCount(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("shipType") ShipType shipType,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating);

    @Query(value = "select count (s) from Ship s where s.name like :name and s.planet like :planet " +
            "and s.prodDate >= :after and s.prodDate <= :before " +
            "and s.speed <= :maxSpeed and s.speed >= :minSpeed " +
            "and s.crewSize >= :minCrewSize and s.crewSize <= :maxCrewSize and s.rating <= :maxRating " +
            "and s.rating >= :minRating"
    )
    long getAnyUsedAnyTypeFilteredShipsCount(
            @Param("name") String name,
            @Param("planet") String planet,
            @Param("after") Date after,
            @Param("before") Date before,
            @Param("minSpeed") Double minSpeed,
            @Param("maxSpeed") Double maxSpeed,
            @Param("minCrewSize") Integer minCrewSize,
            @Param("maxCrewSize") Integer maxCrewSize,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating);

    @Query(value = "select max(s.id) from Ship s")
    long getBiggestId();
}
