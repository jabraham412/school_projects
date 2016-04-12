public class IterativeFibonacci {
	public static long fib(int n) {
		int prev1=0, prev2=1;
		for(int i=0; i<n; i++) {
			int savePrev1 = prev1;
			prev1 = prev2;
			prev2 = savePrev1 + prev2;
		}
		return prev1;
	}
	
	public static void main (String [] args) {
        int N = Integer.parseInt(args[0]);
        for (int i = 1; i <= N; i++)
            System.out.println(i + ": " + fib(i));
	}
}