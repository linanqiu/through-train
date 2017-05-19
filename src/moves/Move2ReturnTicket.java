package moves;

import java.util.HashSet;

import engine.Ticket;

public class Move2ReturnTicket extends Move2 {

	HashSet<Ticket> tickets;

	Move2ReturnTicket(HashSet<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Move2Result accept(Move2Visitor visitor) {
		return visitor.visit(this);
	}
}