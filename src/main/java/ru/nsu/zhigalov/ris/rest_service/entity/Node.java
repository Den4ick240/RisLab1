package ru.nsu.zhigalov.ris.rest_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nodes")
public class Node {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column
    private String usr;

    @Column
    private Long uid;

    @Column
    private Boolean visible;

    @Column
    private Integer version;

    @Column
    private Long changeset;

    @Column
    private Date timestamp;

    @OneToMany(mappedBy = "nodeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Tag> tags;
}
