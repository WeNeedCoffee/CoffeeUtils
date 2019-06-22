package coffee.weneed.utils.scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractScriptManager.
 */
public abstract class AbstractScriptManager {

	/** The Constant sem. */
	private static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

	/**
	 * Gets the invocable.
	 *
	 * @param path the path
	 * @return the invocable
	 */
	protected static Invocable getInvocable(Path path) {
		ScriptEngine se = AbstractScriptManager.getScriptEngine(path);
		return se == null ? null : (Invocable) se;
	}

	/**
	 * Gets the script engine.
	 *
	 * @param path the path
	 * @return the script engine
	 */
	protected static ScriptEngine getScriptEngine(Path path) {
		FileReader fr = null;
		try {
			File script = path.toFile();
			if (!script.exists())
				return null;
			ScriptEngine engine = AbstractScriptManager.SCRIPT_ENGINE_MANAGER.getEngineByName("nashorn");
			fr = new FileReader(script);
			engine.eval("load('nashorn:mozilla_compat.js')");
			engine.eval(fr);
			return engine;
		} catch (ScriptException | FileNotFoundException e) {
			return null;
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
