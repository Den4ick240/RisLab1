package ru.nsu.zhigalov.ris.rest_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.dto.NodeDTO;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;
import ru.nsu.zhigalov.ris.rest_service.mapper.NodeMapper;
import ru.nsu.zhigalov.ris.rest_service.repository.NodeRepository;
import ru.nsu.zhigalov.ris.rest_service.service.NodeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final NodeMapper nodeMapper;

    @Override
    public List<NodeDTO> findNodesInArea(Area area) {
        return nodeMapper.entitiesToDtos(
                nodeRepository.findNodesInRangeOrderByDistanceAsc(area.getLat(), area.getLon(), area.getRad()));
    }

    @Override
    public Optional<NodeDTO> findNodeById(Long id) {
        return nodeRepository.findById(id).map(nodeMapper::entityToDto);
    }

    @Override
    public void deleteNodeById(Long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public NodeDTO putNode(NodeDTO nodeDTO) {
        Node node = nodeMapper.dtoToEntity(nodeDTO);
        System.out.println(node);
        return nodeMapper.entityToDto(nodeRepository.save(node));
    }

    @Override
    public List<NodeDTO> findAll(Pageable pageRequest) {
        return nodeMapper.entitiesToDtos(nodeRepository.findAll(pageRequest).getContent());
    }

    @Override
    public long getNodeCount() {
        return nodeRepository.count();
    }

}
