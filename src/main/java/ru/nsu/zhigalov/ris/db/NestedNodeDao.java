package ru.nsu.zhigalov.ris.db;


import generated.Node;

import java.sql.SQLException;

public class NestedNodeDao implements Dao<Node> {
    private final Dao<TagEntity> tagDao;
    private final Dao<Node> nodeDao;

    public NestedNodeDao(Dao<Node> nodeDao, Dao<TagEntity> tagDao) {
        this.tagDao = tagDao;
        this.nodeDao = nodeDao;
    }

    @Override
    public void insert(Node obj) throws SQLException {
        nodeDao.insert(obj);
        for (var tag : obj.getTag())
            tagDao.insert(new TagEntity(obj.getId(), tag.getK(), tag.getV()));
    }

    @Override
    public void commit() throws SQLException {
        nodeDao.commit();
        tagDao.commit();
    }
}
