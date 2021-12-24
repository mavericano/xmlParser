package by.epamtc.xmlparser.controller;

import by.epamtc.xmlparser.bean.Device;
import by.epamtc.xmlparser.parsers.Parser;
import by.epamtc.xmlparser.parsers.impl.ParserDOM;
import by.epamtc.xmlparser.parsers.ParserException;
import by.epamtc.xmlparser.parsers.impl.ParserSAX;
import by.epamtc.xmlparser.parsers.impl.ParserStAX;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class Controller extends HttpServlet {

    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        // checks if the request actually contains upload file

        // redirects client to message page
        //getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandProvider.getInstance().provideCommand(request.getParameter("command")).execute(request, response);
//        try {
//            Parser parser = new ParserSAX();
//            Parser parser2 = new ParserDOM();
//            Parser parser3 = new ParserStAX();
//            List<Device> a = parser3.parse("../webapps/xmlParser_war/resources/devices.xml");
//            request.setAttribute("devices", a);
//            request.getRequestDispatcher("viewer.jsp").forward(request, response);
//        } catch (ParserException | ServletException ignored) {
//            ignored.printStackTrace();
//        }
    }

    public void destroy() {
    }
}