package StreamCipher;
public class Enc {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String c = "";
        for (int i=1;i<args.length;i++) {
            String word = args[i];
            for (int j=0;j<word.length();j++) {
                if ((i==1)&(j==0))
                    c += rot(word.charAt(j), k);
                if ((i>1)&(j==0)){
                    String prevWord = args[i-1];
                    c += rot(word.charAt(j), toInt(prevWord.charAt(prevWord.length()-1)));
                }
                if (j!=0)
                    c += rot(word.charAt(j), toInt(word.charAt(j-1)));
            }
            c += " ";
        }
        System.out.println(c);
    }
    public static int toInt(char a) {
        return Character.getNumericValue(a)-10;
    }
    public static char toChar(int a) {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(a%26);
    }
    public static char rot(char a, int r) {
        return toChar((toInt(a)+r)%26);
    }
}