package components;

public class Job {

	private static int nextID = 0;
	private int id;
	private double arrivalTime;
	private double serviceTime;
	private double serviceStartTime;
	
	public Job(double arrTime, double serTime)
	{
		this.id = nextID++;
		this.arrivalTime = arrTime;
		this.serviceTime = serTime;
		this.serviceStartTime = -1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public double getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}
	public double getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(double serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public double getTimeInQueue() {
		return serviceStartTime - arrivalTime;
	}
	public double getServiceEndTime() {
		return serviceStartTime + serviceTime;
	}
	
	
}
