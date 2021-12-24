package by.epamtc.xmlparser.tags;

import by.epamtc.xmlparser.bean.Device;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class DeviceTable extends TagSupport {
    private List<Device> devices;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<table border=\"3\">" +
                    "<tr> " +
                    "<th>Name</th> " +
                    "<th>Origin</th> " +
                    "<th>Price</th> " +
                    "<th>Ports</th> " +
                    "<th>Group</th> " +
                    "<th>Energy Consumption</th> " +
                    "<th>Cooler</th> " +
                    "<th>Peripheral</th> " +
                    "<th>Critical</th> " +
                    "<th>Arrival Date</th> " +
                    "</tr>\n");
            for (Device device : devices) {
                out.write("<tr>\n");
                out.write("<td>");
                out.write(device.getName());
                out.write("</td>");

                out.write("<td>");
                out.write(device.getOrigin());
                out.write("</td>");

                out.write("<td>");
                out.write(String.valueOf(device.getPrice()));
                out.write("</td>");

                out.write("<td>");
                out.write(device.getFormattedPorts());
                out.write("</td>");

                out.write("<td>");
                out.write(device.getGroup());
                out.write("</td>");

                out.write("<td>");
                out.write(String.valueOf(device.getEnergyConsumption()));
                out.write("</td>");

                out.write("<td>");
                out.write(device.isHasCooler() ? "+" : "-");
                out.write("</td>");

                out.write("<td>");
                out.write(device.isPeripheral() ? "+" : "-");
                out.write("</td>");

                out.write("<td>");
                out.write(device.isCritical() ? "+" : "-");
                out.write("</td>");

                out.write("<td>");
                out.write(device.getFormattedArrivalDate());
                out.write("</td>");
                out.write("</tr>\n");
            }
            out.write("</table>");
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }

        return SKIP_BODY;
    }
}
