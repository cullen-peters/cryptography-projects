import java.util.*;

public class ObliviousTransferSender
{
	public static void main(String[] args)throws Exception
	{
		Scanner sc = new Scanner(System.in);
		Crypto cr = new Crypto();
		System.out.println("Enter your first messege, m0");
		String m0 = sc.nextLine();
		System.out.println("Enter your second message, m1");
		String m1 = sc.nextLine();
		System.out.println("Your encryptions are: "+cr.OTSender(m0,m1));
		sc.close();
	}
}
