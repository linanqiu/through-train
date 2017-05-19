package players;

import engine.State;
import moves.Move1;
import moves.Move1Result;
import moves.Move2;

public abstract class Player {
	public String name;

	public Player(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	// state should be a snapshot, not reference
	public abstract Move1 getMove1(State state);

	public abstract Move2 getMove2(State state, Move1Result prevMoveResult);
}
