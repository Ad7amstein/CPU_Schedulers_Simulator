import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;

class SRTF_Scheduler extends Scheduler
{
    int currentTime = 0;
    int maxWaiting = 5;
    private Deque<Process> processes_list = new ArrayDeque<>();
    private Deque<Process> TempProcesses = new ArrayDeque<>();
    private Deque<Process> execution = new ArrayDeque<>();
    private PriorityQueue<Process> ReadyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
    private int LoopOnArray(Deque<Process> processes, int currentTime) {
        int completeProcesses = 0;

        for (Process p : processes) {
            if (p.arrivalTime <= currentTime && p.burstTime != 0) {
                ReadyQueue.add(p);
            }
        }
        while (!ReadyQueue.isEmpty()) {
            Process pulledProcess = ReadyQueue.poll();
            Process ret = getProcess(pulledProcess.name);
            if(ret != null)complete_list.remove(ret);
            complete_list.add(pulledProcess);

            Process last = execution.peekLast();
            if(last == null){
                execution.add(pulledProcess);
                execution_order.computeIfAbsent(pulledProcess.name, k -> new ArrayList<>()).add(new Pair(currentTime, currentTime+1));
            }
            else{
                if(last.name == pulledProcess.name){
                    for (Map.Entry<String, List<Pair>> entry : execution_order.entrySet()) {
                        if(entry.getKey() == last.name){
                            Pair pair = entry.getValue().get(entry.getValue().size() - 1);
                            pair.end = currentTime + 1;
                            entry.getValue().set(entry.getValue().size() - 1, pair);
                        }
                    }
                }
                else{
                    execution.add(pulledProcess);
                    execution_order.computeIfAbsent(pulledProcess.name, k -> new ArrayList<>()).add(new Pair(currentTime, currentTime+1));
                }
            }
            pulledProcess.burstTime--;
            currentTime++;
            for(Process p:ReadyQueue){
                p.waitingTime++;
            }

            if (pulledProcess.burstTime == 0) {
                completeProcesses++;
                pulledProcess.completeTime = currentTime;
            }
            for (Process currentProcessInner : processes) {
                if (currentProcessInner.arrivalTime <= currentTime && currentProcessInner.burstTime != 0) {
                    if (!ReadyQueue.contains(currentProcessInner)) {
                        ReadyQueue.add(currentProcessInner);
                    }
                }
            }
            for (Process p: ReadyQueue) {
                if(p.waitingTime >= maxWaiting)p.priorityNumber--;
            }
        }
        return completeProcesses;
    }

    public void schedule(Deque<Process> processes) {
        Iterator<Process> iterator = processes.iterator();
        PriorityQueue<Process> processes1 = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Scheduler.copyProcess(element);
            processes1.add(p);
        }
        int curr = 0;
        for (Process p: processes1) {
            p.priorityNumber = curr;
            processes_list.add(p);
            curr++;
        }
        TempProcesses.addAll(processes_list);

        int completedProcesses = 0;

        while (completedProcesses < processes_list.size()) {
            completedProcesses += LoopOnArray(TempProcesses, currentTime);
            currentTime++;
        }
    }
    public void printExecutionOrder(){
        int curr = 0;
        int maxStart = this.calculateMaxStart();
        while(curr <= maxStart) {
            String pName = getProcess(curr);
            List<Pair> list = execution_order.get(pName);
            for (Pair p: list) {
                if(p.start == curr){
                    System.out.println(p.start + ":(" + pName + "):" + p.end);
                    curr = p.end;
                }
            }
        }
    }
}