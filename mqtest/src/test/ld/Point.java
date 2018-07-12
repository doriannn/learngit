package test.ld;

public class Point {

    private  double x;

    private double y;

//    private DecimalFormat df = new DecimalFormat("0.000000");

    Point() {
        super();
    }

    Point(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public String getPoint() {
        return (String)(x+","+y);
    }

    public Point Vector(Point p0, Point p1) {

        Point vector = new Point();
        vector.setX(p1.getX() - p0.getX());
        vector.setY(p1.getY() - p0.getY());
        return vector;
    }
    public Point Add(Point p0, Point vector) {
        Point add = new Point();
        add.setPoint(p0.getX() + vector.getX(), p0.getY() + vector.getY());
        return add;
    }
    public void Add(Point vector) {

        setPoint(this.getX() + vector.getX(), this.getY() + vector.getY());
    }
}
