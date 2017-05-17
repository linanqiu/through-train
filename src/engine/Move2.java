package engine;

public interface Move2 {

	public MoveResult accept(Move2Visitor visitor);

	public class Move2ReturnTicket implements Move2 {
		public MoveResult accept(Move2Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public class Move2DrawCard implements Move2 {
		public MoveResult accept(Move2Visitor visitor) {
			return visitor.visit(this);
		}
	}
}
