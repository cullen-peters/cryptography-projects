import java.util.*;

public class OTPPGen
{
	public static void main(String[] args)
	{
		OneTimePadPlus otpp = new OneTimePadPlus();
		Scanner scan = new Scanner(System.in);
		System.out.println("Input the number of bits for the message you wish to encrypt:");
		int bits = scan.nextInt();
		System.out.println(otpp.gen(bits));
		scan.close();
	}
}
