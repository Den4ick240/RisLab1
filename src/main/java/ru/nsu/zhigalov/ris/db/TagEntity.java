package ru.nsu.zhigalov.ris.db;

import generated.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class TagEntity {
    private final BigInteger nodeId;
    private final String k;
    private final String v;

    public TagEntity(BigInteger id, Tag tag) {
        nodeId = id;
        k = tag.getK();
        v = tag.getV();
    }
}
