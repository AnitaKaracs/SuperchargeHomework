package hu.anitak.gyakorlo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Labyrinth {
	private Block[][] labyrinth;
	private Point start;
	private Point end;
	
	public Labyrinth() {
		labyrinth = generateLabyrinth();
	}
	
	private Block[][] generateLabyrinth() {
		return new Block[][] {{Block.WALL, Block.WALL, Block.WALL, Block.WALL, Block.WALL},
			 {Block.WALL, Block.WALL, Block.COIN, Block.CORRIDOR, Block.COIN},
			 {Block.WALL, Block.COIN, Block.CORRIDOR, Block.CORRIDOR, Block.WALL},
			 {Block.WALL, Block.CORRIDOR, Block.WALL, Block.WALL, Block.WALL},
			 {Block.COIN, Block.CORRIDOR, Block.CORRIDOR, Block.COIN, Block.WALL},
			 {Block.WALL, Block.WALL, Block.WALL, Block.WALL, Block.WALL}};
		/*
		 * {{1, 1, 1, 1, 1},
			{1, 1, 2, 0, 2},
			{1, 2, 0, 0, 1},
			{1, 0, 1, 1, 1},
			{2, 0, 0, 2, 1},
			{1, 1, 1, 1, 1}}
		 */
	}

	public void enterTheMaze(Point start, Point end) {
		this.start = start;
		this.end = end;
		
		for(Point step : getShortestPath()) {
			printLabyrinth(step);
			collectCoin(step);
		}
	}
	
	private List<Point> getShortestPath() {
	    CoinCollector coinCollector = new CoinCollector(getPointsOfCoins(), start, end);
	    List<Point> shortestRouteToAllCoins = coinCollector.getShortestRouteToAllCoins(); 
	    
		List<Point> shortestPathToCollectAllCoins = new ArrayList<Point>();
		shortestPathToCollectAllCoins.add(start);
		
		Point start = this.start;
		for(Point nextCoin : shortestRouteToAllCoins) {
			shortestPathToCollectAllCoins.addAll(ShortestPathFinder.getShortestPath(labyrinth, start, nextCoin));
			start = nextCoin;
		}
		
		return shortestPathToCollectAllCoins;
	}

	private List<Point> getPointsOfCoins() {
		List<Point> coinsInLabyrinth = new ArrayList<Point>();
		for(int i = 0; i < labyrinth.length; ++i) {
			int j = 0;
			for(Block blockInLabyrinth : labyrinth[i]) {
				if(blockInLabyrinth.equals(Block.COIN)) {
					coinsInLabyrinth.add(new Point(i, j));
				} 
				j++;
			}
		}
		return coinsInLabyrinth;
	}
	
	private void printLabyrinth(Point adventurerPosition) {
		for(int i = 0; i < labyrinth.length; ++i) {
			int j = 0;
			for(Block block : labyrinth[i]) {
				if(adventurerPosition.equals(new Point(i, j))) {
					System.out.print("*");
				} else {
					System.out.print(block.getValue());
				}
				System.out.print(" ");
				j++;
			}
			System.out.println();
		}
		System.out.println();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void collectCoin(Point point) {
		labyrinth[point.x][point.y] = Block.CORRIDOR;
	}
}
