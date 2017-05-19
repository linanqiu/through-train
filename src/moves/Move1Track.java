package moves;

import engine.Track;

public class Move1Track extends Move1 {

	public Track track;

	Move1Track(Track track) {
		this.track = track;
	}

	@Override
	public Move1Result accept(Move1Visitor v) {
		return v.visit(this);
	}

	@Override
	public String toString() {
		return "ClaimTrack=" + track;
	}
}
