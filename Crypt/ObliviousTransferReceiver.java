import java.util.*;

public class ObliviousTransferReceiver
{
	public static void main(String[] args)throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your choice bit, 0 or 1:");
		int c = sc.nextInt();
		Crypto cr = new Crypto();
		System.out.println("Your message is: "+cr.OTReceiver(c));
		sc.close();
	}

}