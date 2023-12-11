import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        AG_Scheduler S4 = new AG_Scheduler();
        int NumberOfProcesses  , QuantumTime;
        Deque<Process> processes = new ArrayDeque<>();
        System.out.println("Enter Number of processes : ");
        Scanner scanner = new Scanner(System.in);
        NumberOfProcesses =  scanner.nextInt();
        System.out.println("Enter Number of QuantumTime : ");
        QuantumTime =  scanner.nextInt();
        System.out.println("Enter "+NumberOfProcesses+" processes in correct format :");
        for(int i = 0 ;i < NumberOfProcesses ; i++)
        {
            scanner.nextLine() ;
            String Name = scanner.nextLine() ;
//            String Color = scanner.nextLine() ;
            int BurstTime = scanner.nextInt();
            int ArrivalTime = scanner.nextInt();
            int PriorityNumber = scanner.nextInt();
            Process p = new Process(Name , "Color" , ArrivalTime, BurstTime , PriorityNumber , QuantumTime);
            p.CalcAgFactorAndCompleteTime(-1,-1);
            processes.addLast(p);
        }
        S4.fun(processes);
        Iterator<Process> iterator = S4.complete_list.iterator();
        while (iterator.hasNext())
        {
            iterator.next().print();
//            System.out.println("A");
        }
    }
}
