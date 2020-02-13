import java.util.*;

public class OTPPEnc
{
	public static void main(String[] args)
	{
		OneTimePadPlus otpp = new OneTimePadPlus();
		Scanner sc = new Scanner(System.in);
		System.out.println("Input your message: ");
		String M = sc.nextLine();
		byte[] bytes = M.getBytes();
		int l = bytes.length;
		String binary = "";
		for(int i=0;i<l;i++)
		{
			String s = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
			binary = binary+s;
		}
		int bits = binary.length();
		System.out.println("Generate a key with an input of at least "+bits);
		String key = sc.nextLine();
		System.out.println("Your Ciphertext is: ");
		System.out.println(otpp.enc(M, key));
		sc.close();
	}
}