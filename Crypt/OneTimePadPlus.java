import java.security.*;

public class OneTimePadPlus
{
	private final int K = 64;
	public String gen(int t)
	{
		SecureRandom rand = new SecureRandom();
		String key = "";
		
		for(int i=0;i<t+K;i++)
		{
			int random = rand.nextInt();
			if(random%2==0)
				key = key+"0";
			else
				key = key+"1";
		}
		return key;
	}
	public String enc(String M, String key)
	{
		byte[] bytes = M.getBytes();
		int l = bytes.length;
		String binary = "";
		for(int i=0;i<l;i++)
		{
			String s = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
			binary = binary+s;
		}
		int a = binary.length();
		if((key.length()-K)>a)
		{
			String ka = key.substring(0, a);
			String cxor = xor(binary,ka);
			String bits = cxor + key.substring(a,(a+K));
			return bits;
		}
		int n = a/key.length()+1;
		String newKey = "";
		for(int i=0;i<n;i++)
		{
			newKey = newKey + key;
		}
		
		String ka = newKey.substring(0, a);
		String cxor = xor(binary,ka);
		String bits = cxor + newKey.substring(a,(a+K));
		
		return bits;
	}
	public String dec(String c, String key)
	{
		if(key.length()>=c.length())
		{
			int b = c.length()-K;
			String A = c.substring(0, b);
			String kA = key.substring(0, b);
			String kB = key.substring(b,b+K);
			String B = c.substring(b);
			if(kB.equals(B))
			{
				String M = "";
				String bits = xor(A,kA);
				int nots = bits.length()/8;
				for(int i=0;i<nots;i++)
				{
					String charac = bits.substring(0, 8);
					bits = bits.substring(8);
					int charCode = Integer.parseInt(charac, 2);
					M = M+new Character((char)charCode).toString();
				}
				return M;
			}
			return "\u22A5";
		}
		int b = c.length()-K;
		int n = b/key.length()+1;
		String newKey = "";
		for(int i=0;i<n;i++)
		{
			newKey = newKey + key;
		}
		String A = c.substring(0, b);
		String kA = newKey.substring(0, b);
		String kB = newKey.substring(b,b+K);
		String B = c.substring(b);
		if(kB.equals(B))
		{
			String M = "";
			String bits = xor(A,kA);
			int nots = bits.length()/8;
			for(int i=0;i<nots;i++)
			{
				String charac = bits.substring(0, 8);
				bits = bits.substring(8);
				int charCode = Integer.parseInt(charac, 2);
				M = M+new Character((char)charCode).toString();
			}
			return M;
		}
		return "\u22A5";
	}
	public String xor(String b1,String b2)
	{
		int s1 = b1.length();
		int s2 = b2.length();
		if(s1>s2)
		{
			for(int i=0;i<s1-s2;i++)
			{
				b2 = "0"+b2;
			}
		}
		if(s2>s1)
		{
			for(int i=0;i<s2-s1;i++)
			{
				b1 = "0"+b1;
			}
		}
		char[] ca1 = b1.toCharArray();
		char[] ca2 = b2.toCharArray();
		//System.out.println(ca1.length);
		//System.out.println(ca2.length);
		int size = ca1.length;
		String xor = "";
		for(int i=0;i<size;i++)
		{
			if(ca1[i]==ca2[i])
				xor = xor+"0";
			else
				xor = xor+"1";
		}
		return xor;
	}
	public static void main(String[] args)
	{
		OneTimePadPlus otpp = new OneTimePadPlus();
		String key = otpp.gen(448);
		String m = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		System.out.println(otpp.enc(m,key));
		System.out.println(otpp.dec(otpp.enc(m,key), key));	
	}
}
