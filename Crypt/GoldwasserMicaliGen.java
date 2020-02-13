import java.util.*;

public class GoldwasserMicaliGen
{
	public static void main(String[] args)
	{
		GoldwasserMicali gm = new GoldwasserMicali();
		Scanner sc = new Scanner(System.in); 
		System.out.println("INPUT THE BIT LENGTH OF THE PRIMES YOU WANT TO GENERATE");
		int t = sc.nextInt();
		sc.close();
		System.out.println(gm.Gen(t));
	}
}
