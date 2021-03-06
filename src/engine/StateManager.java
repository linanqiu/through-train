package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import engine.Track.TrackType;
import moves.Move.MoveType;
import moves.Move1;
import moves.Move1DrawCard;
import moves.Move1DrawTicket;
import moves.Move1DrawTicketResult;
import moves.Move1Result;
import moves.Move1Track;
import moves.Move1Visitor;
import moves.Move2;
import moves.Move2DrawCard;
import moves.Move2Result;
import moves.Move2ReturnTicket;
import moves.Move2Visitor;
import players.Player;

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
		state.tracks = new LinkedList<>();

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
			state.tracks.add(track);
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

	public static boolean turnCycle(State state, List<Player> players) {
		for (Player player : players) {
			singleTurn(state, player);

			if (finalTurnCheck(state)) {
				System.out.println("Declaring final turn");
				int currentPlayerPosition = players.indexOf(player);
				ArrayList<Player> playersArrayList = new ArrayList<Player>(players);

				for (int i = 1; i < players.size() + 1; i++) {
					int index = (currentPlayerPosition + i) % players.size();
					Player playerAtIndex = playersArrayList.get(index);
					singleTurn(state, playerAtIndex);
				}
				return true;
			}
		}
		return false;
	}

	private static void singleTurn(State state, Player player) {
		state.currentPlayer = player;
		state.currentMoveType = MoveType.MOVE1;
		Move1Result result1;
		do {
			Move1 move1 = player.getMove1(state);
			result1 = processMove(state, player, move1);
			System.out.println(String.format("%s: %s", player, result1));
		} while (!result1.success);

		Move2Result result2;
		state.currentMoveType = MoveType.MOVE2;
		if (!ValidMoves.calculateValidMove2s(state, player, result1).isEmpty()) {
			do {
				Move2 move2 = player.getMove2(state, result1);
				result2 = processMove(state, player, move2, result1);
				System.out.println(String.format("%s: %s", player, result2));
			} while (!result2.success);
		}
	}

	public static Move1Result processMove(State state, Player player, Move1 move) {
		Move1Maker visitor = new Move1Maker(state, player);
		Move1Result result = move.accept(visitor);
		return result;
	}

	public static Move2Result processMove(State state, Player player, Move2 move, Move1Result result1) {
		Move2Maker visitor = new Move2Maker(state, player);
		Move2Result result = move.accept(visitor, result1);
		return result;
	}

	private static class Move1Maker implements Move1Visitor {

		private State state;
		private Player player;

		public Move1Maker(State state, Player player) {
			this.state = state;
			this.player = player;
		}

		@Override
		public Move1Result visit(Move1Track move) {
			Track track = move.track;

			// cannot claim claimed track
			if (!track.isUnclaimed()) {
				return Move1Result.failure(move);
			}

			// ensure enough cards
			HashMap<TrackType, Integer> playerCards = state.playerCardMap.get(player);
			int wildcardCount = playerCards.getOrDefault(TrackType.EMPTY, 0);
			int sameCount = playerCards.getOrDefault(track.type, 0);
			int totalMatchCardCount = wildcardCount + sameCount;
			if (track.length > totalMatchCardCount) {
				return Move1Result.failure(move);
			}

			// ensure enough trains
			int trainCount = state.trainPile.get(player);
			if (track.length > trainCount) {
				return Move1Result.failure(move);
			}

			if (sameCount > track.length) {
				Extensions.transfer(state.playerCardMap.get(player), state.cardsDrawPile, track.type, track.length);
			} else {
				Extensions.transfer(state.playerCardMap.get(player), state.cardsDrawPile, track.type, sameCount);
				Extensions.transfer(state.playerCardMap.get(player), state.cardsDrawPile, TrackType.EMPTY,
						track.length - sameCount);
			}

			Extensions.decrement(state.trainPile, player, track.length);

			track.claim(player);

			return Move1Result.success(move);
		}

		@Override
		public Move1Result visit(Move1DrawTicket move) {
			int ticketsLeftCount = state.ticketDrawPile.size();
			if (ticketsLeftCount <= 0) {
				return Move1DrawTicketResult.failure(move);
			}

			HashSet<Ticket> ticketsDrawn = new HashSet<>();
			for (int i = 0; i < Math.min(ticketsLeftCount, 3); i++) {
				Ticket ticket = state.ticketDrawPile.removeFirst();
				state.playerTicketMap.get(player).add(ticket);
				ticketsDrawn.add(ticket);
			}
			return Move1DrawTicketResult.success(move, ticketsDrawn);
		}

		@Override
		public Move1Result visit(Move1DrawCard move) {
			if (move.fromPile) {
				// pile has enough cards
				if (Extensions.countMap(state.cardsDrawPile) <= 0) {
					return Move1Result.failure(move);
				}
				TrackType type = Extensions.randomDraw(state.cardsDrawPile);
				Extensions.transfer(state.cardsDrawPile, state.playerCardMap.get(player), type);
				return Move1Result.success(move);
			} else {
				// open has enough cards
				TrackType type = move.trackType;
				if (state.cardsOpen.get(type) <= 0) {
					return Move1Result.failure(move);
				}
				Extensions.transfer(state.cardsOpen, state.playerCardMap.get(player), type);
				// replenish
				if (Extensions.countMap(state.cardsDrawPile) > 0) {
					TrackType nextType = Extensions.randomDraw(state.cardsDrawPile);
					Extensions.transfer(state.cardsDrawPile, state.cardsOpen, nextType);
				}
				return Move1Result.success(move);
			}
		}
	}

	private static class Move2Maker implements Move2Visitor {

		private State state;
		private Player player;

		public Move2Maker(State state, Player player) {
			this.state = state;
			this.player = player;
		}

		@Override
		public Move2Result visit(Move2ReturnTicket move, Move1Result prevResult) {
			if (move.tickets.size() > 2) {
				return Move2Result.failure(move);
			}

			if (!(prevResult instanceof Move1DrawTicketResult)) {
				throw new IllegalArgumentException("Move1 not Move1DrawTicket");
			}

			Move1DrawTicketResult prevMoveDrawTicket = (Move1DrawTicketResult) prevResult;

			for (Ticket ticket : move.tickets) {
				if (!prevMoveDrawTicket.tickets.contains(ticket)) {
					throw new IllegalArgumentException("You tried to return a card that you didn't draw");
				}
				state.playerTicketMap.get(player).remove(ticket);
				state.ticketDrawPile.addLast(ticket);
			}
			return Move2Result.success(move);
		}

		@Override
		public Move2Result visit(Move2DrawCard move, Move1Result prevResult) {

			if (move.fromPile) {
				// pile has enough cards
				if (Extensions.countMap(state.cardsDrawPile) <= 0) {
					return Move2Result.failure(move);
				}
				TrackType type = Extensions.randomDraw(state.cardsDrawPile);
				Extensions.transfer(state.cardsDrawPile, state.playerCardMap.get(player), type);
				return Move2Result.success(move);
			} else {
				// open has enough cards
				TrackType type = move.trackType;

				// can't draw wildcard again
				if (type == TrackType.EMPTY) {
					return Move2Result.failure(move);
				}

				if (state.cardsOpen.get(type) <= 0) {
					return Move2Result.failure(move);
				}
				Extensions.transfer(state.cardsOpen, state.playerCardMap.get(player), type);
				// replenish
				if (Extensions.countMap(state.cardsDrawPile) > 0) {
					TrackType nextType = Extensions.randomDraw(state.cardsDrawPile);
					Extensions.transfer(state.cardsDrawPile, state.cardsOpen, nextType);
				}
				return Move2Result.success(move);
			}
		}
	}
}
