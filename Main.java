import java.util.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        AG_Scheduler S4 = new AG_Scheduler();
        Priority_Scheduler S3=new Priority_Scheduler();
        int NumberOfProcesses  , QuantumTime;
        Deque<Process> processes = new ArrayDeque<>();
        List<Process> p1 = new ArrayList<>();

        System.out.println("Enter Number of processes : ");
        try (Scanner scanner = new Scanner(System.in)) {
            NumberOfProcesses =  scanner.nextInt();
            System.out.println("Enter Number of QuantumTime : ");
            QuantumTime =  scanner.nextInt();
            System.out.println("Enter "+NumberOfProcesses+" processes in correct format :");
            for(int i = 0 ;i < NumberOfProcesses ; i++)
            {
                scanner.nextLine() ;
                // System.out.print("Name: ");
                String Name = scanner.next();
                // System.out.print("Burst Time: ");
                int BurstTime = scanner.nextInt();
                // System.out.print("Arrival Time: ");
                int ArrivalTime = scanner.nextInt();
                // System.out.print("Priority Number: ");
                int PriorityNumber = scanner.nextInt();
                Process p = new Process(Name , "Color" , ArrivalTime, BurstTime , PriorityNumber , QuantumTime);
                p.CalcAgFactorAndCompleteTime(-1,-1);
                processes.addLast(p);
                Process p2= new Process(Name , ArrivalTime, BurstTime , PriorityNumber);
                p1.add(p2);
            }
        }
        S4.fun(processes);
        System.out.println("Name    WaitingTime    TurnAroundTime");
        Iterator<Process> iterator = S4.complete_list.iterator();
        while (iterator.hasNext())
        {
            iterator.next().print();
        }

        S3.priorityScheduling(p1);
    }

    public static Process copyProcess(Process p){
        Process p2 = new Process(p.Name, p.Color, p.ArrivalTime, p.BurstTime, p.PriorityNumber, p.QuantumTime);
        return p2;
    }
}