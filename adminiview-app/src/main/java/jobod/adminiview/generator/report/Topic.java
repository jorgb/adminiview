package jobod.adminiview.generator.report;

public class Topic implements Comparable<Topic> {

	private final int _id;
	private final String _name;
	private final int _count;
	
	public Topic(int id, String topicName, Integer value) {
		_id = id;
		_name = topicName;
		_count = value;
	}
	
	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}
	
	public int getCount() {
		return _count;
	}

	@Override
	public int compareTo(Topic topic) {
		return _name.compareTo(topic._name);
	}
}
