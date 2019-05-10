package queues_analytical;

public abstract class Queue {
	protected Queue(double lambda, double mu)
	{
		this.lambda = lambda;
		this.mu = mu;
	}

	protected double lambda; //arrival rate
	protected double mu; //service rate
	protected double p; //traffic density
	protected double u; //utilization per server
	protected double P_0; //probability that the system is idle
	protected double E_n; //mean queue length
	protected double throughPut; //throughput of the system
	protected double E_t; //Response time (delay) (avg. total time spent in the system)
	protected double E_s; //avg. service time
	protected double E_w; //avg. waiting time in the queue
	protected double E_m; //avg. number of jobs in the queue
	protected double P_QueueNotEmpty; //probability that the queue is not empty
	protected double P_busy; //probability that the system is busy

	public abstract double P_i(int i);//probability that there are i jobs at the system
	protected abstract void view_P_i();
	protected abstract void Calc_p();
	protected abstract void Calc_u(); 
	protected abstract void Calc_P_0(); 
	protected abstract void Calc_E_n(); 
	protected abstract void Calc_throughPut(); 
	protected abstract void Calc_E_t(); 
	protected abstract void Calc_E_s(); 
	protected abstract void Calc_E_w(); 
	protected abstract void Calc_E_m();
	protected abstract void Calc_P_busy();
	protected abstract void Calc_P_QueueNotEmpty();
	
	public void calculateAll() 
	{
		Calc_p();
		Calc_P_0();
		Calc_E_n(); 
		Calc_throughPut(); 
		Calc_E_t(); 
		Calc_E_s(); 
		Calc_E_w();
		Calc_E_m();
		Calc_u();
		Calc_P_busy();
		Calc_P_QueueNotEmpty();
	}
	
	public void viewPerformance() 
	{
		Calc_p();
		Calc_P_0();
		Calc_E_n(); 
		Calc_throughPut(); 
		Calc_E_t(); 
		Calc_E_s(); 
		Calc_E_w();
		Calc_E_m();
		Calc_u();
		Calc_P_busy();
		Calc_P_QueueNotEmpty();
		
		System.out.println("Traffic Density(p) = " + p);
		System.out.println("probability that the system is idle(P0) = " + P_0);
		view_P_i();
		System.out.println("Mean Queue Length(En) = " + E_n);
		System.out.println("Avg. Throughput = " + throughPut);
		System.out.println("Avg. Response Time(Et) = " + E_t);
		System.out.println("Avg. Service Time(Es) = " + E_s);
		System.out.println("Avg. waiting time in the queue(Ew) = " + E_w);
		System.out.println("Avg. Number of Jobs in the queue(Em) = " + E_m);
		System.out.println("Utilization Per Server(u) = " + u);
		System.out.println("probability that the queue is not empty = " + P_QueueNotEmpty);
		System.out.println("probability that all servers are busy = " + P_busy);
	}
	public double getLambda() {
		return lambda;
	}
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}
	public double getMu() {
		return mu;
	}
	public void setMu(double mu) {
		this.mu = mu;
	}
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public double getU() {
		return u;
	}
	public void setU(double u) {
		this.u = u;
	}
	public double getP_0() {
		return P_0;
	}
	public void setP_0(double p_0) {
		P_0 = p_0;
	}
	public double getE_n() {
		return E_n;
	}
	public void setE_n(double e_n) {
		E_n = e_n;
	}
	public double getThroughPut() {
		return throughPut;
	}
	public void setThroughPut(double throughPut) {
		this.throughPut = throughPut;
	}
	public double getE_t() {
		return E_t;
	}
	public void setE_t(double e_t) {
		E_t = e_t;
	}
	public double getE_s() {
		return E_s;
	}
	public void setE_s(double e_s) {
		E_s = e_s;
	}
	public double getE_w() {
		return E_w;
	}
	public void setE_w(double e_w) {
		E_w = e_w;
	}
	public double getE_m() {
		return E_m;
	}
	public void setE_m(double e_m) {
		E_m = e_m;
	}
	public double getP_QueueNotEmpty() {
		return P_QueueNotEmpty;
	}
	public void setP_QueueNotEmpty(double p_QueueNotEmpty) {
		P_QueueNotEmpty = p_QueueNotEmpty;
	}
	public double getP_busy() {
		return P_busy;
	}
	public void setP_busy(double p_busy) {
		P_busy = p_busy;
	}
	
	
}
