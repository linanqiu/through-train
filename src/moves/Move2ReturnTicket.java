package moves;

import java.util.HashSet;

import engine.Ticket;

public class Move2ReturnTicket extends Move2 {

	public HashSet<Ticket> tickets;

	Move2ReturnTicket(HashSet<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Move2Result accept(Move2Visitor visitor, Move1Result prevMoveResult) {
		return visitor.visit(this, prevMoveResult);
	}

	@Override
	public String toString() {
		return "ReturnTickets=" + tickets.toString();
	}
}