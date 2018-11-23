package coffee.weneed.utils.events;

public interface IEvent {

	public void cancel();

	public int getType();

	public boolean isCancelled();
}
