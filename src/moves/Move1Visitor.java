package moves;

public interface Move1Visitor {
	public Move1Result visit(Move1Track move);

	public Move1Result visit(Move1DrawTicket move);

	public Move1Result visit(Move1DrawCard move);
}
