package moves;

public abstract class Move {
	public enum MoveType {
		MOVE1, MOVE2
	}

	public abstract MoveType getMoveType();
}
