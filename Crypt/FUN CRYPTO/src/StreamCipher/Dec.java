package StreamCipher;
public class Dec {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String m = "";
        for (int i=1;i<args.length;i++) {
            String word = args[i];
            for (int j=0;j<word.length();j++) {
                if ((i==1)&(j==0))
                    m += rot(word.charAt(j), -k);
                if ((i>1)&(j==0)){
                    m += rot(word.charAt(j), -toInt(m.charAt(m.length()-2)));
                }
                if (j!=0)
                    m += rot(word.charAt(j), -toInt(m.charAt(m.length()-1)));
            }
            m += " ";
        }
        System.out.println(m);
    }
    public static int toInt(char a) {
        return Character.getNumericValue(a)-10;
    }
    public static char toChar(int a) {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((a+26)%26);
    }
    public static char rot(char a, int r) {
        return toChar((toInt(a)+r)%26);
    }
}