package com.upmc.twister.services;

public class Operation {
	public static double calcul(double a, double b, String op) {
		if (op.equals("+"))
			return add(a, b);

		else if (op.equals("-"))
			return add(a, -b);

		else if (op.equals("*"))
			return multiply(a, b);
		
		else if (op.equals("/"))
			return divide(a, b);
		
		return 0.0;
	}

	private static double add(double a, double b) {
		return a + b;
	}

	private static double multiply(double a, double b) {
		return a * b;
	}

	private static double divide(double a, double b) {
		return a / b;
	}
	
}
