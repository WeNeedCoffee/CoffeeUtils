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
	 * @param url         the url
	 * @param userAgent  the user agent
	 * @param captchaKey the captcha key
	 * @param publicKey  the public key
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String funCaptcha(String url, String userAgent, String captchaKey, String publicKey) throws Exception {
		return funCaptcha(url, userAgent, captchaKey, publicKey, ProxyTypeOption.NONE, null, -1, null, null);
	}

	/**
	 * Fun captcha.
	 *
	 * @param url         the url
	 * @param userAgent  the user agent
	 * @param captchakey the captcha key
	 * @param publicKey  the public key
	 * @param proxy       the proxy
	 * @param proxyIP    the proxy ip
	 * @param proxyPort  the proxy port
	 * @param proxyUser  the proxy user
	 * @param proxyPass  the proxy pass
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String funCaptcha(String url, String userAgent, String captchaKey, String publicKey, ProxyTypeOption proxy, String proxyIP, int proxyPort, String proxyUser, String proxyPass) throws Exception {
		DebugHelper.setVerboseMode(true);
		FunCaptcha api = new FunCaptcha();
		api.setClientKey(captchaKey);
		api.setWebsiteUrl(new URL(url));
		api.setWebsitePublicKey(publicKey);
		api.setUserAgent(userAgent);
		if (!proxy.equals(ProxyTypeOption.NONE)) {
			api.setProxyType(AnticaptchaBase.ProxyTypeOption.valueOf(proxy.toString()));
			api.setProxyAddress(proxyIP);
			api.setProxyPort(proxyPort);
			api.setProxyLogin(proxyUser);
			api.setProxyPassword(proxyPass);
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
