package moves;

public interface Move2Visitor {
	public Move2Result visit(Move2ReturnTicket move);

	public Move2Result visit(Move2DrawCard move);
}
