package engine;

public interface Move1 {
	public MoveResult accept(Move1Visitor v);

	public class Move1Track implements Move1 {
		public Track track;

		public Move1Track(Track track) {
			this.track = track;
		}

		public MoveResult accept(Move1Visitor v) {
			return v.visit(this);
		}
	}

	public class Move1DrawTicket implements Move1 {
		public MoveResult accept(Move1Visitor v) {
			return v.visit(this);
		}
	}

	public class Move1DrawCard implements Move1 {
		public MoveResult accept(Move1Visitor v) {
			return v.visit(this);
		}
	}
}
