package ru.nsu.zhigalov.ris.rest_service.service;

import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;

import java.util.List;

public interface NodeService {
    List<Node> findNodesInArea(Area area);
    List<Node> getAllNodes();
    Node findNodeById(Long id);
    void deleteNodeById(Long id);
    void putNode(Node node);
}