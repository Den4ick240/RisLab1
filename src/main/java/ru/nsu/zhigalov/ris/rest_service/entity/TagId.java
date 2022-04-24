package ru.nsu.zhigalov.ris.rest_service.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class TagId implements Serializable {
    private Long nodeId;
    private String k;
}
