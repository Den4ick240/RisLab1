package ru.nsu.zhigalov.ris;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

public class StatCounter {

    private static final int USER_ATTRIBUTE = 4;
    protected Statistics statistics;
    protected XMLStreamReader reader;
    private static final String NODE = "node";
    private static final String TAG = "tag";

    public static XMLStreamReader getReader(InputStream inputStream) throws XMLStreamException {
        return XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
    }

    public Statistics countStat(InputStream inputStream) throws XMLStreamException, JAXBException, SQLException {
        statistics = new Statistics(new HashMap<>(), new HashMap<>());

        reader = getReader(inputStream);
        while (reader.hasNext()) {
            countEvent(reader.next());
        }

        return statistics;
    }

    protected void countEvent(int eventType) throws XMLStreamException, JAXBException, SQLException {
        if (eventType != START_ELEMENT) return;

        String localName = reader.getLocalName();
        if (!localName.equals(NODE)) return;
        countNode();
    }

    protected void countNode() throws XMLStreamException, JAXBException, SQLException {
        String username = reader.getAttributeValue("", "user");
        String changeset = reader.getAttributeValue("", "changeset");
        statistics.addUserEdit(username, changeset);

        while (reader.hasNext()) {
            var eventType = reader.next();
            if (eventType != END_ELEMENT && eventType != START_ELEMENT) continue;
            String localName = reader.getLocalName();
            if (eventType == END_ELEMENT && localName.equals(NODE))
                return;
            if (eventType == START_ELEMENT && localName.equals(TAG))
                countTag();
        }
    }

    protected void countTag() {
        String k = reader.getAttributeValue("", "k");
        statistics.addKeyNodeAmount(k);
    }
}
