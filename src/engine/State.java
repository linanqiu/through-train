package engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import engine.Track.TrackType;
import moves.Move.MoveType;
import players.Player;

public class State {
	// stations and tracks
	public HashMap<String, Station> stationNameMap;
	public HashMap<Station, LinkedList<Track>> stationTrackMap;
	public List<Track> tracks;

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

	// turn management
	public Player currentPlayer;
	public MoveType currentMoveType;

	public State(State other) {
		this.stationNameMap = new HashMap<>(other.stationNameMap);
		this.stationTrackMap = new HashMap<>(other.stationTrackMap);
		this.tracks = new LinkedList<>(other.tracks);
		this.playerTicketMap = new HashMap<>(other.playerTicketMap);
		this.stationTicketMap = new HashMap<>(other.stationTicketMap);
		this.ticketDrawPile = new LinkedList<>(other.ticketDrawPile);
		this.playerCardMap = new HashMap<>(other.playerCardMap);
		this.cardsDrawPile = new HashMap<>(other.cardsDrawPile);
		this.cardsOpen = new HashMap<>(other.cardsOpen);
		this.trainPile = new HashMap<>(other.trainPile);
		this.playerPointsMap = new HashMap<>(other.playerPointsMap);
		this.isFinalTurn = other.isFinalTurn;
		this.currentPlayer = other.currentPlayer;
		this.currentMoveType = other.currentMoveType;
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
