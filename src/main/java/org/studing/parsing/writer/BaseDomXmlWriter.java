package org.studing.parsing.writer;

import lombok.NonNull;
import lombok.val;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import static javax.xml.transform.OutputKeys.INDENT;
import static javax.xml.transform.TransformerFactory.newInstance;

public class BaseDomXmlWriter {
    protected void transform(
        @NonNull final Document document,
        @NonNull final String filePath) throws TransformerException {

        val transformer = newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new File(filePath)));
    }
}
