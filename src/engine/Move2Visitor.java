package engine;

import engine.Move2.Move2DrawCard;
import engine.Move2.Move2ReturnTicket;

public interface Move2Visitor {
	public MoveResult visit(Move2ReturnTicket move);

	public MoveResult visit(Move2DrawCard move);
}
