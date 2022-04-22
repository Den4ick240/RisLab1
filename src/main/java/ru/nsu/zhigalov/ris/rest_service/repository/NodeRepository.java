package ru.nsu.zhigalov.ris.rest_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    @Query(
            value = "SELECT * FROM nodes " +
                    "WHERE earth_box(ll_to_earth(:lat, :lon), :rad) @> ll_to_earth(lat, lon) and " +
                    "earth_distance(ll_to_earth(:lat, :lon), ll_to_earth(nodes.lat, nodes.lon)) < :rad " +
                    "ORDER BY earth_distance(ll_to_earth(:lat, :lon), ll_to_earth(nodes.lat, nodes.lon)) ASC",
            nativeQuery = true
    )
    List<Node> findNodesInRangeOrderByDistanceAsc(
            @Param("lat") Double lat,
            @Param("lon") Double lon,
            @Param("rad") Double rad
    );
}
