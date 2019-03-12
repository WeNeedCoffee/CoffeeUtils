package coffee.weneed.utils.events;

public interface IEvent {

	void cancel();

	int getType();

	boolean isCancelled();
}
