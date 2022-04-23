package ru.nsu.zhigalov.ris.db;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TagEntity {
    private final BigInteger nodeId;
    private final String k;
    private final String v;

//    public TagEntity(BigInteger nodeId, String k, String v) {
//        this.nodeId = nodeId;
//        this.k = k;
//        this.v = v;
//    }
}
