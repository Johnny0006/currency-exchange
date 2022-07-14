import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Document document;

    public Parser(String fileName) {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private boolean containsAttribute(Node node, String attribute) {
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.item(i).getNodeName().equals(attribute))
                return true;
        }
        return false;
    }


    public Map<Currency, BigDecimal> getExchangeRateMap() {
        Map<Currency, BigDecimal> map = new HashMap<>();
        if (document != null) {
            NodeList list = document.getElementsByTagName("Cube");
            for (int i = 0; i < list.getLength(); i++) {
                if (containsAttribute(list.item(i), "currency") && containsAttribute(list.item(i), "rate")) {
                    map.put(Currency.getInstance(list.item(i).getAttributes().getNamedItem("currency").getTextContent()),
                            new BigDecimal(list.item(i).getAttributes().getNamedItem("rate").getTextContent()));
                }
            }
        }
        return map;
    }
}
