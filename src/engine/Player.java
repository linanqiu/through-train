package engine;

import engine.Move1.Move1DrawCard;
import engine.Move2.Move2DrawCard;

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
	public Move1 getMove1(State state) {
		// TODO
		return new Move1DrawCard();
	}

	public Move2 getMove2(State state) {
		// TODO
		return new Move2DrawCard();
	}
}
