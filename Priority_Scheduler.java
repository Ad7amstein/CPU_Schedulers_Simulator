import java.util.*;

public class Priority_Scheduler{
    Deque<Process> complete_list = new ArrayDeque<>();
    public void schedule(Deque<Process> p) {
        List<Process> executedP = new ArrayList<>();
        int currTime = 0;
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        priorityQueue.addAll(p);
        while (!priorityQueue.isEmpty()) {
            Process currentProcess = priorityQueue.poll();

            if (currentProcess.arrivalTime > currTime) {
                currTime = currentProcess.arrivalTime;
            }
            int waitTime = currTime - currentProcess.arrivalTime;
            currentProcess.waitingTime = waitTime;
            int turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            currentProcess.turnaroundTime = turnaroundTime;

            currTime += currentProcess.burstTime;
            executedP.add(currentProcess);
            for (Process waitingProcess : priorityQueue) {
                waitingProcess.aging();
            }

        }
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        System.out.println("\nNon Preemptive Priority Scheduling:");
        System.out.println("Process Execution Order:");
        for (Process process : executedP) {
            System.out.println(process.name);
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
    }
        double avgWaitingTime = (double) totalWaitingTime / p.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / p.size();
        System.out.println("\nWaiting Time and Turnaround Time for each process:");
        for (Process process : executedP) {
            System.out.println(process.name + ": Waiting Time -> " + process.waitingTime + ", Turnaround Time -> " + process.turnaroundTime);
        }

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}

