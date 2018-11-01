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

public abstract class AbstractScriptManager {

	private static final ScriptEngineManager sem = new ScriptEngineManager();

	protected static Invocable getInvocable(Path path) {
		ScriptEngine se = AbstractScriptManager.getScriptEngine(path);
		return se == null ? null : (Invocable) se;
	}

	protected static ScriptEngine getScriptEngine(Path path) {
		FileReader fr = null;
		try {
			File script = path.toFile();
			if (!script.exists()) {
				return null;
			}
			ScriptEngine engine = AbstractScriptManager.sem.getEngineByName("nashorn");
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
