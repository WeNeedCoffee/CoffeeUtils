package coffee.weneed.utils.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class EventManager.
 */
public class EventManager {

	/** The handlers. */
	private Map<Integer, List<IEventHandler>> handlers = new HashMap<>();

	/** The before handlers. */
	private Map<Integer, List<IBeforeEventHandler>> beforeHandlers = new HashMap<>();

	/**
	 * Fire event.
	 *
	 * @param event the event
	 */
	public void fireEvent(IEvent event) {
		for (IBeforeEventHandler e : beforeHandlers.get(event.getType())) {
			e.HandleBeforeEvent(event);
		}
		if (!event.isCancelled()) {
			for (IEventHandler e : handlers.get(event.getType())) {
				e.handleEvent(event);
			}
		}
	}

	/**
	 * Gets the before handlers.
	 *
	 * @param event the event
	 * @return the before handlers
	 */
	public List<IBeforeEventHandler> getBeforeHandlers(int event) {
		return beforeHandlers.get(event);
	}

	/**
	 * Gets the handlers.
	 *
	 * @return the handlers
	 */
	public Map<Integer, List<IEventHandler>> getHandlers() {
		return handlers;
	}

	/**
	 * Gets the handlers.
	 *
	 * @param event the event
	 * @return the handlers
	 */
	public List<IEventHandler> getHandlers(int event) {
		return handlers.get(event);
	}

	/**
	 * Register before handler.
	 *
	 * @param event   the event
	 * @param handler the handler
	 */
	public void registerBeforeHandler(int event, IBeforeEventHandler handler) {
		if (beforeHandlers.get(event) == null) {
			beforeHandlers.put(event, new ArrayList<IBeforeEventHandler>());
		}

		if (!beforeHandlers.get(event).contains(handler)) {
			beforeHandlers.get(event).add(handler);
		}
	}

	/**
	 * Register handler.
	 *
	 * @param event   the event
	 * @param handler the handler
	 */
	public void registerHandler(int event, IEventHandler handler) {
		if (handlers.get(event) == null) {
			handlers.put(event, new ArrayList<IEventHandler>());
		}

		if (!handlers.get(event).contains(handler)) {
			handlers.get(event).add(handler);
		}
	}

	/**
	 * Removes the before handler.
	 *
	 * @param event   the event
	 * @param handler the handler
	 */
	public void removeBeforeHandler(int event, IBeforeEventHandler handler) {
		if (beforeHandlers.get(event) != null && beforeHandlers.get(event).contains(handler)) {
			beforeHandlers.get(event).remove(handler);
		}

	}

	/**
	 * Removes the handler.
	 *
	 * @param event   the event
	 * @param handler the handler
	 */
	public void removeHandler(int event, IEventHandler handler) {
		if (handlers.get(event) != null && handlers.get(event).contains(handler)) {
			handlers.get(event).remove(handler);
		}

	}

}
