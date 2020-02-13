import java.security.*;

public class Wire
{
	public String w;
	public static final int LENGTH = 448;
	public static final int NUM0 = 64;
	
	public Wire()
	{
		SecureRandom rand = new SecureRandom();
		w = "";
		for(int i=0;i<LENGTH;i++)
		{
			int keyInt = rand.nextInt(2);
			w = w + Integer.toString(keyInt);
		}
		for(int i=0;i<NUM0;i++)
		{
			w = w + "0";
		}
	}
	public Wire(String w)
	{
		this.w = w;
	}
	public static void main(String arg[])
	{
		Wire wire = new Wire();
		System.out.println(wire.w);
	}
}