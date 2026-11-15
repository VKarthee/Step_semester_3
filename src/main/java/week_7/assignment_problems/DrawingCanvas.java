
abstract class Shape {

    private static int counter = 0;
    private final String shapeId;

    Shape() {
        shapeId = "SH-" + (++counter);
    }

    public abstract double calculateArea();

    public void scale(double factor) {
        scale(factor, factor);
    }

    public void scale(double xFactor, double yFactor) {
        // Overridden by individual shapes if needed
    }

    public String getShapeId() {
        return shapeId;
    }

    public static void printArea(Shape s) {
        System.out.println("Area: " + s.calculateArea());
    }
}

class CircleShape extends Shape {

    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        radius *= Math.sqrt(xFactor * yFactor);
    }
}

class SquareShape extends Shape {

    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        side *= Math.sqrt(xFactor * yFactor);
    }
}

public class DrawingCanvas {

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println("Circle Area: " + c.calculateArea());
        System.out.println("Square Area: " + sq.calculateArea());

        sq.scale(2.0);
        System.out.println("Scaled Square Area: " + sq.calculateArea());

        Shape.printArea(c);

        System.out.println("Circle ID: " + c.getShapeId());
        System.out.println("Square ID: " + sq.getShapeId());
    }
}
