/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {

    private Node root;
    private final ArrayList<Point2D> range;
    private Point2D nearest;

    private class Node {
        private Point2D key;
        private Node left;
        private Node right;
        private int count;

        public Node(Point2D key) {
            this.key = new Point2D(key.x(), key.y());
            this.left = null;
            this.right = null;
            this.count = 1;
        }
    }

    public KdTree() {
        root = null;
        range = new ArrayList<Point2D>();
        nearest = null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Node x = root;
        int level = 0;
        while (x != null) {
            if (p.equals(x.key))
                return true;
            if (level % 2 == 0) {
                if (p.x() < x.key.x()) x = x.left;
                else x = x.right;
            }
            else {
                if (p.y() < x.key.y()) x = x.left;
                else x = x.right;
            }
            level++;
        }
        return false;
    }

    public void insert(Point2D key) {
        if (key == null)
            throw new IllegalArgumentException();
        root = insert(root, key, 0);
    }

    private Node insert(Node x, Point2D key, int level) {
        if (x == null) return new Node(key);
        if (x.key.equals(key)) {
            x.key = new Point2D(key.x(), key.y());
            return x;
        }
        if (level % 2 == 0) {
            if (key.x() < x.key.x()) x.left = insert(x.left, key, level + 1);
            else x.right = insert(x.right, key, level + 1);
            x.count = 1 + size(x.left) + size(x.right);
        }
        else {
            if (key.y() < x.key.y()) x.left = insert(x.left, key, level + 1);
            else x.right = insert(x.right, key, level + 1);
            x.count = 1 + size(x.left) + size(x.right);
        }
        return x;
    }

    public void draw() {
        // Unimplemented
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        range.clear();
        range(rect, root, 0.0, 0.0, 1.0, 1.0, 0);
        return range;
    }

    private void range(RectHV rect, Node x, double xmin, double ymin, double xmax, double ymax,
                       int level) {
        if (x == null)
            return;
        if (level % 2 == 0) {
            RectHV leftRect = new RectHV(xmin, ymin, x.key.x(), ymax);
            RectHV rightRect = new RectHV(x.key.x(), ymin, xmax, ymax);
            if (rect.intersects(leftRect))
                range(rect, x.left, xmin, ymin, x.key.x(), ymax, level + 1);
            if (rect.intersects(rightRect))
                range(rect, x.right, x.key.x(), ymin, xmax, ymax, level + 1);
        }
        else {
            RectHV leftRect = new RectHV(xmin, ymin, xmax, x.key.y());
            RectHV rightRect = new RectHV(xmin, x.key.y(), xmax, ymax);
            if (rect.intersects(leftRect))
                range(rect, x.left, xmin, ymin, xmax, x.key.y(), level + 1);
            if (rect.intersects(rightRect))
                range(rect, x.right, xmin, x.key.y(), xmax, ymax, level + 1);
        }
        if (rect.contains(x.key))
            range.add(x.key);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        RectHV initialRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        nearest = null;
        nearest(p, root, 0, initialRect);
        return nearest;
    }

    private void nearest(Point2D p, Node x, int level, RectHV rect) {
        if (x == null || (nearest != null && nearest.distanceSquaredTo(p) <= rect
                .distanceSquaredTo(p)))
            return;
        if (nearest == null || x.key.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) {
            nearest = x.key;
        }
        if (level % 2 == 0) {
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), x.key.x(), rect.ymax());
            RectHV rightRect = new RectHV(x.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
            if (x.key.x() - p.x() >= 0) {
                nearest(p, x.left, level + 1, leftRect);
                nearest(p, x.right, level + 1, rightRect);
            }
            else {
                nearest(p, x.right, level + 1, rightRect);
                nearest(p, x.left, level + 1, leftRect);
            }
        }
        else {
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.key.y());
            RectHV rightRect = new RectHV(rect.xmin(), x.key.y(), rect.xmax(), rect.ymax());
            if (x.key.y() - p.y() >= 0) {
                nearest(p, x.left, level + 1, leftRect);
                nearest(p, x.right, level + 1, rightRect);
            }
            else {
                nearest(p, x.right, level + 1, rightRect);
                nearest(p, x.left, level + 1, leftRect);
            }
        }
    }

    public static void main(String[] args) {
        // Unimplemented
    }
}
