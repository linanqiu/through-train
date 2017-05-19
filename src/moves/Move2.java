package moves;

import java.util.Collection;
import java.util.HashSet;

import engine.Ticket;
import engine.Track.TrackType;

public abstract class Move2 extends Move {

	public abstract Move2Result accept(Move2Visitor visitor);

	public MoveType getMoveType() {
		return MoveType.MOVE2;
	}

	public static Move2DrawCard drawFromOpen(TrackType trackType) {
		return new Move2DrawCard(trackType);
	}

	public static Move2DrawCard drawFromPile() {
		return new Move2DrawCard();
	}

	public static Move2ReturnTicket returnTickets(Collection<Ticket> tickets) {
		return new Move2ReturnTicket(new HashSet<Ticket>(tickets));
	}

	public static Move2ReturnTicket returnTickets(Ticket ticket) {
		HashSet<Ticket> set = new HashSet<>();
		set.add(ticket);
		return new Move2ReturnTicket(set);
	}
}
