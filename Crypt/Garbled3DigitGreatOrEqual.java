import java.security.SecureRandom;
import java.util.*;

public class Garbled3DigitGreatOrEqual
{
	OneTimePadPlus otpp = new OneTimePadPlus();
	Crypto cr = new Crypto();
	SecureRandom rand = new SecureRandom();
	public ArrayList<ArrayList<String>> garble() throws Exception
	{
		ArrayList<ArrayList<String>> garbledCircuit = new ArrayList<ArrayList<String>>();
		
		GarbledGate gate1 = new GarbledGate();
		gate1.Garble(">");
		GarbledGate gate2 = new GarbledGate();
		gate2.GarbleSetTwoInputs("=", gate1.inputs.get(0), gate1.inputs.get(1), gate1.inputs.get(2), gate1.inputs.get(3));
		GarbledGate gate3 = new GarbledGate();
		gate3.Garble(">");
		GarbledGate gate4 = new GarbledGate();
		gate4.GarbleSetTwoInputs("=", gate3.inputs.get(0), gate3.inputs.get(1), gate3.inputs.get(2), gate3.inputs.get(3));
		GarbledGate gate5 = new GarbledGate();
		gate5.Garble(">=");
		GarbledGate gate6 = new GarbledGate();
		gate6.GarbleSetTwoInputs("and", gate4.output.get(0), gate4.output.get(1), gate5.output.get(0), gate5.output.get(1));
		GarbledGate gate7 = new GarbledGate();
		gate7.GarbleSetTwoInputs("or", gate3.output.get(0), gate3.output.get(1), gate6.output.get(0), gate6.output.get(1));
		GarbledGate gate8 = new GarbledGate();
		gate8.GarbleSetTwoInputs("and", gate2.output.get(0), gate2.output.get(1), gate7.output.get(0), gate7.output.get(1));
		GarbledGate gate9 = new GarbledGate();
		gate9.GarbleSetTwoInputs("or", gate1.output.get(0), gate1.output.get(1), gate7.output.get(0), gate7.output.get(1));
		
		garbledCircuit.add(gate1.doubleEnc);
		garbledCircuit.add(gate1.inputs);
		garbledCircuit.add(gate1.output);
		garbledCircuit.add(gate2.doubleEnc);
		garbledCircuit.add(gate2.inputs);
		garbledCircuit.add(gate2.output);
		garbledCircuit.add(gate3.doubleEnc);
		garbledCircuit.add(gate3.inputs);
		garbledCircuit.add(gate3.output);
		garbledCircuit.add(gate4.doubleEnc);
		garbledCircuit.add(gate4.inputs);
		garbledCircuit.add(gate4.output);
		garbledCircuit.add(gate5.doubleEnc);
		garbledCircuit.add(gate5.inputs);
		garbledCircuit.add(gate5.output);
		garbledCircuit.add(gate6.doubleEnc);
		garbledCircuit.add(gate6.inputs);
		garbledCircuit.add(gate6.output);
		garbledCircuit.add(gate7.doubleEnc);
		garbledCircuit.add(gate7.inputs);
		garbledCircuit.add(gate7.output);
		garbledCircuit.add(gate8.doubleEnc);
		garbledCircuit.add(gate8.inputs);
		garbledCircuit.add(gate8.output);
		garbledCircuit.add(gate9.doubleEnc);
		garbledCircuit.add(gate9.inputs);
		garbledCircuit.add(gate9.output);
		return garbledCircuit;
	}
	public String eval(ArrayList<ArrayList<String>> garbledCircuit, Wire x1, Wire x2, Wire x3, Wire y1, Wire y2, Wire y3) throws Exception
	{
		/*
		ArrayList<String> gate1enc = garbledCircuit.get(0);
		ArrayList<String> gate1out = garbledCircuit.get(1);
		ArrayList<String> gate2enc = garbledCircuit.get(2);
		ArrayList<String> gate2out = garbledCircuit.get(3);
		ArrayList<String> gate3enc = garbledCircuit.get(4);
		ArrayList<String> gate3out = garbledCircuit.get(5);
		ArrayList<String> gate4enc = garbledCircuit.get(6);
		ArrayList<String> gate4out = garbledCircuit.get(7);
		ArrayList<String> gate5enc = garbledCircuit.get(8);
		ArrayList<String> gate5out = garbledCircuit.get(9);
		ArrayList<String> gate6enc = garbledCircuit.get(10);
		ArrayList<String> gate6out = garbledCircuit.get(11);
		ArrayList<String> gate7enc = garbledCircuit.get(12);
		ArrayList<String> gate7out = garbledCircuit.get(13);
		ArrayList<String> gate8enc = garbledCircuit.get(14);
		ArrayList<String> gate8out = garbledCircuit.get(15);
		ArrayList<String> gate9enc = garbledCircuit.get(16);
		ArrayList<String> gate9out = garbledCircuit.get(17);
		*/
		ArrayList<String> gate1enc = garbledCircuit.get(0);
		ArrayList<String> gate2enc = garbledCircuit.get(1);
		ArrayList<String> gate3enc = garbledCircuit.get(2);
		ArrayList<String> gate4enc = garbledCircuit.get(3);
		ArrayList<String> gate5enc = garbledCircuit.get(4);
		ArrayList<String> gate6enc = garbledCircuit.get(5);
		ArrayList<String> gate7enc = garbledCircuit.get(6);
		ArrayList<String> gate8enc = garbledCircuit.get(7);
		ArrayList<String> gate9enc = garbledCircuit.get(8);
		ArrayList<String> gate9out = garbledCircuit.get(9);
		
		Wire out1 = new Wire(evaluateGate(gate1enc, x1, y1));
		Wire out2 = new Wire(evaluateGate(gate2enc, x1, y1));
		Wire out3 = new Wire(evaluateGate(gate3enc, x2, y2));
		Wire out4 = new Wire(evaluateGate(gate4enc, x2, y2));
		Wire out5 = new Wire(evaluateGate(gate5enc, x3, y3));
		Wire out6 = new Wire(evaluateGate(gate6enc, out4, out5));
		Wire out7 = new Wire(evaluateGate(gate7enc, out3, out6));
		Wire out8 = new Wire(evaluateGate(gate8enc, out2, out7));
		Wire out9 = new Wire(evaluateGate(gate9enc, out1, out8));
		if((out9.w).equals(gate9out.get(0)))
			return "0";
		if((out9.w).equals(gate9out.get(1)))
			return "1";
		return "\u22A5";
	}
	public String evaluateGate(ArrayList<String> doubleEnc, Wire a, Wire b) throws Exception
	{
		String e1 = doubleEnc.get(0);
		String e2 = doubleEnc.get(1);
		String e3 = doubleEnc.get(2);
		String e4 = doubleEnc.get(3);
		
		String bH = cr.binaryHash(b.w);
		String aH = cr.binaryHash(a.w);
		
		String d1 = otpp.xor(otpp.xor(e1, bH), aH);
		//System.out.println(d1);
		//System.out.println(d1.substring(448));
		if(d1.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d1);
			return d1;
		}
		String d2 = otpp.xor(otpp.xor(e2, bH), aH);
		//System.out.println(d2);
		//System.out.println(d2.substring(448));
		if(d2.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d2);
			return d2;
		}
		String d3 = otpp.xor(otpp.xor(e3, bH), aH);
		//System.out.println(d3);
		//System.out.println(d3.substring(448));
		if(d3.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d3);
			return d3;
		}
		String d4 = otpp.xor(otpp.xor(e4, bH), aH);
		//System.out.println(d4);
		//System.out.println(d4.substring(448));
		if(d4.substring(448).equals("0000000000000000000000000000000000000000000000000000000000000000"))
		{
			//System.out.println(d4);
			return d4;
		}
		return "\u22A5";
	}
	public static void main(String[] args) throws Exception
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your first input:");
		String x = scan.nextLine();
		System.out.println("Enter your second input:");
		String y = scan.nextLine();
		char[] xarr = x.toCharArray();
		char[] yarr = y.toCharArray();
		char xchar1 = xarr[0];
		char xchar2 = xarr[1];
		char xchar3 = xarr[2];
		char ychar1 = yarr[0];
		char ychar2 = yarr[1];
		char ychar3 = yarr[2];
		
		Garbled3DigitGreatOrEqual garb = new Garbled3DigitGreatOrEqual();
		ArrayList<ArrayList<String>> garbledCircuit = garb.garble();
		Wire x1=new Wire(), x2=new Wire(), x3=new Wire(), y1=new Wire(), y2=new Wire(), y3=new Wire();
		if(xchar1=='0')
			x1 = new Wire(garbledCircuit.get(1).get(0));
		if(xchar1=='1')
			x1 = new Wire(garbledCircuit.get(1).get(1));
		if(ychar1=='0')
			y1 = new Wire(garbledCircuit.get(1).get(2));
		if(ychar1=='1')
			y1 = new Wire(garbledCircuit.get(1).get(3));
		if(xchar2=='0')
			x2 = new Wire(garbledCircuit.get(7).get(0));
		if(xchar2=='1')
			x2 = new Wire(garbledCircuit.get(7).get(1));
		if(ychar2=='0')
			y2 = new Wire(garbledCircuit.get(7).get(2));
		if(ychar2=='1')
			y2 = new Wire(garbledCircuit.get(7).get(3));
		if(xchar3=='0')
			x3 = new Wire(garbledCircuit.get(13).get(0));
		if(xchar3=='1')
			x3 = new Wire(garbledCircuit.get(13).get(1));
		if(ychar3=='0')
			y3 = new Wire(garbledCircuit.get(13).get(2));
		if(ychar3=='1')
			y3 = new Wire(garbledCircuit.get(13).get(3));
		for(int i=2;i<=18;i++)
		{
			int j = i/2;
			garbledCircuit.remove(j);
		}
		String result = garb.eval(garbledCircuit, x1, x2, x3, y1, y2, y3);
		if(result.equals("0"))
			System.out.print(x+" < "+y);
		if(result.equals("1"))
			System.out.print(x+" >= "+y);
		System.out.println(result);
		scan.close();
	}
}
