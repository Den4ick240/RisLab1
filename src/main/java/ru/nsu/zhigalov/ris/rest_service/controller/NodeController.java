package ru.nsu.zhigalov.ris.rest_service.controller;

import com.sun.istack.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.dto.NodeDTO;
import ru.nsu.zhigalov.ris.rest_service.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("api/node")
class NodeController {
    private final NodeService nodeService;

    NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("in_area_body")
    public List<NodeDTO> getNodesInArea(@NotNull @RequestBody Area area) {
        return nodeService.findNodesInArea(area);
    }

    @GetMapping("in_area")
    public List<NodeDTO> getInAreaParams(Area area ) {
        return nodeService.findNodesInArea(area);
    }

    @GetMapping
    public List<NodeDTO> getNodes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        return nodeService.findAll(pageRequest);
    }

    @GetMapping("{id}")
    public Object getNodeById(@PathVariable("id") Long id) {
        var res = nodeService.findNodeById(id);
        if (res.isPresent()) return res.get();
        return "No such id";
    }

    @GetMapping("count")
    public long getNodeCount() {
        return nodeService.getNodeCount();
    }

    @DeleteMapping("{id}")
    public void deleteNode(@PathVariable("id") Long id) {
        nodeService.deleteNodeById(id);
    }

    @PutMapping
    public NodeDTO putNode(@NotNull @RequestBody NodeDTO nodeDTO) {
        return nodeService.putNode(nodeDTO);
    }

}