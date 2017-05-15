package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

		List<Ticket> ticketList = new LinkedList<>();

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

		List<String> data = Files.lines(cardsPath).collect(Collectors.toList());

		for (String line : data) {
			String[] elements = line.trim().split(",");
			TrackType trackType = Extensions.toTrackType(elements[0].trim());
			int count = Integer.parseInt(elements[1].trim());
			state.cardsDrawPile.put(trackType, count);
		}
	}

	private static void setupPlayer(State state, Player player) {
		// TODO
	}
}