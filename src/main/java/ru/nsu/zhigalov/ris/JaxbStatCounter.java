package ru.nsu.zhigalov.ris;

import org.openstreetmap.osm._0.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;

public class JaxbStatCounter extends StatCounter {
    protected Unmarshaller unmarshaller;

    @Override
    public Statistics countStat(InputStream inputStream) throws XMLStreamException, JAXBException {
        reader = getReader(inputStream);
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        return super.countStat(inputStream);
    }

    @Override
    protected void countNode() throws XMLStreamException, JAXBException {
        Node node = (Node) unmarshaller.unmarshal(reader, Node.class).getValue();
        statistics.addUserEdit(node.getUser(), String.valueOf(node.getChangeset()));
        node.getTag().forEach(tag -> statistics.addKeyNodeAmount(tag.getK()));
    }
}
