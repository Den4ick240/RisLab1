package ru.nsu.zhigalov.ris.rest_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
@IdClass(TagId.class)
public class Tag implements Serializable {
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "jobDetailId")
//    private JobDetail jobDetail;
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "node_id")
//    private Node node;

    @Id
    @Column(nullable = false)
    @JoinTable(name = "nodes")
    private Long nodeId;

    @Id
    @Column
    private String k;

    @Column
    private String v;
}
