public class FridgeTest {
    public static void main(String[] args) {
        if(args.length != 4) {
            System.err.println("Usage: java Test <number of engineers> <number of drinks> <number of drinks on lists> <seed>");
            System.exit(1);
        }
        int e = Integer.parseInt(args[0]);
        int d = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);
        java.util.Random r = new java.util.Random(Long.parseLong(args[3]));
        e &= ~1;
        System.out.println(e + " " + d);
        for(int i = 0; i < d; ++i) System.out.println(i + "\tDrink " + i);
        int[] pref = new int[d];
        for(int j = 0; j < d; ++j) pref[j] = j;
        for(int i = 0; i < e; ++i) {
            System.out.print(i + "\t");
            for(int j = 0; j < n; ++j) {
                int k = j + r.nextInt(d - j);
                int t = pref[k];
                pref[k] = pref[j];
                pref[j] = t;
                System.out.print(t);
                if(j < n - 1) System.out.print(",");
            }
            System.out.println();
        }
    }
}
