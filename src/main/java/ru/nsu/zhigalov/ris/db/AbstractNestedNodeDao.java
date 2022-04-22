package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.SQLException;

public abstract class AbstractNestedNodeDao implements Dao<Node>{
    private final Dao<TagEntity> tagEntityDao;

    protected AbstractNestedNodeDao(Dao<TagEntity> tagEntityDao) {
        this.tagEntityDao = tagEntityDao;
    }

    protected abstract void insertNode(Node node) throws SQLException;

    @Override
    public void insert(Node obj) throws SQLException {

        insertNode(obj);
        for (var tag : obj.getTag())
            tagEntityDao.insert(new TagEntity(obj.getId(), tag.getK(), tag.getV()));

    }
}
