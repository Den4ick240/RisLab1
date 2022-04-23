package ru.nsu.zhigalov.ris.rest_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nsu.zhigalov.ris.rest_service.dto.Area;
import ru.nsu.zhigalov.ris.rest_service.entity.Node;
import ru.nsu.zhigalov.ris.rest_service.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("api/node")
class NodeController {
    private final NodeService nodeService;

    NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("in_area")
    public List<Node> getNodesInArea(@RequestBody Area area) {
        return nodeService.findNodesInArea(area);
    }

    @GetMapping
    public List<Node> getNodes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        var result = nodeService.findAll(pageRequest);
        return result.getContent();
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
    public void putNode(@RequestBody Node node) {
        nodeService.putNode(node);
    }
}