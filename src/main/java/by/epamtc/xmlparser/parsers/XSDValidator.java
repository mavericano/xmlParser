package by.epamtc.xmlparser.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.http.Part;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {
    private static final Logger logger = LogManager.getLogger(XSDValidator.class);
    public static boolean isValid(Part filePart, String schemaPath) {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaLocation = new File(schemaPath);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filePart.getInputStream());
            validator.validate(source);
            return true;
        } catch (IOException | SAXException e) {
            logger.error(e.getClass().getSimpleName() + " while validating xml [CONTROLLED]");
            return false;
        }
    }
}
