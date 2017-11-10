package hu.anitak.gyakorlo;

import java.awt.Point;

public class Main {

	public static void main(String[] args) {
		Point start = new Point(4, 0);
		Point end = new Point(1, 4);
		Labyrinth maze = new Labyrinth();
		maze.enterTheMaze(start, end);
	}

}
