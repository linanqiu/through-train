package engine;

import java.util.HashMap;
import java.util.LinkedList;

import engine.Track.TrackType;

public class State {
	// stations and tracks
	public HashMap<String, Station> stationNameMap;
	public HashMap<Station, LinkedList<Track>> stationTrackMap;

	// tickets
	public HashMap<Player, LinkedList<Ticket>> playerTicketMap;
	public HashMap<Station, LinkedList<Ticket>> stationTicketMap;
	public LinkedList<Ticket> ticketDrawPile;

	// cards
	public HashMap<Player, HashMap<TrackType, Integer>> playerCardMap;
	public HashMap<TrackType, Integer> cardsDrawPile;
	public HashMap<TrackType, Integer> cardsOpen;

	// trains
	public HashMap<Player, Integer> trainPile;

	// points
	public HashMap<Player, Integer> playerPointsMap;
	
	public boolean isFinalTurn = false;

	public State(State other) {
		this.stationNameMap = new HashMap<>(other.stationNameMap);
		this.stationTrackMap = new HashMap<>(other.stationTrackMap);
		this.playerTicketMap = new HashMap<>(other.playerTicketMap);
		this.stationTicketMap = new HashMap<>(other.stationTicketMap);
		this.ticketDrawPile = new LinkedList<>(other.ticketDrawPile);
		this.playerCardMap = new HashMap<>(other.playerCardMap);
		this.cardsDrawPile = new HashMap<>(other.cardsDrawPile);
		this.cardsOpen = new HashMap<>(other.cardsOpen);
		this.trainPile = new HashMap<>(other.trainPile);
	}

	public State() {

	}

	public String toString() {
		return stationTrackMap.toString() + "\n" + stationTicketMap.toString() + "\n" + cardsDrawPile.toString() + "\n"
				+ cardsOpen.toString();
	}

	public String toStringPlayers() {
		return playerTicketMap.toString() + "\n" + playerCardMap.toString() + "\n" + trainPile;
	}
}
