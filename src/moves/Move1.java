package moves;

import engine.Track;
import engine.Track.TrackType;

public abstract class Move1 extends Move {
	public abstract Move1Result accept(Move1Visitor v);

	public MoveType getMoveType() {
		return MoveType.MOVE1;
	}

	public static Move1DrawCard drawFromOpen(TrackType trackType) {
		return new Move1DrawCard(trackType);
	}

	public static Move1DrawCard drawFromPile() {
		return new Move1DrawCard();
	}

	public static Move1DrawTicket drawTicket() {
		return new Move1DrawTicket();
	}

	public static Move1Track claimTrack(Track track) {
		return new Move1Track(track);
	}
}
