import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Priority_Scheduler {
    public void priorityScheduling(List<Process> p) {
        List<Process> executedP = new ArrayList<>();
        int currTime = 0;
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        for (Process process : p) {
            priorityQueue.add(process);
        }
        while (!priorityQueue.isEmpty()) {
            Process currentProcess = priorityQueue.poll();

            if (currentProcess.ArrivalTime > currTime) {
                currTime = currentProcess.ArrivalTime;
            }
            int waitTime = currTime - currentProcess.ArrivalTime;
            currentProcess.WaitingTime = waitTime;
            int turnaroundTime = currentProcess.WaitingTime + currentProcess.BurstTime;
            currentProcess.TurnaroundTime = turnaroundTime;

            currTime += currentProcess.BurstTime;
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
            System.out.println(process.Name);
            totalWaitingTime += process.WaitingTime;
            totalTurnaroundTime += process.TurnaroundTime;
    }
        double avgWaitingTime = (double) totalWaitingTime / p.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / p.size();
        System.out.println("\nWaiting Time and Turnaround Time for each process:");
        for (Process process : executedP) {
            System.out.println(process.Name + ": Waiting Time -> " + process.WaitingTime + ", Turnaround Time -> " + process.TurnaroundTime);
        }

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }



}

