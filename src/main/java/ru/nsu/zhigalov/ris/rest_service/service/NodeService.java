package ru.nsu.zhigalov.ris.rest_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;

import java.util.List;
import java.util.Optional;

public interface NodeService {
    List<Node> findNodesInArea(Area area);
    Optional<Node> findNodeById(Long id);
    void deleteNodeById(Long id);
    void putNode(Node node);
    Page<Node> findAll(Pageable pageRequest);
    long getNodeCount();
}