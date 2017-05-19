package engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import engine.Track.TrackType;
import moves.Move.MoveType;
import moves.Move1;
import moves.Move1DrawCard;
import moves.Move1DrawTicketResult;
import moves.Move1Result;
import moves.Move2;
import players.Player;

public class ValidMoves {
	public static List<Move1> calculateValidMove1s(State state, Player player) {
		// not you
		if (!state.currentPlayer.equals(player)) {
			return new LinkedList<>();
		}
		// not this move type
		if (state.currentMoveType != MoveType.MOVE1) {
			return new LinkedList<>();
		}

		List<Move1> moves = new LinkedList<>();

		// draw open card
		moves.addAll(getValidOpenCardDraws1(state));

		// draw from pile
		moves.addAll(getValidPileCardDraws(state));

		// draw tickets
		moves.addAll(getValidTicketDraws1(state));

		// lay tracks
		moves.addAll(getValidTrackClaims(state, player));

		return moves;
	}

	public static List<Move2> calculateValidMove2s(State state, Player player, Move1Result moveResult) {
		// not you
		if (!state.currentPlayer.equals(player)) {
			return new LinkedList<>();
		}
		// not this move type
		if (state.currentMoveType != MoveType.MOVE2) {
			return new LinkedList<>();
		}

		Move1 prevMove = moveResult.prevMove;

		List<Move2> moves = new LinkedList<>();

		if (prevMove instanceof Move1DrawCard) {
			Move1DrawCard prevMoveDrawCard = (Move1DrawCard) prevMove;
			// if drew wild card previous move, cannot draw anything this time
			if (prevMoveDrawCard.trackType == TrackType.EMPTY) {
				return moves;
			}
			// did not draw wild card in previous move.

			// open
			moves.addAll(getValidOpenCardDraws2(state));

			// pile
			moves.addAll(getValidPileCardDraws2(state));

			return moves;
		}
		if (moveResult instanceof Move1DrawTicketResult) {
			Move1DrawTicketResult moveResultTicket = (Move1DrawTicketResult) moveResult;
			HashSet<Ticket> tickets = moveResultTicket.tickets;

			moves.addAll(getValidReturnTickets(tickets));
		}

		return moves;
	}

	private static List<Move1> getValidPileCardDraws(State state) {
		if (state.cardsDrawPile.values().stream().anyMatch(i -> i > 0)) {
			List<Move1> list = new LinkedList<>();
			list.add(Move1.drawFromPile());
			return list;
		}
		return new LinkedList<>();
	}

	private static List<Move1> getValidOpenCardDraws1(State state) {
		return state.cardsOpen.entrySet().stream().filter(e -> e.getValue() > 0)
				.map(e -> Move1.drawFromOpen(e.getKey())).collect(Collectors.toList());
	}

	private static List<Move1> getValidTicketDraws1(State state) {
		if (state.ticketDrawPile.size() > 0) {
			List<Move1> list = new LinkedList<>();
			list.add(Move1.drawTicket());
			return list;
		}
		return new LinkedList<>();
	}

	private static List<Move1> getValidTrackClaims(State state, Player player) {
		HashMap<TrackType, Integer> playerCardCounts = state.playerCardMap.get(player);
		int wildcardCount = playerCardCounts.getOrDefault(TrackType.EMPTY, 0);
		int trainCount = state.trainPile.get(player);

		return state.tracks.stream().filter(t -> t.isUnclaimed())
				.filter(t -> wildcardCount + playerCardCounts.getOrDefault(t, 0) >= t.length)
				.filter(t -> trainCount >= t.length).map(t -> Move1.claimTrack(t)).collect(Collectors.toList());
	}

	private static List<Move2> getValidPileCardDraws2(State state) {
		if (state.cardsDrawPile.values().stream().anyMatch(i -> i > 0)) {
			List<Move2> list = new LinkedList<>();
			list.add(Move2.drawFromPile());
			return list;
		}
		return new LinkedList<>();
	}

	private static List<Move2> getValidOpenCardDraws2(State state) {
		return state.cardsOpen.entrySet().stream().filter(e -> e.getValue() > 0)
				.map(e -> Move2.drawFromOpen(e.getKey())).collect(Collectors.toList());
	}

	private static List<Move2> getValidReturnTickets(HashSet<Ticket> tickets) {
		List<Move2> tickets1 = tickets.stream().map(t -> Move2.returnTickets(t)).collect(Collectors.toList());
		// too lazy to write general function since we stop at two
		List<Move2> tickets2 = new LinkedList<>();
		if (tickets.size() > 1) {
			for (Ticket ticket1 : tickets) {
				for (Ticket ticket2 : tickets) {
					if (!ticket1.equals(ticket2)) {
						HashSet<Ticket> ticketsPair = new HashSet<>();
						ticketsPair.add(ticket1);
						ticketsPair.add(ticket2);
						tickets2.add(Move2.returnTickets(ticketsPair));
					}
				}
			}
		}

		tickets1.addAll(tickets2);

		return tickets1;
	}
}
