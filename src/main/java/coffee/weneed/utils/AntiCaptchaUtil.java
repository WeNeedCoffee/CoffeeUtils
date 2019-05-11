package coffee.weneed.utils;

import java.net.URL;
import com.anti_captcha.AnticaptchaBase;
import com.anti_captcha.Api.FunCaptcha;
import com.anti_captcha.Helper.DebugHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AntiCaptchaUtil.
 */
public class AntiCaptchaUtil {
	
	/**
	 * The Enum ProxyTypeOption.
	 */
	public enum ProxyTypeOption {
		
		/** The http. */
		HTTP, 
 /** The socks4. */
 SOCKS4, 
 /** The socks5. */
 SOCKS5, 
 /** The none. */
 NONE
	}

	/**
	 * Fun captcha.
	 *
	 * @param url the url
	 * @param user_agent the user agent
	 * @param captcha_key the captcha key
	 * @param public_key the public key
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String funCaptcha(String url, String user_agent, String captcha_key, String public_key) throws Exception {
		return funCaptcha(url, user_agent, captcha_key, public_key, ProxyTypeOption.NONE, null, -1, null, null);
	}

	/**
	 * Fun captcha.
	 *
	 * @param url the url
	 * @param user_agent the user agent
	 * @param captcha_key the captcha key
	 * @param public_key the public key
	 * @param proxy the proxy
	 * @param proxy_ip the proxy ip
	 * @param proxy_port the proxy port
	 * @param proxy_user the proxy user
	 * @param proxy_pass the proxy pass
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String funCaptcha(String url, String user_agent, String captcha_key, String public_key, ProxyTypeOption proxy, String proxy_ip, int proxy_port, String proxy_user, String proxy_pass) throws Exception {
		DebugHelper.setVerboseMode(true);
		FunCaptcha api = new FunCaptcha();
		api.setClientKey(captcha_key);
		api.setWebsiteUrl(new URL(url));
		api.setWebsitePublicKey(public_key);
		api.setUserAgent(user_agent);
		if (!proxy.equals(ProxyTypeOption.NONE)) {
			api.setProxyType(AnticaptchaBase.ProxyTypeOption.valueOf(proxy.toString()));
			api.setProxyAddress(proxy_ip);
			api.setProxyPort(proxy_port);
			api.setProxyLogin(proxy_user);
			api.setProxyPassword(proxy_pass);
		}
		if (!api.createTask().booleanValue()) {
			throw new Exception("API v2 send failed. " + api.getErrorMessage());
		} else if (!api.waitForResult().booleanValue()) {
			throw new Exception("Could not solve the captcha." + api.getErrorMessage());
		} else {
			return api.getTaskSolution().getToken();
		}
	}
}
