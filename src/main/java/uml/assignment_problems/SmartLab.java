
import java.util.*;

interface Capability {

    boolean apply(double value, String deviceName);
}

class PowerCapability implements Capability {

    public boolean apply(double value, String name) {
        if (value != 0 && value != 1) {
            System.out.println("Rejected: Invalid power value.");
            return false;
        }

        System.out.println(name + ": "
                + (value == 1 ? "ON" : "OFF") + ".");
        return true;
    }
}

class BrightnessCapability implements Capability {

    public boolean apply(double value, String name) {
        if (value < 0 || value > 100) {
            System.out.println(
                    "Rejected: Brightness must be between 0 and 100%.");
            return false;
        }

        System.out.println(name
                + ": brightness set to " + (int) value + "%.");
        return true;
    }
}

class TemperatureCapability implements Capability {

    public boolean apply(double value, String name) {
        if (value < 16 || value > 30) {
            System.out.println("Rejected: " + name
                    + " temperature must be between 16°C and 30°C.");
            return false;
        }

        System.out.println(name
                + ": temperature set to " + (int) value + "°C.");
        return true;
    }
}

class Device {

    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(String key, Capability capability) {
        capabilities.put(key, capability);
        System.out.println(name + ": " + key
                + " capability added.");
    }

    public boolean supports(String key) {
        return capabilities.containsKey(key);
    }

    public boolean apply(String key, double value) {
        Capability capability = capabilities.get(key);

        if (capability == null) {
            return false;
        }

        return capability.apply(value, name);
    }
}

class SceneStep {

    String capabilityName;
    double value;

    public SceneStep(String capabilityName, double value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }
}

class Scene {

    private String name;
    private List<SceneStep> steps = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(String capability, double value) {
        steps.add(new SceneStep(capability, value));
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");

        int actions = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.supports(step.capabilityName)) {
                    if (device.apply(
                            step.capabilityName, step.value)) {
                        actions++;
                    }
                }
            }
        }

        System.out.println("Scene '" + name
                + "' completed: " + actions
                + " actions applied.");
    }
}

public class SmartLab {

    public static void main(String[] args) {

        Device ac = new Device("Lab AC");
        ac.addCapability("Power", new PowerCapability());
        ac.addCapability("Temperature",
                new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability("Power", new PowerCapability());
        lights.addCapability("Brightness",
                new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability("Power", new PowerCapability());

        List<Device> devices
                = Arrays.asList(ac, lights, projector);

        Scene lecture = new Scene("Lecture Mode");

        lecture.addStep("Power", 1);
        lecture.addStep("Brightness", 40);
        lecture.addStep("Temperature", 24);

        lecture.execute(devices);

        ac.apply("Temperature", 12);

        projector.addCapability(
                "Brightness", new BrightnessCapability());

        projector.apply("Brightness", 70);
    }
}
