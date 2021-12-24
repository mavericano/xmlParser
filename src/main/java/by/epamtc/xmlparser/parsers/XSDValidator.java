package by.epamtc.xmlparser.parsers;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator {
    public static boolean isValid(String filePath, String schemaPath) {
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaLocation = new File(schemaPath);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filePath);
            validator.validate(source);
            return true;
        } catch (IOException | SAXException e) {
            return false;
        }
    }
}
