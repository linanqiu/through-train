package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import engine.Track.TrackType;

public class StateManager {
	public static void readData(State state, String path) throws IOException {
		Optional<Path> stationsPath = Files.find(Paths.get(path), 1, (p, n) -> p.toString().contains("stations.csv"))
				.findFirst();
		if (!stationsPath.isPresent()) {
			throw new IOException();
		}
		setupStations(state, stationsPath.get());

		Optional<Path> ticketsPath = Files.find(Paths.get(path), 1, (p, n) -> p.toString().contains("tickets.csv"))
				.findFirst();
		if (!ticketsPath.isPresent()) {
			throw new IOException();
		}
		setupTickets(state, ticketsPath.get());

		Optional<Path> cardsPath = Files.find(Paths.get(path), 1, (p, n) -> p.toString().contains("cards.csv"))
				.findFirst();
		if (!cardsPath.isPresent()) {
			throw new IOException();
		}
		setupCards(state, cardsPath.get());
	}

	private static void setupStations(State state, Path stationsPath) throws IOException {
		state.stationTrackMap = new HashMap<>();
		state.stationNameMap = new HashMap<>();

		List<String> data = Files.lines(stationsPath).collect(Collectors.toList());
		int index = 0;

		for (String line : data) {
			String[] elements = line.trim().split(",");
			String stationNameA = elements[0].trim().toUpperCase();
			String stationNameB = elements[1].trim().toUpperCase();
			if (!state.stationNameMap.containsKey(stationNameA)) {
				Station station = new Station(stationNameA);
				state.stationNameMap.put(stationNameA, station);
				state.stationTrackMap.put(station, new LinkedList<>());
			}
			if (!state.stationNameMap.containsKey(stationNameB)) {
				Station station = new Station(stationNameB);
				state.stationNameMap.put(stationNameB, station);
				state.stationTrackMap.put(station, new LinkedList<>());
			}
			Station stationA = state.stationNameMap.get(stationNameA);
			Station stationB = state.stationNameMap.get(stationNameB);
			Track track = new Track(Extensions.toTrackType(elements[3]), Integer.parseInt(elements[2]), stationA,
					stationB, index++);
			state.stationTrackMap.get(stationA).add(track);
			state.stationTrackMap.get(stationB).add(track);
		}
	}

	private static void setupTickets(State state, Path ticketsPath) throws IOException {
		state.playerTicketMap = new HashMap<>();
		state.stationTicketMap = new HashMap<>();

		List<String> data = Files.lines(ticketsPath).collect(Collectors.toList());

		LinkedList<Ticket> ticketList = new LinkedList<>();

		for (String line : data) {
			String[] elements = line.trim().split(",");
			String stationNameA = elements[0].trim().toUpperCase();
			String stationNameB = elements[1].trim().toUpperCase();

			Station stationA = state.stationNameMap.get(stationNameA);
			Station stationB = state.stationNameMap.get(stationNameB);

			Ticket ticket = new Ticket(stationA, stationB, Integer.parseInt(elements[2]));

			if (!state.stationTicketMap.containsKey(stationA)) {
				state.stationTicketMap.put(stationA, new LinkedList<>());
			}
			if (!state.stationTicketMap.containsKey(stationB)) {
				state.stationTicketMap.put(stationB, new LinkedList<>());
			}

			state.stationTicketMap.get(stationA).add(ticket);
			state.stationTicketMap.get(stationB).add(ticket);

			ticketList.add(ticket);
		}

		Collections.shuffle(ticketList);

		state.ticketDrawPile = ticketList;
	}

	private static void setupCards(State state, Path cardsPath) throws IOException {
		state.cardsDrawPile = new HashMap<>();
		state.playerCardMap = new HashMap<>();
		state.cardsOpen = new HashMap<>();

		List<String> data = Files.lines(cardsPath).collect(Collectors.toList());

		for (String line : data) {
			String[] elements = line.trim().split(",");
			TrackType trackType = Extensions.toTrackType(elements[0].trim());
			int count = Integer.parseInt(elements[1].trim());
			state.cardsDrawPile.put(trackType, count);
			state.cardsOpen.put(trackType, 0);
		}

		// open cards
		for (int i = 0; i < 5; i++) {
			TrackType nextCard = Extensions.randomDraw(state.cardsDrawPile);
			Extensions.transfer(state.cardsDrawPile, state.cardsOpen, nextCard);
		}
	}

	public static void setupPlayer(State state, Player player) {
		// tickets
		if (state.playerTicketMap == null) {
			state.playerTicketMap = new HashMap<>();
		}
		state.playerTicketMap.put(player, new LinkedList<Ticket>());
		for (int i = 0; i < 3; i++) {
			Ticket ticket = state.ticketDrawPile.removeFirst();
			state.playerTicketMap.get(player).add(ticket);
		}

		// cards
		if (state.playerCardMap == null) {
			state.playerCardMap = new HashMap<>();
		}
		state.playerCardMap.put(player, new HashMap<>());
		for (TrackType t : state.cardsDrawPile.keySet()) {
			state.playerCardMap.get(player).put(t, 0);
		}
		for (int i = 0; i < 4; i++) {
			TrackType nextCard = Extensions.randomDraw(state.cardsDrawPile);
			Extensions.transfer(state.cardsDrawPile, state.playerCardMap.get(player), nextCard);
		}

		// trains
		if (state.trainPile == null) {
			state.trainPile = new HashMap<>();
		}
		state.trainPile.put(player, 50);

		// points
		if (state.playerPointsMap == null) {
			state.playerPointsMap = new HashMap<>();
		}
		state.playerPointsMap.put(player, 0);
	}

	public static boolean finalTurnCheck(State state) {
		if (state.isFinalTurn) {
			return true;
		}
		for (Entry<Player, Integer> entry : state.trainPile.entrySet()) {
			if (entry.getValue() <= 2) {
				if (!state.isFinalTurn) {
					state.isFinalTurn = true;
					return false;
				}
			}
		}
		return false;
	}
	
	public static void turnCycle(State state, List<Player> players) {
		// TODO
	}
}
