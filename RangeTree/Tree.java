import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map;
import java.util.Comparator;

public class Tree {

    private Node root;

    private String by;


    public Tree(String by) {
        this.by = by;
    }


    public Node construct(Point[] points) {

        Point[] sorted = Sort.sort(points, this.by);

        this.root = this.util_constructor(sorted);

        return this.root;
    }


    private Node util_constructor(Point[] points) {

        if (points.length == 0) {
            return null;
        }

        else if (points.length == 1) {
            return new Node(points[0], points);
        }

        else {

            int med = points.length/2;
            Point point = points[med];

            Point[] p1 = Arrays.copyOfRange(points, 0, med);
            
            Point[] p2 = Arrays.copyOfRange(points, med+1, points.length);

            Node u = new Node(point, points);

            Node t1 = util_constructor(p1);
            Node t2 = util_constructor(p2);

            u.left = t1;
            u.right = t2;

            return u;
        }
    }

    private boolean is_leaf(Node root) {
        if (root == null || (root.left == null && root.right == null)) {
            return true;
        }

        return false;
    }


    private Node get_u(Node root, Rect range, String by) {
        if (by.equals("x")) {

            Interval x = range.x_range;
            while (!(this.is_leaf(root)) && (root.point.x > x.upper || root.point.x < x.lower)) {
                if (root.point.x >= x.upper) {
                    root = root.left;
                }
                else {
                    root = root.right;
                }
            }
            return root;
        }

        else {

            Interval y = range.y_range;
            while (!(this.is_leaf(root)) && (root.point.y > y.upper || root.point.y < y.lower)) {
                if (root.point.y >= y.upper) {
                    root = root.left;
                }
                else {
                    root = root.right;
                }
            }
            return root;
        }
    }


    public void query_2d(Rect range) {
        //(y, x)
        TreeMap<Double, Double> output = new TreeMap<>();

        Node u = this.get_u(this.root, range, "x");

        if (u != null) { 

            if (range.in_range(u.point)) {
                output.put(u.point.y, u.point.x);
            }

            Node v = u.left;
            while (v != null) {
                if (range.in_range(v.point)) {
                    output.put(v.point.y, v.point.x);
                }
        
                if (range.x_range.lower <= v.point.x) {
                
                    if (v.right != null)  { 
                        //construction of the y tree
                        if (v.right.sub_tree == null) { 
                            v.right.sub_tree = new Tree("y");
                            v.right.sub_tree.construct(v.right.points);
                            v.right.points = null;
                            v.right.sub_tree.query_1d(range, output);
                        }
                        else {
                            v.right.sub_tree.query_1d(range, output);
                        }
                    }

                    v = v.left;
                } else {
                    v = v.right;
                }
            }

            v = u.right;
            while (v != null) {
                if (range.in_range(v.point)) {
                    output.put(v.point.y, v.point.x);
                }
        
                if (range.x_range.upper >= v.point.x) {
                    
                    if (v.left != null)  { 
                        //construction of the y tree
                        if (v.left.sub_tree == null) { 
                            v.left.sub_tree = new Tree("y");
                            v.left.sub_tree.construct(v.left.points);
                            v.left.points = null;
                            v.left.sub_tree.query_1d(range, output);
                        }
                        else {
                            v.left.sub_tree.query_1d(range, output);
                        }
                    }

                    v = v.right;
                } else {
                    v = v.left;
                }
            }
        }

        if (output.size() > 0) { 
            String xs = "";
            String ys = "";
            for (Map.Entry<Double,Double> p : output.entrySet()) {
                if (p.getValue()%1 == 0)
                    xs += p.getValue().intValue() + " ";
                else
                    xs += p.getValue() + " ";

                if (p.getKey()%1 == 0)
                    ys += p.getKey().intValue() + " ";
                else    
                    ys += p.getKey() + " ";
            }
            System.out.println(xs);
            System.out.println(ys);
        } else 
            System.out.println("None");
        
    }


    public void query_1d(Rect range, TreeMap<Double, Double> output) {

        Node u = this.get_u(this.root, range, "y");

        if (u != null) { 

            if (range.in_range(u.point)) {
                output.put(u.point.y, u.point.x);
            }

            Node v = u.left;
            while (v != null) {
                if (range.in_range(v.point)) {
                    output.put(v.point.y, v.point.x);
                }
        
                if (range.y_range.lower <= v.point.y) {

                    if (v.right != null)  { 
                        Point[] ps = v.right.points;
                        for (Point point : ps) {
                            output.put(point.y, point.x);
                        }
                    }

                    v = v.left;
                } else {
                    v = v.right;
                }
            }

            v = u.right;
            while (v != null) {
                if (range.in_range(v.point)) {
                    output.put(v.point.y, v.point.x);
                }
        
                if (range.y_range.upper >= v.point.y) {
                    if (v.left != null)  { 
                        Point[] ps = v.left.points;
                        for (Point point : ps) {
                            output.put(point.y, point.x);
                        }
                    }

                    v = v.right;
                } else {
                    v = v.left;
                }
            }
        }
    }

}
/////////////////////////////////////////////////////////////////////// main implementation

//utils

class Sort {
    public static Point[] sort(Point[] points, String by) {
        if (by.equals("x")) {
            Comparator<Point> by_X = (Point p1, Point p2) -> {
                double temp = p1.x - p2.x;
                if (temp > 0)   return 1;
                else if (temp < 0)  return -1;
                return 0;
            };
            Arrays.sort(points, by_X);
            return points;
        } else {
            Comparator<Point> by_Y = (Point p1, Point p2) -> {
                double temp = p1.y - p2.y;
                if (temp > 0)   return 1;
                else if (temp < 0)  return -1;
                return 0;
            };
            Arrays.sort(points, by_Y);
            return points;
        }
    }
}



class Rect {
    public Interval x_range;
    public Interval y_range;

    public Rect(Interval x_range, Interval y_range) {
        this.x_range = x_range;
        this.y_range = y_range;
    }

    public boolean in_range(Point point) {
        return this.x_range.lower <= point.x && point.x <= this.x_range.upper && 
        this.y_range.lower <= point.y && point.y <= this.y_range.upper;
    }
}



class Interval {
    public double lower;
    public double upper;

    public Interval(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
    }
}
