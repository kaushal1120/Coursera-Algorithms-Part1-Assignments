/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

// finds all line segments containing 4 points
public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        int length = points.length;
        Point[] pointsCopy = new Point[length];
        for (int i = 0; i < length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
            pointsCopy[i] = points[i];
        }

        numberOfSegments = 0;
        lineSegments = new LineSegment[length * (length - 1) / 2];
        Arrays.sort(pointsCopy);
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                for (int k = j + 1; k < length - 1; k++) {
                    for (int m = k + 1; m < length; m++) {
                        if (Double
                                .compare(pointsCopy[i].slopeTo(pointsCopy[j]),
                                         pointsCopy[i].slopeTo(pointsCopy[k]))
                                == 0
                                && Double.compare(pointsCopy[i].slopeTo(pointsCopy[j]),
                                                  pointsCopy[i].slopeTo(pointsCopy[m])) == 0) {
                            lineSegments[numberOfSegments++] = new LineSegment(pointsCopy[i],
                                                                               pointsCopy[m]);
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegmentCopy = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++)
            lineSegmentCopy[i] = lineSegments[i];
        return lineSegmentCopy;
    }

    public static void main(String[] args) {
        // Default Client
    }
}
