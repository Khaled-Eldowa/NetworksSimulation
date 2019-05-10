package simulationModels;


import components.Job;
import components.Server;
import randomGens.ExponentialGenerator;

public class MMCLBreakdown extends Simulation {

	private int maxLength;

	public MMCLBreakdown(int numberOfServers, int queueLength) {
		super(numberOfServers);

		// initialize the servers
		for (int i = 0; i < numberOfServers; i++) {
			servers.add(new Server());
		}
		this.maxLength = queueLength;
	}

	public void showLogs() {
		System.out.println("Showing the results : " + servedJobs.size() + "\n");
		for (int i = 0; i < servedJobs.size(); ++i) {
			System.out.println("Job ID : " + Integer.toString(servedJobs.get(i).getId()) + ",The waiting time is : "
					+ Double.toString(servedJobs.get(i).getTimeInQueue()) + " arrival: "
					+ servedJobs.get(i).getArrivalTime() + " service start, end: "
					+ servedJobs.get(i).getServiceStartTime() + ", " + servedJobs.get(i).getServiceEndTime());
		}

		System.out.println("List of dropped jobs :" + droppedJobs.size() + "\n");
		for (int i = 0; i < droppedJobs.size(); ++i) {
			System.out.println("Job ID : " + Integer.toString(droppedJobs.get(i).getId()) + ",The waiting time is : "
					+ Double.toString(droppedJobs.get(i).getTimeInQueue()) + " arrival: "
					+ droppedJobs.get(i).getArrivalTime() + " service start, end: "
					+ droppedJobs.get(i).getServiceStartTime() + ", " + droppedJobs.get(i).getServiceEndTime());
		}
	}


	public void startSimulation(double meanInterArrivalTime, double meanServiceTime, double meanTimeBetweenFailures,
			double meanTimeToRepair, int numberOfJobs) {
		reset();
		ExponentialGenerator interArrivalTimeGenerator = new ExponentialGenerator(meanInterArrivalTime);
		ExponentialGenerator sericeTimeGenerator = new ExponentialGenerator(meanServiceTime);
		ExponentialGenerator timeBetweenFailuresGenerator = new ExponentialGenerator(meanTimeBetweenFailures);
		ExponentialGenerator timeToRepairGenerator = new ExponentialGenerator(meanTimeToRepair);
		this.clock = 0;
		double previousClock = 0;
		int nextServerID;
		int nextServerID_repair;
		int[] serverStatus; // holds index of first empty server and the number of empty servers

		int jobCount = 0;

		// System.out.println("Start Simulation Function !!!");
		
		Job nextJob = new Job(0.0, sericeTimeGenerator.generate());
		double nextJobArrivalTime = 0;
		double nextBreakDown = clock + timeBetweenFailuresGenerator.generate();
		jobCount++;
		
		double nextServiceEnd;
		double nextRepairEnd;

		while (servedJobs.size() + droppedJobs.size() < numberOfJobs) {
			
			/**
			 * Need to know what is the next event and what time it is.
			 */
			// System.out.println("Iteration!");

			nextServerID = getNextServer_modified(); // the id of the next server going to finish
			if(nextServerID == -1)
				nextServiceEnd = Double.POSITIVE_INFINITY;
			else
				nextServiceEnd = servers.get(nextServerID).getJobBeingServed().getServiceEndTime();
			
			if (jobCount <= numberOfJobs)
				nextJobArrivalTime = nextJob.getArrivalTime(); // The time of the next job arrival
			else
				nextJobArrivalTime = Double.POSITIVE_INFINITY; //no more jobs
			
			nextServerID_repair = getNextRepair();
			if(nextServerID_repair == -1)
				nextRepairEnd = Double.POSITIVE_INFINITY; //no repairs pending
			else
				nextRepairEnd = servers.get(nextServerID_repair).getRepairedTime();
			
			//warning: the following booleans are not mutually exclusive, the first true one is considered (check if elses below)
			//we are deciding which kind of event is next
			boolean arrivalCheck = (nextJobArrivalTime < nextServiceEnd) && (nextJobArrivalTime < nextRepairEnd) &&
					(nextJobArrivalTime < nextBreakDown) && (nextJobArrivalTime < Double.POSITIVE_INFINITY) || 
					(nextJobArrivalTime == clock);
			
			boolean serviceCheck = (nextServiceEnd < nextRepairEnd) && (nextServiceEnd < nextBreakDown) 
					&& (nextServiceEnd < Double.POSITIVE_INFINITY) || 
					(nextServiceEnd == clock);
			
			boolean repairCheck = (nextRepairEnd < nextBreakDown) && (nextRepairEnd < Double.POSITIVE_INFINITY) || 
					(nextRepairEnd == clock);
			
			boolean breakDownCheck = (nextBreakDown < Double.POSITIVE_INFINITY) || (nextBreakDown == clock);
			
			

			// Check the status of all servers
			serverStatus = checkServers();

			// in case all servers are empty and there is a job going to arrive
			// in case the following server is not empty (as in case of all empty servers
			// this condition will be satisfied)
			// or there is more than one job with the same arrival time, so compare it with
			// the clock time
			if (arrivalCheck) {

				previousClock = clock;
				this.clock = nextJobArrivalTime; // Change the time
				updateStateAndServerTimes_unreliable(clock, previousClock); //update the records
				
				// Check that the maximum length is not exceeded
				if (queue.size() + (numberOfServers - serverStatus[1]) >= maxLength) {
					droppedJobs.add(nextJob); // add the new job to the dropped list
					//System.out.println("Job (dropped): " + Integer.toString(currentJobID));
				} else {
					queue.add(nextJob); // add the new arrived job to the queue
					//System.out.println("Job (queue): " + Integer.toString(currentJobID));
				}
				
				nextJob = new Job(clock + interArrivalTimeGenerator.generate(), sericeTimeGenerator.generate());
				jobCount++;
				// System.out.println("Arrival");

			} else if (serviceCheck) {

				previousClock = clock;
				this.clock = servers.get(nextServerID).getJobBeingServed().getServiceEndTime();
				updateStateAndServerTimes_unreliable(clock, previousClock); //update the records

				servedJobs.add(servers.get(nextServerID).getJobBeingServed()); //store the finished job

				servers.get(nextServerID).finishJob();  //make the server free again
				// System.out.println("Departure");
			} else if (repairCheck) {
				
				previousClock = clock;
				this.clock = servers.get(nextServerID_repair).getRepairedTime();
				updateStateAndServerTimes_unreliable(clock, previousClock); //update the records
						
				servers.get(nextServerID_repair).repair();
				
			} else if (breakDownCheck) {
				previousClock = clock;
				this.clock = nextBreakDown;
				updateStateAndServerTimes_unreliable(clock, previousClock); //update the records
				
				int breakDownServer = chooseBreakDownServer(); //choose a server to break randomly
				if(breakDownServer!=-1) { //if there is at least one server that is not broken down
					if(!servers.get(breakDownServer).isEmptyStatus())
						droppedJobs.add(servers.get(breakDownServer).getJobBeingServed()); //drop the job being served
					servers.get(breakDownServer).breakDown(nextBreakDown, getRepairManBusyTime() + timeToRepairGenerator.generate());
					//break down the server and generate a repair time
				}
				
				nextBreakDown = clock + timeBetweenFailuresGenerator.generate();
					
			} else { //dead code, just for testing
				System.out.println("This should never happen!");
			}

			// Push the jobs waiting in the queue to the servers if they are Idle
			int i = 0;
			while (queue.size() > 0 && i < servers.size()) {
				// If the server is empty and there is a job, add the job to the server
				if (servers.get(i).isEmptyStatus() == true && !servers.get(i).isBrokeDown(clock)) {
					servers.get(i).addJob(queue.get(0), this.clock); // current system time
					queue.remove(0);
				}
				// System.out.println("Push from the queue");
				i++;
			}
			
                                         
		}
	}
	
	public double getNumberOfJobsSoFar() {
		return servedJobs.size() + droppedJobs.size();
	}

}
