import java.util.Scanner;
// import Tree;

public class Main {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int size = Integer.parseInt(sc.nextLine());
        Point[] dps = new Point[size];
        String[] x_s = sc.nextLine().split(" ");
        String[] y_s = sc.nextLine().split(" ");
        
        for (int i = 0; i < x_s.length; i++) {
            dps[i] = new Point(Double.parseDouble(x_s[i]), Double.parseDouble(y_s[i]));
        }

        Tree rt = new Tree("x");
        Node root = rt.construct(dps);
        

        int loop = Integer.parseInt(sc.nextLine());
        int counter = 0;
        Interval x = new Interval(0, 0);
        Interval y = new Interval(0, 0);
        Rect range = new Rect(x, y);
        String[] input;

        while (counter < loop) {
            input = sc.nextLine().split(" ");
            x.upper = Double.parseDouble(input[2]);
            x.lower = Double.parseDouble(input[0]);

            y.lower = Double.parseDouble(input[1]);
            y.upper = Double.parseDouble(input[3]);

            range.x_range = x;
            range.y_range = y;

            rt.query_2d(range);
            counter++;
        }

    }
}
