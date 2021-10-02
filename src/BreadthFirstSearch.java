import java.util.ArrayList;

public class BreadthFirstSearch {
    public BreadthFirstSearch() {

    }

    public ArrayList<Node> bfs(Node root) {

        ArrayList<Node> pathToSolution = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        int counter = 0;

        openList.add(root);
        boolean goalFound = false;

        while (openList.size() > 0 && !goalFound) {
            counter++;
            System.out.println("Computing ticks: " + counter);
            Node currentNode = openList.get(0);
            if (currentNode.isBoardValid()) {
                System.out.println("The given board is valid");
                goalFound = true;
                pathTrace(pathToSolution, currentNode);
            }
            closedList.add(currentNode);
            openList.remove(0);

            currentNode.expandNode();

            for (int i = 0; i < currentNode.children.size(); i++) {
                Node currentChild = currentNode.children.get(i);

                if (currentChild.isBoardValid()) {
                    System.out.println("Goal found. ");
                    System.out.println("CL: " + closedList.size());
                    System.out.println("OL: " + openList.size());
                    goalFound = true;
                    pathTrace(pathToSolution, currentChild);
                    break;
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
