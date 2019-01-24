
package UnlimitedInteger;

public class UnlimitedInteger {


	private String value;

	public UnlimitedInteger(String value) {
		
		// Checking parameter value for validity
		if (value.matches("[+-]?\\d+")) {
			this.value = value;
		} 
		else {
			throw new IllegalArgumentException();
		}
	}
		
		public String getValue() {
			return value;
		}
				
		public String toString() {
			return "UnlimitedInteger: " + value;
		}
		
		// plus and times method in OOP style
		
		public UnlimitedInteger plus(UnlimitedInteger op) {

			return new UnlimitedInteger(plus(value, op.getValue()));
		}
		
		public UnlimitedInteger times(UnlimitedInteger op) {	
			return new UnlimitedInteger(times(value, op.getValue()));
		}
				
		// Method that checks whether to use subtraction or addition
		private static String plus(String a, String b) {
			
			// if length of A or Be is null (string is blank) it ignores it and jumps to the next number.
			if (a.length() == 0) {
				return b;
			}

			if (b.length() == 0) {
				return a;
			}
			// Checks if the 1st character of the strings a and b is '-'
			boolean checkNegative1 = a.charAt(0) == '-';
			boolean checkNegative2 = b.charAt(0) == '-';
			String result = "";

			// If 1 positive and 1 negative do subtraction
			if (!checkNegative1 && checkNegative2) {
				result = preCheck(a, b);

				return result;
			}

			// If 1 negative and 1 positive do subtraction
			// Interesting observation for this case, negative number + larger positive
			// would output --
			// So the substring removes that as -- is equivalent to +
			if (checkNegative1 && !checkNegative2) {
				result = "-" + preCheck(a, b);
				if (result.charAt(0) == '-' && result.charAt(1) == '-') {
					result = result.substring(2);
				}
				// if starting with a negative number then adding numbers making the result 0
				// it will print -0 so the substring removes the 0
				if (result.charAt(0) == '-' && result.charAt(1) == '0') {
					result = result.substring(1);
				}
				return result;
			}

			// If both numbers are negative, treat as positive addition and put a "-" in
			// front
			if (checkNegative1 && checkNegative2) {
				result = "-" + (addition(a, b));
				return result;

			// If numbers aren't negative, regular addition
			} else {
				result = addition(a, b);
				return result;
			}

		}

		// Method that is required to perform subtraction
		// It checks the numbers and makes them compatible with the actual subtraction
		// method
		private static String preCheck(String a, String b) {

			// Remove the '+' or before we enter the arithmetic operation > exception
			// doesn't allow for this
			boolean isPositive1 = a.charAt(0) == '+';
			if (isPositive1) {
				a = a.substring(1);
			}
			boolean isPositive2 = b.charAt(0) == '+';
			if (isPositive2) {
				b = b.substring(1);
			}

			// Remove the '-' before we enter the arithmetic operation > exception doesn't
			// allow for this
			boolean isNegative1 = a.charAt(0) == '-';
			if (isNegative1) {
				a = a.substring(1);
			}
			boolean isNegative2 = b.charAt(0) == '-';
			if (isNegative2) {
				b = b.substring(1);
			}

			// This next block adds 0s to the shortest string until String length = longest
			// We removed the sign before prepending with 0s so avoid getting numbers such
			// as "0000-19"
			while (a.length() < largestStringLength(a, b)) {
				a = "0" + a;
			}
			while (b.length() < largestStringLength(a, b)) {
				b = "0" + b;
			}

			// Method bigger checks which string has a higher value
			// If a is larger than b it performs the subtraction of a - b
			// Else it performs the subtraction b - a
			if (isFirstBigger(a, b)) {
				return subtraction(a, b);
			}

			// Of course, simply switching the inputs is not the goal
			// A smaller number - a larger number is negative result
			// In the case of a switch it we add a "-" in front to correct this
			else
				return "-" + subtraction(b, a);

		}

		// Method for addition
		private static String addition(String a, String b) {

			if (a.length() == 0 || b.length() == 0) {
				throw new NumberFormatException();
			}

			// Remove the '+' or before we enter the arithmetic operation > exception
			// doesn't allow for this
			boolean isPositive1 = a.charAt(0) == '+';
			if (isPositive1) {
				a = a.substring(1);
			}
			boolean isPositive2 = b.charAt(0) == '+';
			if (isPositive2) {
				b = b.substring(1);
			}

			a = addZeros(a, b);
			b = addZeros(b, a);

			// Before carrying out the arithmetic operation, we must first check if it's a
			// character between "0" and "9"
			if (!isValidNumber(a) || !isValidNumber(b)) {
				throw new NumberFormatException();
			}

			String result = "";

			int carry = 0;

			int i = a.length() - 1;

			while (i >= 0) {

				int value1 = a.charAt(i) - '0';
				int value2 = b.charAt(i) - '0';

				// value3 gives up the addition of characters at the same index + the carry just
				// like elementary school addition
				// We do % 10 to give us the value we want to print into our string e.g 9 + 8 =
				// 17, we can't do much with 17 so we get the individual digit 7 with the
				// modulus
				int value3 = (value1 + value2 + carry) % 10;

				// The carry is calculated similarly to the value3, however we perform an
				// integer division instead of modulus
				// As in the previous example 8 + 9 = 17 we care about the 1 in this case. To
				// get this we divide 17/10 which is 1
				// We keep this value and add it in the next iteration
				carry = (value1 + value2 + carry) / 10;

				i--;

				result = value3 + result;

			}

			// Carry can take value 1 after loop is done and we must add this in front to
			// complete the operation
			if (carry != 0) {
				return carry + result;
			}

			// Removes exceeding zeros e.g -500 + 300 +200 = 000 but now its 0
			// Does this by checking if the first character of the string is 0 all the way
			// to the last character of the string
			// Removes the prepending 0 by starting the string at character(1)
			while (result.charAt(0) == '0' && result.length() > 1) {
				result = result.substring(1);
			}
			return result;
		}

		private static String addZeros(String a, String b) {
			
			// We eliminate the signal in front of A.
			
			if (a.charAt(0) == '-')
				a = a.substring(1);
			
			//It compares both strings, A and B using the largestStringLength. It will add 0 to the shortest one until both are the same size.
			while (a.length() < largestStringLength(a, b)) {
				a = "0" + a;
			}
			return a;
		}
		
		private static String times(String a, String b) {

			String sign = "";
			String result = "";
			String zeros = "";
			
			// All 3 characters must be entered exception if not
			if (a.length() == 0 || b.length() == 0) {
				throw new NumberFormatException();
			}
			
			// for multiplication, we have to compare the signs of 2 numbers. If there is no sign, or they are the same (++ or --) the result will be positive. 
			//This way, we use the sign method. If both strings have different signs, we return "-" because the result will be negative.
			if (sign(a) != sign(b)) {
				sign = "-";
			}
			
			// first we delete the signs of both numbers so only numbers remain in both strings.
			
			String aWithoutSign = deleteSign(a);
			String bWithoutSign = deleteSign(b);
			
			// Before carrying out the arithmetic operation, we must first check if it's a
			// character between "0" and "9"
			
			if (!isValidNumber(aWithoutSign) || !isValidNumber(bWithoutSign)) {
				throw new NumberFormatException();
			}

			// we start from right to left. So 12 * 132, first we multiply 2*2, then 2*3 and then 2*1. We do the same thing for 1 until the end.
			for (int j = bWithoutSign.length() - 1; j >= 0; j--) {
				
				// Since J is a single character type, we will convert to String so we can add to the string Result. For example 11 * 123, we will get the 3.
				
				String stringB = Character.toString(bWithoutSign.charAt(j));
				
				// The string result is empty, so we call your method and multiplies
				// each digit by all the other digits of the second number. So 11 * 123 will be 123. Then, we add a zero at the Zero at the 
				// end of each row of multiplications.
				// The first row will be 123. The second one 1230. Then, we just call our method plus, to do the sum of both numbers.
				
				// for the multiplication of 11* 123, this is 0 = plus(0, manyByOneDigitMult (11, 3 ) + zero.
				// The result will be 33. Then we go to the second number (2) and add a 0. 
				// 33 = plus (33, manyByOneDigitMult(11 * 2) + 0. We are using our plus method to sum with the previous 
				// result, so is 33 + 220 = 253 and add one more 0.
				// the last  row will be  253 = (plus(253, manyByOneDigitMult(11 * 1) + 00 = 1100 + 253 = 1353.

				result = plus(result, manyByOneDigitMult(aWithoutSign, stringB) + zeros);
				zeros += "0";
			}
			
			// If strings a and b for multiplication have different signs, we will add "-" at the front of the result.
			
			return sign + result;
		}

		private static String sign(String number) {
			
			// if the first character is negative, we keep that way. Otherwise, if it is blank or +, we consider positive.
			if (number.charAt(0) == '-') {
				return "-";
			}
			return "+";
		}

		private static String manyByOneDigitMult(String a, String b) {

			//  This method only multiplies one digit each time.
			// So 123* 11 will be 123.
			
			String result = "";
			int carry = 0;
			int i = a.length() - 1;
			int j = b.length() - 1;
			
			while (j >= 0 && i >= 0) {
				
				int value1 = a.charAt(i) - '0';
				int value2 = b.charAt(j) - '0';
				
				// value3 gives up the addition of characters at the same index + the carry just
				// like elementary school addition
				// We do % 10 to give us the value we want to print into our string e.g 9 * 9 =
				// 81, we can't do much with 81 so we get the individual digit 1 with the
				// modulus
				int value3 = (value1 * value2 + carry) % 10;
				
				// The carry is calculated similarly to the value3, however we perform an
				// integer division instead of modulus
				// As in the previous example 9 * 9 = 81 we care about the 1 in this case. To
				// get this we divide 81/10 which is 8
				// We keep this value and add it in the next iteration		
				carry = (value1 * value2 + carry) / 10;
	
				i--;

				result = value3 + result;
			}
			
			// Carry can take value 1 after loop is done and we must add this in front to
			// complete the operation
			if (carry != 0) {
				return carry + result;
			}
			
			while (result.charAt(0) == '0' && result.length() > 1) {
				result = result.substring(1);
			}
			return result;
		}
		
		// Method to delete signs in front of the number. This is very similar to the one we put at the Precheck method, but I am using this one specifically for the times method.
		private static String deleteSign(String number) {
			if (number.charAt(0) == '+' || number.charAt(0) == '-') {
				number = number.substring(1);
			}
			return number;
		}

		// Method for subtraction
		private static String subtraction(String a, String b) {

			// Before carrying out the arithmetic operation, we must first check if it's a
			// character between "0" and "9"
			if (!isValidNumber(a) || !isValidNumber(b)) {
				throw new NumberFormatException();
			}
			// Initialize empty string to carry the result after arithmetic operation
			String result = "";

			int carry = 0;

			// Index i starts at the last character of the string a or b doesn't matter as
			// they're the same length
			// We choose to start at the last index so we can add the numbers from right to
			// left, making the operation easier to manage
			// This loop will function until i >= 0
			// As we're starting at the end of the string we need to decrement i rather than
			// increment
			for (int i = a.length() - 1; i >= 0; i--) {

				// - '0' is done to normalize the digits, each number in the string has a
				// Unicode
				// - '0' has Unicode 48, '1' is 49 so 49 - 48 = 1, the number is normalized
				int value1 = a.charAt(i) - '0';
				int value2 = b.charAt(i) - '0';

				int value3 = value1 - value2 - carry;

				// If value 3 less than 0 we add 10 to value3 and assign carry = 1
				if (value3 < 0) {
					value3 = value3 + 10;
					carry = 1;
				}

				result = value3 + result;
			}

			// Removes exceeding zeros e.g -500 + 300 +200 = 000 but now its 0
			// Does this by checking if the first character of the string is 0 all the way
			// to the last character of the string
			// Removes the prepending 0 by starting the string at character(1)
			while (result.charAt(0) == '0' && result.length() > 1) { // removes exceeding zeros e.g -500 + 300 +200 = 000
																		// but now its 0
				result = result.substring(1);
			}

			return result;

		}

		// Method comparing the lengths of strings a and b
		private static int largestStringLength(String a, String b) {

			if (a.length() > b.length()) {
				return a.length();
			}

			else if (b.length() > a.length()) {
				return b.length();
			}

			return a.length();
		}

		// Method comparing the values of a and b
		private static boolean isFirstBigger(String a, String b) {

			for (int i = 0; i < a.length(); i++) { // 1234 0023

				// Checks if a at index i is less than b if this is the case we return false
				// indicating we will need to flip
				if (a.charAt(i) < b.charAt(i))
					return false;
				
				// Checks if a at index i is less than b if this is the case we return true
				// indicating a flip is not necessary
				else if (a.charAt(i) > b.charAt(i)) {
					return true;
				}
			}
			return true;
		}

		// Method checking for errors in the input of the user
		private static boolean isValidNumber(String a) {

			// Checks for strings a and b if the Unicode value is between values of '0' and
			// '9'
			// If it were outside these parameters the characters could be symbols or
			// letters which are irrelevant for mathematical operations
			for (int i = a.length() - 1; i >= 0; i--) {
				if (a.charAt(i) < '0' || a.charAt(i) > '9') {
					return false;
				}
			}
			return true;
		}
	}