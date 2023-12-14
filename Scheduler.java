import java.awt.*;
import java.util.*;
import java.util.List;

public class Scheduler
{
    Map<String, List<Pair>> execution_order = new HashMap<>();
    Deque<Process> complete_list = new ArrayDeque<>();
    public static Process copyProcess(Process p){
        return new Process(p.name, p.color, p.arrivalTime, p.burstTime, p.priorityNumber, p.quantumTime);
    }
    public Process getProcess(String name){
        for(Process p: complete_list){
            if(p.name == name)return p;
        }
        return null;
    }
    public String getProcess(int startIndex){
        for (Map.Entry<String, List<Pair>> entry : execution_order.entrySet()) {
            for (Pair p: entry.getValue()) {
                if(startIndex == p.start)return entry.getKey();
            }
        }
        return "";
    }
    public int calculateMaxTime() {
        Map<String, List<Pair>> mp = this.execution_order;
        int maxTime = 0;
        for (Map.Entry<String, List<Pair>> entry : mp.entrySet()) {
            for (Pair pair: entry.getValue()) {
                maxTime = Math.max(maxTime, pair.end);
            }
        }
        return maxTime;
    }
    public int calculateMaxStart() {
        Map<String, List<Pair>> mp = this.execution_order;
        int maxTime = 0;
        for (Map.Entry<String, List<Pair>> entry : mp.entrySet()) {
            for (Pair pair: entry.getValue()) {
                maxTime = Math.max(maxTime, pair.start);
            }
        }
        return maxTime;
    }
}