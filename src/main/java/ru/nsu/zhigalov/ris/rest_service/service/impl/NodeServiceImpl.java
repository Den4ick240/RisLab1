package ru.nsu.zhigalov.ris.rest_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.dto.NodeDTO;
import ru.nsu.zhigalov.ris.rest_service.dto.TagDTO;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;
import ru.nsu.zhigalov.ris.rest_service.entity.Tag;
import ru.nsu.zhigalov.ris.rest_service.entity.TagId;
import ru.nsu.zhigalov.ris.rest_service.mapper.NodeMapper;
import ru.nsu.zhigalov.ris.rest_service.repository.NodeRepository;
import ru.nsu.zhigalov.ris.rest_service.repository.TagRepository;
import ru.nsu.zhigalov.ris.rest_service.service.NodeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final TagRepository tagRepository;
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
        Node result = nodeRepository.save(node);

        var ks = nodeDTO.getTags().stream().map(TagDTO::getKey).collect(Collectors.toList());
        tagRepository.deleteByNodeIdAndKNotIn(result.getId(), ks);

        return nodeMapper.entityToDto(result);
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
