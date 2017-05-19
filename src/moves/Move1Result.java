package moves;

public class Move1Result extends MoveResult {

	public Move1 prevMove;
	public boolean success;

	protected Move1Result(Move1 prevMove, boolean success) {
		this.prevMove = prevMove;
		this.success = success;
	}

	@Override
	public boolean isSuccessful() {
		return success;
	}
	
	public static Move1Result success(Move1 prevMove) {
		return new Move1Result(prevMove, true);
	}

	public static Move1Result failure(Move1 prevMove) {
		return new Move1Result(prevMove, false);
	}
}
