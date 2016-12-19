package Interface;

import java.io.File;
import java.util.Map;

/**
 * Interface defined for process
 * @author Ravi vadla
 *
 */
public interface RegexTextReplacement {
	public  Map processFile(File file, String fileAcceptPattern, String regexPattern, 
			String replacement, Map occuranceMap);
}
