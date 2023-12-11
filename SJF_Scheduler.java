import java.util.*;

public class SJF_Scheduler{
    int time = 0;
    int contextSwitching;
    Deque<Process> processes_list = new ArrayDeque<>();
    Deque<Process> complete_list = new ArrayDeque<>();

    void schedule(Deque<Process> processes) {
        Iterator<Process> iterator = processes.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Main.copyProcess(element);
            processes_list.addLast(p);
        }
        int maxArrivalTime = 0;
        for (Process p : processes_list) {
            maxArrivalTime = Math.max(p.arrivalTime, maxArrivalTime);
        }

        while(true){
            List<Process> current = new ArrayList<>();
            current = getProcesses(time);
            if(current.isEmpty())break;
            Collections.sort(current, Comparator.comparingInt(p -> p.burstTime));
            time += current.get(0).burstTime;
            current.get(0).completeTime = time;
            time += contextSwitching;
            complete_list.add(current.get(0));
        }
    }
    List<Process> getProcesses(int time){
        List<Process> ret = new ArrayList<>();
        for (Process p: processes_list){
            if(p.arrivalTime <= time && p.completeTime == 0){
                ret.add(p);
            }
        }
        return ret;
    }
}
