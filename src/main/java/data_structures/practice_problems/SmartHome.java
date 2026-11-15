
interface Dimmable {

    void dim(int level);
}

interface Schedulable {

    void schedule(String time);
}

interface EnergyMonitor {

    void showEnergy();
}

abstract class Device {

    protected String id;
    protected boolean isOn;

    Device(String id) {
        this.id = id;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(id + " is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(id + " is OFF");
    }
}

class Light extends Device implements Dimmable, Schedulable {

    Light(String id) {
        super(id);
    }

    public void dim(int level) {
        if (level >= 0 && level <= 100) {
            System.out.println(id + " dimmed to " + level);
        } else {
            System.out.println(id + " rejected: invalid dim level");
        }
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Fan extends Device implements Schedulable {

    Fan(String id) {
        super(id);
    }

    public void schedule(String time) {
        System.out.println(id + " scheduled " + time);
    }
}

class Plug extends Device implements EnergyMonitor {

    private double energy;

    Plug(String id, double energy) {
        super(id);
        this.energy = energy;
    }

    public void showEnergy() {
        System.out.println(id + " energy " + energy + " kWh");
    }
}

public class SmartHome {

    public static void processCommand(Device device, String command, String value) {
        switch (command) {
            case "ON":
                device.turnOn();
                break;

            case "OFF":
                device.turnOff();
                break;

            case "DIM":
                if (device instanceof Dimmable) {
                    ((Dimmable) device).dim(Integer.parseInt(value));
                } else {
                    System.out.println(device.id + " rejected: DIM unsupported");
                }
                break;

            case "SCHEDULE":
                if (device instanceof Schedulable) {
                    ((Schedulable) device).schedule(value);
                } else {
                    System.out.println(device.id + " rejected: SCHEDULE unsupported");
                }
                break;

            case "ENERGY":
                if (device instanceof EnergyMonitor) {
                    ((EnergyMonitor) device).showEnergy();
                } else {
                    System.out.println(device.id + " rejected: ENERGY unsupported");
                }
                break;

            default:
                System.out.println(device.id + " rejected: invalid command");
        }
    }

    public static void main(String[] args) {
        Device light = new Light("L1");
        Device fan = new Fan("F1");
        Device plug = new Plug("P1", 12);

        processCommand(light, "ON", "");
        processCommand(light, "DIM", "40");
        processCommand(fan, "DIM", "30");
        processCommand(fan, "SCHEDULE", "22:00");
        processCommand(plug, "ENERGY", "");
        processCommand(light, "ENERGY", "");
    }
}
