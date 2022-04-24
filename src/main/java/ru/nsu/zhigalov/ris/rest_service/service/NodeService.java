package ru.nsu.zhigalov.ris.rest_service.service;

import org.springframework.data.domain.Pageable;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.dto.NodeDTO;

import java.util.List;
import java.util.Optional;

public interface NodeService {
    List<NodeDTO> findNodesInArea(Area area);
    Optional<NodeDTO> findNodeById(Long id);
    void deleteNodeById(Long id);
    NodeDTO putNode(NodeDTO node);
    List<NodeDTO> findAll(Pageable pageRequest);
    long getNodeCount();
}