import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

public class Priority_Scheduler extends Scheduler {
    int time = 0;
    Deque<Process> processes_list = new ArrayDeque<>();

    void schedule(Deque<Process> processes) {
        Iterator<Process> iterator = processes.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Scheduler.copyProcess(element);
            processes_list.addLast(p);
        }

        while (true) {
            Process current = getNextProcess();
            if (current == null) break;
            int s = time, e;
            time += current.burstTime;
            e = time;
            execution_order.computeIfAbsent(current.name, k -> new ArrayList<>()).add(new Pair(s, e));
            current.completeTime = time;
            complete_list.add(current);
        }
    }
    Process getNextProcess() {
        Process selected = null;
        int highestPriority = Integer.MAX_VALUE;
        int maxArrivalTime = 0;
        for (Process p : processes_list) {
            maxArrivalTime = Math.max(p.arrivalTime, maxArrivalTime);
        }
        for (Process p : processes_list) {
            if (p.arrivalTime <= time && p.completeTime == 0) {
                p.aging(time);
                if (p.priorityNumber < highestPriority || (p.priorityNumber == highestPriority && p.arrivalTime < maxArrivalTime)) {
                    highestPriority = p.priorityNumber;
                    selected = p;
                }
            }
        }

        if (selected != null) {
            processes_list.remove(selected);
            return selected;
        }

        return null;
    }
}