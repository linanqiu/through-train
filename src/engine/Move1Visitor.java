package engine;

import engine.Move1.Move1DrawCard;
import engine.Move1.Move1DrawTicket;
import engine.Move1.Move1Track;

public interface Move1Visitor {
	public MoveResult visit(Move1Track move);

	public MoveResult visit(Move1DrawTicket move);

	public MoveResult visit(Move1DrawCard move);
}
