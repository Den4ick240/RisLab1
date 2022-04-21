package ru.nsu.zhigalov.ris;

import generated.Node;
import ru.nsu.zhigalov.ris.db.Dao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.sql.SQLException;

public class JaxbStatCounter extends StatCounter {
    protected Unmarshaller unmarshaller;
    private final Dao<Node> nodeDao;

    public JaxbStatCounter(Dao<Node> nodeDao) {
        this.nodeDao = nodeDao;
    }

    @Override
    public Statistics countStat(InputStream inputStream) throws XMLStreamException, JAXBException, SQLException {
        reader = getReader(inputStream);
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        return super.countStat(inputStream);
    }

    @Override
    protected void countNode() throws JAXBException, SQLException {
        Node node = (Node) unmarshaller.unmarshal(reader);
        statistics.addUserEdit(node.getUser(), String.valueOf(node.getChangeset()));
        node.getTag().forEach(tag -> statistics.addKeyNodeAmount(tag.getK()));
        nodeDao.insert(node);
    }
}
