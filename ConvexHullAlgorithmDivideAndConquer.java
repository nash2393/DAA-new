import java.util.*;

/**
 * Implementation of the Convex Hull using the Divide and Conquer algorithm.
 *
 * @author NISHANT SINGH PARIHAR
 */
public class ConvexHullAlgorithmDivideAndConquer {

    public static void main(String[] args) {
        int[][] coordinates = {
                { 3, 3 }, { 3, -3 }, { -3, 3 }, { -3, -3 },
                { 1, -1 }, { -1, 1 }, { 2, -2 }, { 3, -1 },
                {4,4},{-7,5},{4,2},{10,10}, {3,-1}, {3,-6},
                {-1,-8},{-8,-5},{16,17},{17,17}
        };

        List<int[]> convexHullPoints = determineConvexHullPoints(coordinates);
        System.out.println("Convex Hull:");
        for (int[] point : convexHullPoints) {
            System.out.println(point[0] + " " + point[1]);
        }
    }

    /**
     * Determines the convex hull of a set of points using the
     * Divide and Conquer algorithm.
     *
     * @param points The array of input points.
     * @return The list of points that form the convex hull in counter-clockwise order.
     */
    public static List<int[]> determineConvexHullPoints(int[][] points) {
        sortPointsByXCoordinate(points);

        int[] referencePoint = points[0];
        sortPointsByPolarAngleWithRespectToReference(points, referencePoint);

        List<int[]> convexHull = new ArrayList<>();
        convexHull.add(points[0]);
        convexHull.add(points[1]);

        for (int i = 2; i < points.length; i++) {
            while (convexHull.size() > 1 && calculateCrossProduct(convexHull.get(convexHull.size() - 2), convexHull.get(convexHull.size() - 1), points[i]) <= 0) {
                convexHull.remove(convexHull.size() - 1);
            }
            convexHull.add(points[i]);
        }

        return convexHull;
    }

    /**
     * Computes the cross product of vectors OA and OB, where O is the origin point.
     *
     * @param o The origin point.
     * @param a First endpoint of the vector.
     * @param b Second endpoint of the vector.
     * @return The result of the cross product.
     */
    private static int calculateCrossProduct(int[] o, int[] a, int[] b) {
        return (a[0] - o[0]) * (b[1] - o[1]) - (a[1] - o[1]) * (b[0] - o[0]);
    }

    /**
     * Computes the squared Euclidean distance between two points.
     *
     * @param a The first point.
     * @param b The second point.
     * @return The squared distance between the two points.
     */
    private static int calculateSquaredDistance(int[] a, int[] b) {
        return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]);
    }

    /**
     * Sorts the array of points by their x-coordinates in ascending order.
     * In case of a tie, points are sorted by their y-coordinates.
     *
     * @param points The array of points to be sorted.
     */
    private static void sortPointsByXCoordinate(int[][] points) {
        // Implementing bubble sort to sort points by X and then by Y.
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = 0; j < points.length - 1 - i; j++) {
                if (points[j][0] > points[j + 1][0] ||
                        (points[j][0] == points[j + 1][0] && points[j][1] > points[j + 1][1])) {
                    // Swap the points.
                    int[] temp = points[j];
                    points[j] = points[j + 1];
                    points[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Sorts the array of points based on their polar angles with respect
     * to a given reference point. Points are ordered counter-clockwise.
     *
     * @param points    The array of points to be sorted.
     * @param reference The reference point for determining polar angles.
     */
    private static void sortPointsByPolarAngleWithRespectToReference(int[][] points, int[] reference) {
        // Implementing bubble sort to sort points based on polar angle and distance.
        for (int i = 1; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int cross = calculateCrossProduct(reference, points[i], points[j]);
                if (cross < 0 || (cross == 0 && calculateSquaredDistance(reference, points[i]) > calculateSquaredDistance(reference, points[j]))) {
                    // Swap the points.
                    int[] temp = points[i];
                    points[i] = points[j];
                    points[j] = temp;
                }
            }
        }
    }
}