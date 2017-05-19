package moves;

public class Move1DrawTicket extends Move1 {

	public Move1Result accept(Move1Visitor v) {
		return v.visit(this);
	}

	@Override
	public String toString() {
		return "DrawTickets";
	}
}
