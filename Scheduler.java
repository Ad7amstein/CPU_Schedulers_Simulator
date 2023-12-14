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
}