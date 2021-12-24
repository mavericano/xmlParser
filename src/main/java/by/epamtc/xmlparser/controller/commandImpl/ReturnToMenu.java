package by.epamtc.xmlparser.controller.commandImpl;

import by.epamtc.xmlparser.controller.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReturnToMenu implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("viewer.jsp");
    }
}
