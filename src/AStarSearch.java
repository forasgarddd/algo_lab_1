import java.util.ArrayList;
import java.util.Comparator;

public class AStarSearch {

    public AStarSearch() {

    }

    public ArrayList<Node> aStar(Node root) {

        ArrayList<Node> pathToSolution = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        int counter = 0;

        openList.add(root);
        boolean goalFound = false;
        if (root.isBoardValid()) {
            System.out.println("The given board is valid");
            goalFound = true;
            pathTrace(pathToSolution, root);
        }

        while (openList.size() > 0 && !goalFound) {
            counter++;
            System.out.println("Computing ticks: " + counter);
            Node currentNode = openList.stream()
                    .min(Comparator.comparingInt(Node::heuristicBoard))
                    .get();
            openList.remove(currentNode);
            closedList.add(currentNode);
            if (currentNode.isBoardValid()) {
                System.out.println("Goal found. ");
                System.out.println("CL: " + closedList.size());
                System.out.println("OL: " + openList.size());
                pathTrace(pathToSolution, currentNode);
                goalFound = true;
                break;
            }
            currentNode.expandNode();

            for (Node currentChild : currentNode.children) {
                if (closedList.contains(currentChild)) {
                    continue;
                }
                //currentChild.printResult(currentChild.board);
                for (Node node : openList) {
                    if (currentChild == node && currentChild.heuristicBoard() > node.heuristicBoard()) {
                        continue;
                    }
                }
                openList.add(currentChild);
            }
        }
        return pathToSolution;
    }

    public void pathTrace(ArrayList<Node> path, Node n) {
        System.out.println("Tracing path: ");
        Node current = n;
        path.add(current);

        while (current.parent != null) {
            current = current.parent;
            path.add(current);
        }
    }
}
