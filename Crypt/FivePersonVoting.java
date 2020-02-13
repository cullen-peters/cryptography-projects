import java.util.*;
import java.math.*;

public class FivePersonVoting
{
	public static BigInteger ONE = new BigInteger("1");
	public static BigInteger TWO = new BigInteger("2");
	public static BigInteger THREE = new BigInteger("3");
	public static BigInteger FOUR = new BigInteger("4");
	public static BigInteger FIVE = new BigInteger("5");
	public static BigInteger SIX = new BigInteger("6");
	public static BigInteger ZERO = new BigInteger("0");
	
	public static boolean validate(BGWProtocol bgw, int i)
	{
		bgw.multiply5(i, i);
		bgw.add5(i);
		bgw.add5(i);
		bgw.add5(i);
		bgw.add5(i);
		bgw.add5(i);
		bgw.add5(i);
		BigInteger y = bgw.getAnswer();
		
		if(y.compareTo(BigInteger.ZERO)==0)
			return true;
		return false;
	}
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("User 1, vote 1 for yes or 0 for no");
		BigInteger x1 = scan.nextBigInteger();
		System.out.println("User 2, vote 1 for yes or 0 for no");
		BigInteger x2 = scan.nextBigInteger();
		System.out.println("User 3, vote 1 for yes or 0 for no");
		BigInteger x3 = scan.nextBigInteger();
		System.out.println("User 4, vote 1 for yes or 0 for no");
		BigInteger x4 = scan.nextBigInteger();
		System.out.println("User 5, vote 1 for yes or 0 for no");
		BigInteger x5 = scan.nextBigInteger();
		BGWProtocol bgw = new BGWProtocol(x1,x2,x3,x4,x5);
		/*BGWUser user1 = bgw.users.get(0);
		BGWUser user2 = bgw.users.get(1);
		BGWUser user3 = bgw.users.get(2);
		BGWUser user4 = bgw.users.get(3);
		BGWUser user5 = bgw.users.get(4);
		*/
		if(validate(bgw,1)&&validate(bgw,2)&&validate(bgw,3)&&validate(bgw,4)&&validate(bgw,5))
		{
			/*
			BigInteger s1 = BigInteger.ZERO;
			for(int i=0; i<5; i++)
			{
				s1 = user1.shares.get(i).add(s1).mod(bgw.m);
			}
			BigInteger s2 = BigInteger.ZERO;
			for(int i=0; i<5; i++)
			{
				s2 = user2.shares.get(i).add(s2).mod(bgw.m);
			}
			BigInteger s3 = BigInteger.ZERO;
			for(int i=0; i<5; i++)
			{
				s3 = user3.shares.get(i).add(s3).mod(bgw.m);
			}
			BigInteger s4 = BigInteger.ZERO;
			for(int i=0; i<5; i++)
			{
				s4 = user4.shares.get(i).add(s4).mod(bgw.m);
			}
			BigInteger s5 = BigInteger.ZERO;
			for(int i=0; i<5; i++)
			{
				s5 = user5.shares.get(i).add(s5).mod(bgw.m);
			}
			*/
			bgw.add5(1,2);
			bgw.add5(3);
			bgw.add5(4);
			bgw.add5(5);
			BigInteger s = bgw.getAnswer();
			/*System.out.println("The sum is: "+sum.evaluate(BigInteger.ZERO));
			System.out.println("y4: "+y4);
			System.out.println("y5: "+y5);
			System.out.println("s4: "+s4);
			System.out.println("s5: "+s5);*/
			
			BigInteger majorityCheck = SIX.multiply(s.pow(5)).add(TWO.multiply(s.pow(4))).add(FIVE.multiply(s.pow(3))).add(s).mod(bgw.m);
				//System.out.println(majorityCheck);
			if(majorityCheck.compareTo(ONE)==0)
			{
				System.out.println("Majority was met.");
				System.out.println("User 1's vote was " +x1);
				System.out.println("User 2's vote was " +x2);
				System.out.println("User 3's vote was " +x3);
				System.out.println("User 4's vote was " +x4);
				System.out.println("User 5's vote was " +x5);
			}
			if(majorityCheck.compareTo(ONE)!=0)
			{
				System.out.println("Majority was not met.");
			}
		}
			
			//6x^5 + 2x^4 + 5x^3 + x
		if(!(validate(bgw,1)&&validate(bgw,2)&&validate(bgw,3)&&validate(bgw,4)&&validate(bgw,5)))
			System.out.println("Invalid input. Pick either 0 or 1");
		
		scan.close();
	}
}
