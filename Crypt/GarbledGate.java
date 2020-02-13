import java.security.SecureRandom;
import java.util.*;

public class GarbledGate
{
	public ArrayList<String> doubleEnc = new ArrayList<String>();
	public ArrayList<String> inputs = new ArrayList<String>();
	public ArrayList<String> output = new ArrayList<String>();
	OneTimePadPlus otpp = new OneTimePadPlus();
	Crypto cr = new Crypto();
	SecureRandom rand = new SecureRandom();
	
	public void Garble(String ohoh, String ohone, String oneoh, String oneone) throws Exception
	{
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
		String e1="0", e2="0", e3="0", e4="0";
		if(ohoh.equals("0"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
		}
		if(ohoh.equals("1"))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
		}
		if(ohone.equals("0"))
		{
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
		}
		if(ohone.equals("1"))
		{
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
		}
		if(oneoh.equals("0"))
		{
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
		}
		if(oneoh.equals("1"))
		{
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
		}
		if(oneone.equals("0"))
		{
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(oneone.equals("1"))
		{
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		
		doubleEnc.add(e1);
		doubleEnc.add(e2);
		doubleEnc.add(e3);
		doubleEnc.add(e4);
		Collections.shuffle(doubleEnc);
		inputs.add(wa0.w);
		inputs.add(wa1.w);
		inputs.add(wb0.w);
		inputs.add(wb1.w);
		output.add(wo0.w);
		output.add(wo1.w);
	}
	public void Garble(String gateType) throws Exception
	{
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
		String e1="0", e2="0", e3="0", e4="0";
		if(gateType.equalsIgnoreCase("and"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("or"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase(">"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("<"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase(">="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("<="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(!gateType.equalsIgnoreCase("<=")&&!gateType.equalsIgnoreCase(">=")&&!gateType.equalsIgnoreCase("=")&&!gateType.equalsIgnoreCase("<")&&!gateType.equalsIgnoreCase(">")&&!gateType.equalsIgnoreCase("or")&&!gateType.equalsIgnoreCase("and"))
			System.out.println("Gate is not recognized. Please enter truth table.");
		
		doubleEnc.add(e1);
		doubleEnc.add(e2);
		doubleEnc.add(e3);
		doubleEnc.add(e4);
		Collections.shuffle(doubleEnc);
		inputs.add(wa0.w);
		inputs.add(wa1.w);
		inputs.add(wb0.w);
		inputs.add(wb1.w);
		output.add(wo0.w);
		output.add(wo1.w);
	}
	public void GarbleSetTwoInputs(String gateType, String sa0, String sa1, String sb0, String sb1) throws Exception
	{
		Wire wa0 = new Wire(sa0);
		String a0 = cr.binaryHash(wa0.w);
		Wire wa1 = new Wire(sa1);
		String a1 = cr.binaryHash(wa1.w);
		Wire wb0 = new Wire(sb0);
		String b0 = cr.binaryHash(wb0.w);
		Wire wb1 = new Wire(sb1);
		String b1 = cr.binaryHash(wb1.w);
		Wire wo0 = new Wire();
		Wire wo1 = new Wire();
		String e1="0", e2="0", e3="0", e4="0";
		if(gateType.equalsIgnoreCase("and"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("or"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase(">"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("<"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase(">="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(gateType.equalsIgnoreCase("<="))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		if(!gateType.equalsIgnoreCase("<=")&&!gateType.equalsIgnoreCase(">=")&&!gateType.equalsIgnoreCase("=")&&!gateType.equalsIgnoreCase("<")&&!gateType.equalsIgnoreCase(">")&&!gateType.equalsIgnoreCase("or")&&!gateType.equalsIgnoreCase("and"))
			System.out.println("Gate is not recognized. Please enter truth table.");
		
		doubleEnc.add(e1);
		doubleEnc.add(e2);
		doubleEnc.add(e3);
		doubleEnc.add(e4);
		Collections.shuffle(doubleEnc);
		inputs.add(wa0.w);
		inputs.add(wa1.w);
		inputs.add(wb0.w);
		inputs.add(wb1.w);
		output.add(wo0.w);
		output.add(wo1.w);
	}
	public void GarbleSetTwoInputs(String ohoh, String ohone, String oneoh, String oneone, String sa0, String sa1, String sb0, String sb1) throws Exception
	{
		Wire wa0 = new Wire(sa0);
		String a0 = cr.binaryHash(wa0.w);
		Wire wa1 = new Wire(sa1);
		String a1 = cr.binaryHash(wa1.w);
		Wire wb0 = new Wire(sb0);
		String b0 = cr.binaryHash(wb0.w);
		Wire wb1 = new Wire(sb1);
		String b1 = cr.binaryHash(wb1.w);
		Wire wo0 = new Wire();
		Wire wo1 = new Wire();
		String e1="0", e2="0", e3="0", e4="0";
		if(ohoh.equals("0"))
		{
			e1 = otpp.xor(otpp.xor(wo0.w, a0), b0);
		}
		if(ohoh.equals("1"))
		{
			e1 = otpp.xor(otpp.xor(wo1.w, a0), b0);
		}
		if(ohone.equals("0"))
		{
			e2 = otpp.xor(otpp.xor(wo0.w, a0), b1);
		}
		if(ohone.equals("1"))
		{
			e2 = otpp.xor(otpp.xor(wo1.w, a0), b1);
		}
		if(oneoh.equals("0"))
		{
			e3 = otpp.xor(otpp.xor(wo0.w, a1), b0);
		}
		if(oneoh.equals("1"))
		{
			e3 = otpp.xor(otpp.xor(wo1.w, a1), b0);
		}
		if(oneone.equals("0"))
		{
			e4 = otpp.xor(otpp.xor(wo0.w, a1), b1);
		}
		if(oneone.equals("1"))
		{
			e4 = otpp.xor(otpp.xor(wo1.w, a1), b1);
		}
		
		doubleEnc.add(e1);
		doubleEnc.add(e2);
		doubleEnc.add(e3);
		doubleEnc.add(e4);
		Collections.shuffle(doubleEnc);
		inputs.add(wa0.w);
		inputs.add(wa1.w);
		inputs.add(wb0.w);
		inputs.add(wb1.w);
		output.add(wo0.w);
		output.add(wo1.w);
	}
	public void print()
	{
		System.out.println("Double Encryption 1: "+doubleEnc.get(0));
		System.out.println("Double Encryption 2: "+doubleEnc.get(1));
		System.out.println("Double Encryption 3: "+doubleEnc.get(2));
		System.out.println("Double Encryption 4: "+doubleEnc.get(3));
		System.out.println("a0: "+inputs.get(0));
		System.out.println("a1: "+inputs.get(1));
		System.out.println("b0: "+inputs.get(2));
		System.out.println("b1: "+inputs.get(3));
		System.out.println("o0: "+output.get(0));
		System.out.println("o1: "+output.get(1));
	}
}
