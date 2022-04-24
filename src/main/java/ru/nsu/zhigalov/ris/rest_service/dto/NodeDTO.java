package ru.nsu.zhigalov.ris.rest_service.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NodeDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String username;
    private Long uid;
    private Boolean visible;
    private Integer version;
    private Long changeset;
    private Date timestamp;
    private List<TagDTO> tags;
}
