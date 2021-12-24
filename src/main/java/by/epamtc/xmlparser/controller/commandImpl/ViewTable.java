package by.epamtc.xmlparser.controller.commandImpl;

import by.epamtc.xmlparser.bean.Device;
import by.epamtc.xmlparser.controller.Command;
import by.epamtc.xmlparser.parsers.ParserException;
import by.epamtc.xmlparser.parsers.ParserProvider;
import by.epamtc.xmlparser.parsers.XSDValidator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

//@MultipartConfig
public class ViewTable implements Command {
    private static final String SCHEMA_PATH = "../webapps/xmlParser_war/resources/devices.xsd";
    private static final Logger logger = LogManager.getLogger(ViewTable.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            logger.error("Form must have multipart/form-data enctype");
            return;
        }

        Part file = request.getPart("file");
        String parserType = request.getParameter("parserType");
        List<Device> devices;

        boolean isValid = XSDValidator.isValid(file, SCHEMA_PATH);

        if (isValid) {
            try {
                devices = ParserProvider.getInstance().provideParser(parserType).parse(file);
                request.setAttribute("devices", devices);
                request.setAttribute("parserType", parserType);
            } catch (ParserException e) {
                logger.error(e.getClass().getSimpleName() + " on calling parser " + parserType);
            }
        }

        request.setAttribute("isValid", isValid);
        request.getRequestDispatcher("parsedTable.jsp").forward(request, response);
    }
}
