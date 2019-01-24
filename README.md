# Unilimited-Integer
A Java program that calculates ax²+bx+c where all numbers are represented as Strings, with no size limitation. Program developed for the course Computer Science at Hochschule Ulm.

For example:
		
    system.out.println("Calculate ax² + bx + c: ");
    
		UnlimitedInteger a = new UnlimitedInteger("-189898");
		UnlimitedInteger b = new UnlimitedInteger("5565161215");
		UnlimitedInteger c = new UnlimitedInteger("-18989");
		UnlimitedInteger x = new UnlimitedInteger("-11998845232323222");

		UnlimitedInteger result = a.times(x.times(x)).plus(b.times(x)).plus(c);

Output:-27340049339558718358087225680055162751
