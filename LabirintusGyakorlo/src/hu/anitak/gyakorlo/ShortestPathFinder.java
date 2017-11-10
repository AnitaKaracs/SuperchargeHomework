package hu.anitak.gyakorlo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShortestPathFinder {
	private static final int FREE_SPACE = 0;
	private static final int BLOCK = -1;

	public static List<Point> getShortestPath(Block[][] labyrinth, Point start, Point end) {
		return shortestPathFinderAlgorythm(labyrinth, start, end);
	}
	
	//based on
	//https://stackoverflow.com/questions/30551194/find-shortest-path-in-a-maze-with-recursive-algorithm
	private static List<Point> shortestPathFinderAlgorythm(Block[][] labyrinth, Point start, Point end) {
		int[][] stepsFromStart = calculateStepsFromStart(labyrinth, start, end);
		
		List<Point> shortestPath = getShortestPathBySteps(labyrinth, start, end, stepsFromStart);
		
		return shortestPath;
	}

	private static List<Point> getShortestPathBySteps(Block[][] labyrinth, Point start, Point end, int[][] stepsFromStart) {
		List<Point> shortestPath = new ArrayList<Point>();
		Point stepBack = end;
		
		while(!stepBack.equals(start)) {
	        shortestPath.add(stepBack);
	        int step = stepsFromStart[stepBack.x][stepBack.y];
	        ArrayList<Point> possibleMoves = getPossibleMovesOfPoint(stepBack);

	        for (Point potentialMove: possibleMoves)  {
	            if (!isLabyrinthEdgeReached(labyrinth, potentialMove)) {
	                if (stepsFromStart[potentialMove.x][potentialMove.y] == step - 1) {
	                	stepBack = potentialMove;
	                    break;
	                }
	            }
	        }
	    }
		Collections.reverse(shortestPath);
		return shortestPath;
	}

	private static int[][] calculateStepsFromStart(Block[][] labyrinth, Point start, Point end) {
		int[][] stepsFromStart = getStepMapOfLabyrinth(labyrinth);

	    stepsFromStart[start.x][start.y] = 1;
	    
	    LinkedList<Point> path = new LinkedList<Point>();
	    path.add(start);
		
	    while (!path.isEmpty()) {
	        Point pointToStep = path.poll();
	        
	        if(pointToStep.equals(end)) {
	            break;
	        }

	        int step = stepsFromStart[pointToStep.x][pointToStep.y];
	        ArrayList<Point> possibleMoves = getPossibleMovesOfPoint(pointToStep);
	        
	        for(Point potentialMove : possibleMoves) {
	        	if(!isLabyrinthEdgeReached(labyrinth, potentialMove)) {
	        		if(stepsFromStart[potentialMove.x][potentialMove.y] == FREE_SPACE) {
	        			path.add(potentialMove);
	                    stepsFromStart[potentialMove.x][potentialMove.y] = step + 1;
	                }
	        	}
	        }
	    }
		return stepsFromStart;
	}

	private static int[][] getStepMapOfLabyrinth(Block[][] labyrinth) {
		int[][] steps = new int[labyrinth.length][labyrinth[0].length];

	    for(int i = 0; i < labyrinth.length; i++) {
	    	int j = 0;
			for(Block block : labyrinth[i]) {
	            if (block.equals(Block.WALL)) {
	            	steps[i][j++] = BLOCK;
	            } else {
	            	steps[i][j++] = FREE_SPACE;
	            }
	        }
	    }
		return steps;
	}
	
	private static ArrayList<Point> getPossibleMovesOfPoint(Point point) {
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
        // Move Right
        possibleMoves.add(new Point(point.x + 1, point.y));
        // Down Move
        possibleMoves.add(new Point(point.x, point.y - 1));
        // Move Left
        possibleMoves.add(new Point(point.x - 1, point.y));
        // Move Up
        possibleMoves.add(new Point(point.x, point.y + 1));
        return possibleMoves;
	}
	
	private static boolean isLabyrinthEdgeReached(Block[][] labyrinth, Point point) {
		return point.x < 0 || point.x > labyrinth.length-1 || point.y < 0 || point.y > labyrinth[0].length-1;
	}
}
