package moves;

public interface Move2Visitor {
	public Move2Result visit(Move2ReturnTicket move, Move1Result prevMoveResult);

	public Move2Result visit(Move2DrawCard move, Move1Result prevMoveResult);
}
