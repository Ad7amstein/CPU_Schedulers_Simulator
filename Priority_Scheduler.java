import java.util.*;

public class Priority_Scheduler{
    Deque<Process> complete_list = new ArrayDeque<>();
    Deque<Process> processes_list = new ArrayDeque<>();
    public void schedule(Deque<Process> processes) {
        Iterator<Process> iterator = processes.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Main.copyProcess(element);
            processes_list.addLast(p);
        }

        int currTime = 0;
        PriorityQueue<Process> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
        priorityQueue.addAll(processes_list);
        while (!priorityQueue.isEmpty()) {
            Process currentProcess = priorityQueue.poll();

            if (currentProcess.arrivalTime > currTime) {
                currTime = currentProcess.arrivalTime;
            }
            currentProcess.waitingTime = currTime - currentProcess.arrivalTime;
            currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            currentProcess.completeTime = currTime + currentProcess.burstTime;

            currTime += currentProcess.burstTime;
            complete_list.add(currentProcess);
            for (Process waitingProcess : priorityQueue) {
                waitingProcess.aging();
            }
        }
    }
}

