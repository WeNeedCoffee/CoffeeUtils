package coffee.weneed.utils;

//TODO source
public enum Logger {
	PLUS {
		@Override
		public void log(Object object) {
			System.out.println("[+] " + object);
		}
	},
	NEGATIVE {
		@Override
		public void log(Object object) {
			System.err.println("[-] " + object);
		}
	},
	NULL {
		@Override
		public void log(Object object) {
			System.out.println("[!] " + object);
		}
	};

	public abstract void log(Object object);

	public void log(String format, Object... args) {
		log(String.format(format, args));
	}
}
