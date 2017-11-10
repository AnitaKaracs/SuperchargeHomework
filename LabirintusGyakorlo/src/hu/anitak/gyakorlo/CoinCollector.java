package hu.anitak.gyakorlo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinCollector {
	private static final double MAX_COIN_DISTANCE = 1000.0;
	private List<Point> allCoins;
	private Point start;
	private Point end;
	
	public CoinCollector(List<Point> allCoins, Point start, Point end) {
		this.allCoins = allCoins;
		this.start = start;
		this.end = end;
	}
	
	public List<Point> getShortestRouteToAllCoins() {
		List<Coin> routeToAllCoins = new ArrayList<Coin>();
		Coin firstCoin = new Coin(start);
		routeToAllCoins.add(firstCoin);
		getRouteToAllCoins(routeToAllCoins, firstCoin);
		
		Coin coinWithShortestDistanceFromFirstCoin = getCoinWithShortestDistanceFromFirstCoin(routeToAllCoins);
		
		List<Point> shortestRouteToAllCoins = new ArrayList<Point>();
		shortestRouteToAllCoins.add(coinWithShortestDistanceFromFirstCoin.getPosition());
		
		Coin previousCoin = coinWithShortestDistanceFromFirstCoin.getPreviousCoin();
		while(previousCoin != null) {
			shortestRouteToAllCoins.add(previousCoin.getPosition());
			previousCoin = previousCoin.getPreviousCoin();
		}
		
		Collections.reverse(shortestRouteToAllCoins);
		
		return shortestRouteToAllCoins;
	}
	
	private void getRouteToAllCoins(List<Coin> routeToAllCoins, Coin previousCoin) {
		for(Point coinToCollect : allCoins) {
			if(isNotCollectedCoin(new Coin(coinToCollect), previousCoin)) {
				Coin nextCoin = new Coin(coinToCollect, previousCoin); 
				routeToAllCoins.add(nextCoin);
				
				if(!coinToCollect.equals(end)) {
					getRouteToAllCoins(routeToAllCoins, nextCoin);
				}
			}
		}	
	} 
	
	private boolean isNotCollectedCoin(Coin actualCoin, Coin previousCoin) {
		if(actualCoin.getPosition().equals(previousCoin.getPosition())) {
			return false;
		}
		
		Coin collectedCoin = previousCoin.getPreviousCoin();
		while(collectedCoin != null) {
			if(actualCoin.getPosition().equals(collectedCoin.getPosition())) {
				return false;
			}
			collectedCoin = collectedCoin.getPreviousCoin();
		}
		
		return true;
	}
	
	private Coin getCoinWithShortestDistanceFromFirstCoin(List<Coin> routeToAllCoins) {
    	double shortestDistanceFromFirstCoin = MAX_COIN_DISTANCE;
    	Coin coinWithShortestDistanceFromFirstCoin = null;
    	for(Coin coin : routeToAllCoins) {
    		if(coin.isLastCoin()) {
    			Coin previousCoin = coin.getPreviousCoin();
    			double distanceFromFirstCoin = 0.0;
    			int collectedCoins = 1;
        		while(previousCoin != null) {
        			distanceFromFirstCoin += countDistance(coin.getPosition(), previousCoin.getPosition());
        			previousCoin = previousCoin.getPreviousCoin();
        			collectedCoins++;
        		}
        		
        		if(collectedCoins != allCoins.size()) {
        			continue;
        		}
        		
        		if(distanceFromFirstCoin < shortestDistanceFromFirstCoin) {
        			coinWithShortestDistanceFromFirstCoin = coin;
        		}
    		}
    	}
    	
    	return coinWithShortestDistanceFromFirstCoin;
    }
	
	//szépséghiba... ha fal van közte, akkor is a legrövidebb útnak mondja
	private static double countDistance(Point coin1, Point coin2) {
		return Math.abs(coin1.distance(coin2));
	}
	
	
	class Coin {
	    private List<Coin> otherCoinsToCollect = new ArrayList<Coin>();
	    private Coin previousCoin = null;
	    private Point position = null;

	    public Coin(Point position) {
	        this.position = position;
	    }

	    public Coin(Point position, Coin parent) {
	        this.position = position;
	        setParent(parent);
	    }

	    public void setParent(Coin parent) {
	        parent.addOtherCoinsToCollect(this);
	        this.previousCoin = parent;
	    }

	    public void addOtherCoinsToCollect(Coin child) {
	        this.otherCoinsToCollect.add(child);
	    }
	    
	    public Point getPosition() {
			return position;
		}

	    public boolean isLastCoin() {
	        return this.otherCoinsToCollect.isEmpty(); 
	    }

	    public Coin getPreviousCoin() {
			return previousCoin;
		}
	}
}
