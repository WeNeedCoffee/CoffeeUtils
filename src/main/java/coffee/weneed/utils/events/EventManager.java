package coffee.weneed.utils.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

	private Map<Integer, List<IEventHandler>> handlers = new HashMap<>();

	private Map<Integer, List<IBeforeEventHandler>> beforeHandlers = new HashMap<>();

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

	public List<IBeforeEventHandler> getBeforeHandlers(int event) {
		return beforeHandlers.get(event);
	}

	public Map<Integer, List<IEventHandler>> getHandlers() {
		return handlers;
	}

	public List<IEventHandler> getHandlers(int event) {
		return handlers.get(event);
	}

	public void registerBeforeHandler(int event, IBeforeEventHandler handler) {
		if (beforeHandlers.get(event) == null) {
			beforeHandlers.put(event, new ArrayList<IBeforeEventHandler>());
		}

		if (!beforeHandlers.get(event).contains(handler)) {
			beforeHandlers.get(event).add(handler);
		}
	}

	public void registerHandler(int event, IEventHandler handler) {
		if (handlers.get(event) == null) {
			handlers.put(event, new ArrayList<IEventHandler>());
		}

		if (!handlers.get(event).contains(handler)) {
			handlers.get(event).add(handler);
		}
	}

	public void removeBeforeHandler(int event, IBeforeEventHandler handler) {
		if (beforeHandlers.get(event) != null && beforeHandlers.get(event).contains(handler)) {
			beforeHandlers.get(event).remove(handler);
		}

	}

	public void removeHandler(int event, IEventHandler handler) {
		if (handlers.get(event) != null && handlers.get(event).contains(handler)) {
			handlers.get(event).remove(handler);
		}

	}

}
