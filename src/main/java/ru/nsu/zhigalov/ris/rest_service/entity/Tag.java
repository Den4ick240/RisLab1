package ru.nsu.zhigalov.ris.rest_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @Column(nullable = false)
    private Integer nodeId;

    @Id
    @Column
    private String k;

    @Column
    private String v;
}
