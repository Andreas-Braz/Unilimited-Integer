package UnlimitedInteger;

public class TUnlimitedInteger {

	public static void main(String[] args) {
		
		System.out.println("Calculate ax² + bx + c: ");
		

		UnlimitedInteger a = new UnlimitedInteger("-10");
		UnlimitedInteger b = new UnlimitedInteger("9999999999999999999999999999900");
		UnlimitedInteger c = new UnlimitedInteger("-10");
		UnlimitedInteger x = new UnlimitedInteger("-10");

		UnlimitedInteger result = a.times(x.times(x)).plus(b.times(x)).plus(c);
		
		System.out.println("ax² + bx + c = " + result);

	}

}