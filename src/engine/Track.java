package engine;

import players.Player;

public class Track {
	public TrackType type;
	public int length;
	public UnorderedPair<Station> stations;

	// unique identifier
	public int id;

	public Player player;

	public Track(TrackType type, int length, Station station1, Station station2, int id) {
		this.type = type;
		this.length = length;
		stations = new UnorderedPair<Station>(station1, station2);
		this.id = id;
	}

	public void claim(Player player) {
		if (this.player == null) {
			this.player = player;
		}
		throw new IllegalArgumentException(
				String.format("Track already claimed. Can't be claimed by player %s", player));
	}

	public boolean isUnclaimed() {
		return player == null;
	}

	public Player owner() {
		return player;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (other.id == id) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("@%d TRA(%s -> %s) %s %d", id, stations.a, stations.b, type, length);
	}

	public enum TrackType {
		EMPTY, WHITE, BLUE, PINK, GREEN, YELLOW, RED, BLACK, ORANGE
	}
}
