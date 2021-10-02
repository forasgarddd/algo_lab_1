import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        int[][] board = {
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0},
        };

        Node rootNode = new Node(board);
        //System.out.println(rootNode.isBoardValid());
        //System.out.println(rootNode.heuristicBoard());
        //runAStar(rootNode);
        runBFS(rootNode);
    }

    public static void runBFS(Node rootNode) {
        Instant start = Instant.now();
        BreadthFirstSearch ui = new BreadthFirstSearch();
        ArrayList<Node> solution = ui.bfs(rootNode);
        result(start, solution);
    }

    public static void runAStar(Node rootNode) {
        Instant start = Instant.now();
        AStarSearch ui = new AStarSearch();
        ArrayList<Node> solution = ui.aStar(rootNode);
        result(start, solution);
    }

    private static void result(Instant start, ArrayList<Node> solution) {
        if (solution.size() > 0) {
            for (Node node : solution) {
                node.printResult(node.board);

            }
        } else {
            System.out.println("No path to solution is found!");
        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Time: " + timeElapsed + " ms.");
    }
}
