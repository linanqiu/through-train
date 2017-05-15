package engine;

import java.util.HashMap;
import java.util.List;

public class State {
	// stations and tracks
	public HashMap<String, Station> stationNameMap;
	public HashMap<Station, List<Track>> stationTrackMap;

	// tickets
	public HashMap<Player, Ticket> playerTicketMap;
	public HashMap<Station, List<Ticket>> stationTicketMap;
	public List<Ticket> ticketDrawPile;

	// cards
	public HashMap<Player, HashMap<TrackType, Integer>> playerCardMap;
	public HashMap<TrackType, Integer> cardsDrawPile;

	// trains
	public HashMap<Player, Integer> trainPile;

	public String toString() {
		return stationTrackMap.toString() + "\n" + stationTicketMap.toString() + "\n" + cardsDrawPile.toString();
	}
}
