package moves;

public class Move2Result extends MoveResult {

	public Move2 prevMove;
	public boolean success;

	private Move2Result(Move2 prevMove, boolean success) {
		this.prevMove = prevMove;
		this.success = success;
	}

	@Override
	public boolean isSuccessful() {
		return success;
	}

	public static Move2Result success(Move2 prevMove) {
		return new Move2Result(prevMove, true);
	}

	public static Move2Result failure(Move2 prevMove) {
		return new Move2Result(prevMove, false);
	}

	@Override
	public String toString() {
		return "Move2 " + success + " " + prevMove;
	}
}
