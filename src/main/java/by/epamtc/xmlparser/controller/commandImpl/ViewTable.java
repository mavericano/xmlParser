package by.epamtc.xmlparser.controller.commandImpl;

import by.epamtc.xmlparser.bean.Device;
import by.epamtc.xmlparser.controller.Command;
import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.ParserException;
import by.epamtc.xmlparser.parsers.ParserProvider;
import by.epamtc.xmlparser.parsers.XSDValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@MultipartConfig
public class ViewTable implements Command {
    private static final String SCHEMA_PATH = "../webapps/xmlParser_war/resources/devices.xsd";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            //TODO logger
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }

        Part file = request.getPart("file");
        String parserType = request.getParameter("parserType");
        List<Device> devices;

        boolean isValid = XSDValidator.isValid(file, SCHEMA_PATH);
        //TODO REFACTOR TO SOFT CODE
        if (isValid) {
            try {
                devices = ParserProvider.getInstance().provideParser(parserType).parse(file);
                request.setAttribute("devices", devices);
                request.setAttribute("parserType", parserType);
            } catch (ParserException e) {
                e.printStackTrace();
                //TODO logger
            }
        }

        request.setAttribute("isValid", isValid);
        request.getRequestDispatcher("parsedTable.jsp").forward(request, response);
    }
}
