import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Priority_Scheduler {
    int currTime = 0;
    Deque<Process> processes_list = new ArrayDeque<>();
    Deque<Process> complete_list = new ArrayDeque<>();

    void schedule(Deque<Process> processes) {
        Iterator<Process> iterator = processes.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Main.copyProcess(element);
            processes_list.addLast(p);
        }

        while (true) {
            Process current = getNextProcess();
            if (current == null) break;

            currTime += current.burstTime;
            current.completeTime = currTime;
            complete_list.add(current);
        }
    }

    Process getNextProcess() {
        Process selected = null;
        int highestPriority = Integer.MAX_VALUE;
        for (Process p : processes_list) {
            if (p.arrivalTime <= currTime && p.completeTime == 0) {
                p.aging();
                if (p.priorityNumber < highestPriority) {
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