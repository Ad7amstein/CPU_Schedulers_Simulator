import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
public class Scheduler
{
    public static Process copyProcess(Process p){
        return new Process(p.name, p.color, p.arrivalTime, p.burstTime, p.priorityNumber, p.quantumTime);
    }
}