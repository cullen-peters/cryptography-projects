import java.util.*;
import java.security.*;

public class GarbledAnd
{
	OneTimePadPlus otpp = new OneTimePadPlus();
	Crypto cr = new Crypto();
	SecureRandom rand = new SecureRandom();
	public ArrayList<String> garble()throws Exception
	{
		ArrayList<String> garbledCircuit = new ArrayList<String>();
		Wire wa0 = new Wire();
		String a0 = cr.binaryHash(wa0.w);
		Wire wa1 = new Wire();
		String a1 = cr.binaryHash(wa1.w);
		Wire wb0 = new Wire();
		String b0 = cr.binaryHash(wb0.w);
		Wire wb1 = new Wire();
		String b1 = cr.binaryHash(wb1.w);
		Wire wo0 = new Wire();
		Wire wo1 = new Wire();
		String e1, e2, e3, e4;
		
		e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
		e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
		e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
		e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		
		garbledCircuit.add(e1);
		garbledCircuit.add(e2);
		garbledCircuit.add(e3);
		garbledCircuit.add(e4);
		Collections.shuffle(garbledCircuit);
		garbledCircuit.add(wa0.w);
		garbledCircuit.add(wa1.w);
		garbledCircuit.add(wb0.w);
		garbledCircuit.add(wb1.w);
		garbledCircuit.add(wo0.w);
		garbledCircuit.add(wo1.w);
		
		return garbledCircuit;
	}
	public String eval(ArrayList<String> garbledCircuit, Wire a, Wire b)throws Exception
	{	
		String e1 = garbledCircuit.get(0);
		String e2 = garbledCircuit.get(1);
		String e3 = garbledCircuit.get(2);
		String e4 = garbledCircuit.get(3);
		String o0 = garbledCircuit.get(4);
		String o1 = garbledCircuit.get(5);
		
		String bH = cr.binaryHash(b.w);
		String aH = cr.binaryHash(a.w);
		
		String d1 = otpp.xor(otpp.xor(e1, bH), aH);
		//System.out.println(d1);
		//System.out.println(d1.substring(448));
		if(d1.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d1);
			if(d1.equals(o0))
				return "0";
			if(d1.equals(o1))
				return "1";
		}
		String d2 = otpp.xor(otpp.xor(e2, bH), aH);
		//System.out.println(d2);
		//System.out.println(d2.substring(448));
		if(d2.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d2);
			if(d2.equals(o0))
				return "0";
			if(d2.equals(o1))
				return "1";
		}
		String d3 = otpp.xor(otpp.xor(e3, bH), aH);
		//System.out.println(d3);
		//System.out.println(d3.substring(448));
		if(d3.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d3);
			if(d3.equals(o0))
				return "0";
			if(d3.equals(o1))
				return "1";
		}
		String d4 = otpp.xor(otpp.xor(e4, bH), aH);
		//System.out.println(d4);
		//System.out.println(d4.substring(448));
		if(d4.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d4);
			if(d4.equals(o0))
				return "0";
			if(d4.equals(o1))
				return "1";
		}
		return "\u22A5";
	}
	public String eval(String e1, String e2, String e3, String e4, String o0, String o1, Wire a, Wire b)throws Exception
	{	
		String bH = cr.binaryHash(b.w);
		String aH = cr.binaryHash(a.w);
		
		String d1 = otpp.xor(otpp.xor(e1, bH), aH);
		//System.out.println(d1);
		//System.out.println(d1.substring(448));
		if(d1.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d1);
			if(d1.equals(o0))
				return "0";
			if(d1.equals(o1))
				return "1";
		}
		String d2 = otpp.xor(otpp.xor(e2, bH), aH);
		//System.out.println(d2);
		//System.out.println(d2.substring(448));
		if(d2.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d2);
			if(d2.equals(o0))
				return "0";
			if(d2.equals(o1))
				return "1";
		}
		String d3 = otpp.xor(otpp.xor(e3, bH), aH);
		//System.out.println(d3);
		//System.out.println(d3.substring(448));
		if(d3.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d3);
			if(d3.equals(o0))
				return "0";
			if(d3.equals(o1))
				return "1";
		}
		String d4 = otpp.xor(otpp.xor(e4, bH), aH);
		//System.out.println(d4);
		//System.out.println(d4.substring(448));
		if(d4.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d4);
			if(d4.equals(o0))
				return "0";
			if(d4.equals(o1))
				return "1";
		}
		return "\u22A5";
	}
	public static void main(String[] arg)throws Exception
	{
		GarbledAnd gand = new GarbledAnd();
		ArrayList<String> g = gand.garble();
		
		Wire a = new Wire(g.get(5));
		Wire b = new Wire(g.get(7));
		g.remove(4);
		g.remove(4);
		g.remove(4);
		g.remove(4);
		System.out.println(gand.eval(g, a, b));
	}
}
