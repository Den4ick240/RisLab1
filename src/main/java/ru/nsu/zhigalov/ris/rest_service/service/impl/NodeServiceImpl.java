package ru.nsu.zhigalov.ris.rest_service.service.impl;

import org.springframework.stereotype.Service;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;
import ru.nsu.zhigalov.ris.rest_service.repository.NodeRepository;
import ru.nsu.zhigalov.ris.rest_service.service.NodeService;

import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;

    public NodeServiceImpl(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public List<Node> findNodesInArea(Area area) {
        return nodeRepository.findNodesInRangeOrderByDistanceAsc(area.getLat(), area.getLon(), area.getRad());
    }
}
