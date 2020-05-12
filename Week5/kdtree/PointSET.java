/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {

    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> range = new ArrayList<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point))
                range.add(point);
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            double squaredDistance = point.distanceSquaredTo(p);
            if (squaredDistance < minDistance) {
                minDistance = squaredDistance;
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        // Unimplemented
    }
}
