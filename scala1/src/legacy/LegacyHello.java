package legacy;

import sandbox.Hello;

public class LegacyHello {
	public static void staticHello(String name) {
		System.out.println("hello, " + name);
	}
	
	/**
	 * Javaからこんにちは
	 * @param nom フランス人の名前
	 */
	public void disBonjour(String nom) {
		System.out.println("Bonjour, " + nom);
	}
	
	public void sayHello() {
		new Hello().hello("Duke");
	}
}
