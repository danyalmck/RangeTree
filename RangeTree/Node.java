public class Node {

    public Node left, right;

    public Tree sub_tree = null;

    public Point point;

    public Point[] points;

    public Node(Point point, Point[] points) {
        this.point = point;
        this.points = points;
    }

}
