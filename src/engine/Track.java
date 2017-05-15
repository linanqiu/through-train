package engine;

public class Track {
	public TrackType type;
	public int length;
	UnorderedPair<Station> stations;

	// unique identifier
	public int id;

	public Track(TrackType type, int length, Station station1, Station station2, int id) {
		this.type = type;
		this.length = length;
		stations = new UnorderedPair<Station>(station1, station2);
		this.id = id;
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
}
