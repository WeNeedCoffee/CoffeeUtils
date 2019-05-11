package coffee.weneed.utils.events;

// TODO: Auto-generated Javadoc
/**
 * The Interface IEvent.
 */
public interface IEvent {

	/**
	 * Cancel.
	 */
	void cancel();

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	int getType();

	/**
	 * Checks if is cancelled.
	 *
	 * @return true, if is cancelled
	 */
	boolean isCancelled();
}
