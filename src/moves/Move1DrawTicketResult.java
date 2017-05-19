package moves;

import java.util.HashSet;

import engine.Ticket;

public class Move1DrawTicketResult extends Move1Result {

	public HashSet<Ticket> tickets;
	public Move1 prevMove;
	public boolean success;

	private Move1DrawTicketResult(Move1 prevMove, boolean success, HashSet<Ticket> tickets) {
		super(prevMove, success);
		this.tickets = tickets;
	}

	public static Move1DrawTicketResult success(Move1 prevMove, HashSet<Ticket> tickets) {
		return new Move1DrawTicketResult(prevMove, true, tickets);
	}

	public static Move1DrawTicketResult failure(Move1 prevMove) {
		return new Move1DrawTicketResult(prevMove, false, null);
	}
}
