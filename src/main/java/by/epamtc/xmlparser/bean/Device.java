package by.epamtc.xmlparser.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Device implements Serializable {
    private int id;
    private int stock;
    private String name;
    private String origin;
    private int price;
    private List<String> ports;
    private String group;
    private int energyConsumption;
    private boolean hasCooler;
    private boolean peripheral;
    private boolean critical;
    private Date arrivalDate;

    public Device() {
        id = -1;
        stock = -1;
        name = "";
        origin = "";
        price = -1;
        ports = new ArrayList<>();
        group = "";
        energyConsumption = -1;
        arrivalDate = new Date();
    }

    public Device(int id, int stock, String name, String origin, int price, List<String> ports, String group, int energyConsumption, boolean hasCooler, boolean peripheral, boolean isCritical, Date arrivalDate) {
        this.id = id;
        this.stock = stock;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.ports = ports;
        this.group = group;
        this.energyConsumption = energyConsumption;
        this.hasCooler = hasCooler;
        this.peripheral = peripheral;
        this.critical = isCritical;
        this.arrivalDate = arrivalDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getPorts() {
        return ports;
    }

    public String getFormattedPorts() {
        String res = ports.toString();
        if (res.length() == 2) {
            return "Портов нет";
        }
        res = res.replace("[", "");
        res = res.replace("]", "");

        return res;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public boolean isHasCooler() {
        return hasCooler;
    }

    public void setHasCooler(boolean hasCooler) {
        this.hasCooler = hasCooler;
    }

    public boolean isPeripheral() {
        return peripheral;
    }

    public void setPeripheral(boolean peripheral) {
        this.peripheral = peripheral;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public String getFormattedArrivalDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(arrivalDate);
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id == device.id && stock == device.stock && price == device.price && energyConsumption == device.energyConsumption && hasCooler == device.hasCooler && peripheral == device.peripheral && critical == device.critical && Objects.equals(name, device.name) && Objects.equals(origin, device.origin) && Objects.equals(ports, device.ports) && Objects.equals(group, device.group) && Objects.equals(arrivalDate, device.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, name, origin, price, ports, group, energyConsumption, hasCooler, peripheral, critical, arrivalDate);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append("{");
        stringBuilder.append("id='").append(id).append('\'');
        stringBuilder.append("stock='").append(stock).append('\'');
        stringBuilder.append("name='").append(name).append('\'');
        stringBuilder.append(", origin='").append(origin).append('\'');
        stringBuilder.append(", price='").append(price).append('\'');
        stringBuilder.append(", ports='").append(ports).append('\'');
        stringBuilder.append(", group='").append(group).append('\'');
        stringBuilder.append(", energyConsumption='").append(energyConsumption).append('\'');
        stringBuilder.append(", hasCooler='").append(hasCooler).append('\'');
        stringBuilder.append(", isPeripheral='").append(peripheral).append('\'');
        stringBuilder.append(", isCritical='").append(critical).append('\'');
        stringBuilder.append(", arrivalDate='").append(arrivalDate).append('}');
        return stringBuilder.toString();
    }
}
