import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int NumberOfProcesses, QuantumTime, contextSwitching;
        Deque<Process> processes = new ArrayDeque<>();
        System.out.println("Enter Number of processes : ");
        NumberOfProcesses =  scanner.nextInt();
        System.out.println("Enter Context Switching : ");
        contextSwitching = scanner.nextInt();
        System.out.println("Enter Number of QuantumTime : ");
        QuantumTime =  scanner.nextInt();
        System.out.println("Enter " + NumberOfProcesses + " processes in correct format :");
        for(int i = 0 ;i < NumberOfProcesses ; i++)
        {
            scanner.nextLine() ;
            System.out.print("Name: ");
            String Name = scanner.nextLine() ;
            //String Color = scanner.nextLine() ;
            System.out.print("Burst Time: ");
            int BurstTime = scanner.nextInt();
            System.out.print("Arrival Time: ");
            int ArrivalTime = scanner.nextInt();
            System.out.print("Priority Number: ");
            int PriorityNumber = scanner.nextInt();
            Process p = new Process(Name , "Color" , ArrivalTime, BurstTime , PriorityNumber , QuantumTime);
            p.CalcAgFactorAndCompleteTime(-1,-1);
            processes.addLast(p);
        }
        SJF_Scheduler sjfScheduler = new SJF_Scheduler();
        sjfScheduler.contextSwitching = contextSwitching;
        sjfScheduler.schedule(processes);
        System.out.println("Shortest Job First Scheduling");
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : sjfScheduler.complete_list) process.print();
        calcAverages(sjfScheduler.complete_list);

        SRJF_Scheduler srjfScheduler = new SRJF_Scheduler();
        srjfScheduler.schedule(processes);
        System.out.println("\n\nShortest Remaining Job First Scheduling");
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : srjfScheduler.complete_list) process.print();
        calcAverages(srjfScheduler.complete_list);

        Priority_Scheduler priorityScheduler = new Priority_Scheduler();
        priorityScheduler.schedule(processes);
        System.out.println("\n\nPriority Scheduling");
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : priorityScheduler.complete_list) process.print();
        calcAverages(priorityScheduler.complete_list);

        AG_Scheduler agScheduler = new AG_Scheduler();
        System.out.println("\n\nAG Scheduling");
        agScheduler.schedule(processes);
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : agScheduler.complete_list) process.print();
        calcAverages(agScheduler.complete_list);
    }
    public static void calcAverages(Deque<Process> completeList){
        double averageWaiting = 0, averageTurnaround = 0;
        for (Process p: completeList){
            averageWaiting += p.waitingTime;
            averageTurnaround += p.turnaroundTime;
        }
        System.out.println("Average Waiting: " + averageWaiting / completeList.size() + "\nAverage Turnaround: " + averageTurnaround / completeList.size());
    }
    public static Process copyProcess(Process p){
        return new Process(p.name, p.color, p.arrivalTime, p.burstTime, p.priorityNumber, p.quantumTime);
    }
}