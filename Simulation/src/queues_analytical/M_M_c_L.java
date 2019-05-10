package queues_analytical;

import java.lang.Math;
import auxMath.*;

public class M_M_c_L extends Queue{
	private int c;
	private int L;
	public M_M_c_L(double lambda, double mu, int c, int L)
	{
		super(lambda,mu);
		this.c = c;
		this.L = L;
	}
	
	protected void Calc_p()
	{
		p = lambda/(mu*c);
	}
	
	protected void Calc_P_0()
	{
		Double sum1 = 0.0, sum2 = 0.0;
		for(int i=0; i<c; i++)
			sum1 += (Math.pow(c*p,i))/(Factorial.getFact(i));
		for(int i=c; i<=L; i++)
			sum2 += Math.pow(p,i);
		P_0 = 1/(sum1 + sum2 * (Math.pow(c,c))/(Factorial.getFact(c)));
	}
	
	public double P_i(int i)
	{
		if(i<c)
			return ((Math.pow(c*p,i))/(Factorial.getFact(i))) * P_0;
		else
			return ((Math.pow(c, c)*Math.pow(p,i))/(Factorial.getFact(c))) * P_0;
	}
	
	protected void view_P_i()
	{
		System.out.println("if i<c, P(i) = (cp)^i / i! * P_0");
		System.out.println("else, P(i) = ((c^c * p^i) / c!) * P_0");
	}
	
	protected void Calc_E_n()
	{
		Double sum1 = 0.0, sum2 = 0.0;
		for(int i=0; i<c; i++)
			sum1 += (i * Math.pow(c*p,i))/(Factorial.getFact(i));
		for(int i=c; i<=L; i++)
			sum2 += i * Math.pow(p,i);
		E_n = P_0*(sum1 + sum2 * (Math.pow(c,c))/(Factorial.getFact(c)));
	}
	
	protected void Calc_throughPut()
	{
		Double sum1 = 0.0, sum2 = 0.0;
		for(int i=1; i<c; i++)
			sum1 += (i * Math.pow(c*p,i))/(Factorial.getFact(i));
		for(int i=c; i<=L; i++)
			sum2 += Math.pow(p,i);
		throughPut = mu*P_0*(sum1 + sum2 * (Math.pow(c,c+1))/(Factorial.getFact(c)));
	}
	
	protected void Calc_E_t()
	{
		E_t = E_n/throughPut;
	}
	
	protected void Calc_E_s()
	{
		E_s = 1/mu;
	}
	
	protected void Calc_E_w()
	{
		E_w = E_t - E_s;
	}
	
	protected void Calc_E_m()
	{
		Double sum1 = 0.0;
		for(int i=c; i<=L; i++)
			sum1 += (i-c) * Math.pow(p,i);
		E_m = P_0*(sum1 * (Math.pow(c,c))/(Factorial.getFact(c)));
	}
	
	protected void Calc_u()
	{
		u = (E_n - E_m)/c;
	}
	
	protected void Calc_P_busy()
	{
		Double sum1 = 0.0;
		for(int i=c; i<=L; i++)
			sum1 += Math.pow(p,i);
		P_busy = P_0*(sum1 * (Math.pow(c,c))/(Factorial.getFact(c)));
	}
	
	protected void Calc_P_QueueNotEmpty()
	{
		P_QueueNotEmpty = P_busy - P_i(c);
	}
	
	@Override
	public void viewPerformance()
	{
		System.out.println("Queue Type: M/M/c/L");
		System.out.println("Number of servers = " + c);
		System.out.println("Max number of jobs in the system(L) = " + L);
		if(L>=c)
			super.viewPerformance();
		else
			System.out.println("L should not be smaller than c");
	}

}
