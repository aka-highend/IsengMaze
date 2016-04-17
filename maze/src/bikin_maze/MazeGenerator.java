/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bikin_maze;

import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author fachrur_122
 */
public class MazeGenerator {
        public Node[][] nodeArray;
	ArrayList<Node> nodeList;
	int depth;
	int width;
	int totalNodes;
	int visitedNodes;
	boolean debug;
	boolean solved;
	ArrayList<Node> sol;
        
	public MazeGenerator(int width, int depth, boolean debug) {
		nodeArray = new Node[width][depth];
		nodeList = new ArrayList<Node>();
		totalNodes = width * depth;
		this.depth = width;
		this.width = depth;
		visitedNodes = 0;
		this.debug = debug;
		solved = false;
		sol = new ArrayList<Node>();

		createNodes(totalNodes, width, depth);
		setRelations();
		makeMaze(nodeArray[0][0]);

	}
        
	private boolean allVisited() {
		for (Node node : nodeList) {
			if (node.visited == false) {
				return false;
			}
		}
		return true;
	}
        
	private void makeMaze(Node node) {

		if (node == nodeArray[depth - 1][width - 1]) {
			solved = true;
		}

		if (!solved) {
			node.solution = true;
			sol.add(node);
		}

		if (debug)
			display();

		ArrayList<Node> validNodes = node.validNodes();

		if (allVisited()) {
			nodeArray[depth - 1][width - 1].visitedSouth = true;
			nodeArray[0][0].visitedNorth = true;
			return;
		}

		Random rand = new Random();
		node.visited = true;
		visitedNodes = visitedNodes + 1;

		if (validNodes.size() == 0) {
			makeMaze(node.cameHereFrom);
			if (!solved) {
				sol.remove(node);
			}
			visitedNodes = visitedNodes - 1;
			return;
		}
		else {

			int nodeI = rand.nextInt(validNodes.size());

			Node nodeToVisit = validNodes.get(nodeI);

			if (node.north == nodeToVisit) {
				node.visitedNorth = true;
				node.north.cameHereFrom = node;
				node.north.visitedSouth = true;
			}
			if (node.east == nodeToVisit) {
				node.visitedEast = true;
				node.east.cameHereFrom = node;
				node.east.visitedWest = true;
			}
			if (node.south == nodeToVisit) {
				node.visitedSouth = true;
				node.south.cameHereFrom = node;
				node.south.visitedNorth = true;
			}
			if (node.west == nodeToVisit) {
				node.visitedWest = true;
				node.west.cameHereFrom = node;
				node.west.visitedEast = true;
			}

			makeMaze(nodeToVisit);
		}

	}
	private void setRelations() {
		for (Node node : nodeList) {
			node.north = getNodeBasedOnLoc(node.x, node.y - 1);
			node.south = getNodeBasedOnLoc(node.x, node.y + 1);
			node.east = getNodeBasedOnLoc(node.x + 1, node.y);
			node.west = getNodeBasedOnLoc(node.x - 1, node.y);

		}

	}
	private Node getNodeBasedOnLoc(int x, int y) {
		for (Node node : nodeList) {
			if (node.x == x && node.y == y) {
				return node;
			}
		}
		return null;
	}
	private void createNodes(int totalNodes, int width, int depth) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < depth; j++) {
				Node node = new Node(i, j);
				nodeList.add(node);
				nodeArray[i][j] = node;
			}
		}

	}
	public void display() {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < 3; j++) {

				for (int k = 0; k < depth; k++) {
					if (j == 0) {
						System.out.print(nodeArray[k][i].topToString());
					}
					else if (j == 1) {
						System.out.print(nodeArray[k][i].middletoString());
					}
					else {
						System.out.print(nodeArray[k][i].bottomtoString());
					}
				}
				System.out.println();
			}

		}
	}
	public class Node {

		Node north;
		Node south;
		Node east;
		Node west;

		Node cameHereFrom;

		boolean visitedEast;
		boolean visitedWest;
		boolean visitedNorth;
		boolean visitedSouth;

		boolean visited;

		boolean solution = false;

		int x;
		int y;
                
		private Node(int x, int y) {
			this.x = x;
			this.y = y;
			visited = false;
			visitedEast = false;
			visitedWest = false;
			visitedNorth = false;
			visitedSouth = false;

		}

		@Override
		public String toString() {
			return this.x + " , " + this.y + "\nNorth: " + this.visitedNorth + "\nEast: "
			        + this.visitedEast + "\nSouth: " + this.visitedSouth + "\nWest: "
			        + this.visitedWest + "\n-------";

		}
                
		public String topToString() {
			StringBuilder sb = new StringBuilder();

			sb.append("@ ");

			if (this.visitedNorth) {
				sb.append("  ");
			}
			else
				sb.append("@ ");
			sb.append("@");

			return sb.toString();
		}
                
		public String middletoString() {
			StringBuilder sb = new StringBuilder();

			if (this.visitedWest) {
				sb.append("  ");
			}
			else
				sb.append("@ ");
			sb.append("  ");
			if (this.visitedEast) {
				sb.append(" ");
			}
			else
				sb.append("@");
			return sb.toString();
		}
		public String bottomtoString() {
			StringBuilder sb = new StringBuilder();
			sb.append("@ ");

			if (this.visitedSouth) {
				sb.append("  ");
			}
			else
				sb.append("@ ");
			sb.append("@");

			return sb.toString();
		}
		private ArrayList<Node> validNodes() {
			ArrayList<Node> list = new ArrayList<Node>();

			if (this.north != null && this.north.visited == false) {
				list.add(this.north);
			}
			if (this.east != null && this.east.visited == false) {
				list.add(this.east);
			}
			if (this.south != null && this.south.visited == false) {
				list.add(this.south);
			}
			if (this.west != null && this.west.visited == false) {
				list.add(this.west);
			}

			return list;

		}
	}
}


