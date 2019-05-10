package auxMath;

public class Factorial {

	public static double getFact(int n)
	{
		if(n<1)
			return 1;
		else
			return n*getFact(n-1);
	}
}
