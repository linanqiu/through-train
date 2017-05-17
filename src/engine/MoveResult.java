package engine;

public class MoveResult {
	private boolean isValid;
	private boolean hasNextMove;

	public MoveResult(boolean isValid, boolean hasNextMove) {
		this.isValid = isValid;
		this.hasNextMove = hasNextMove;
	}

	public boolean isValid() {
		return isValid;
	}

	public boolean hasNextMove() {
		return hasNextMove;
	}
}
