package players;

import engine.State;
import moves.Move1;
import moves.Move1Result;
import moves.Move2;

public class PlayerDummy extends Player {

	public PlayerDummy(String name) {
		super(name);
	}

	@Override
	public Move1 getMove1(State state) {
		return Move1.drawFromPile();
	}

	@Override
	public Move2 getMove2(State state, Move1Result prevMoveResult) {
		return Move2.drawFromPile();
	}
}
