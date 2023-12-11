import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        int NumberOfProcesses, QuantumTime, contextSwitching;
        Deque<Process> processes = new ArrayDeque<>();
        System.out.println("Enter Number of processes : ");
        Scanner scanner = new Scanner(System.in);
        NumberOfProcesses =  scanner.nextInt();
        System.out.println("Enter Number of QuantumTime : ");
        QuantumTime =  scanner.nextInt();
        System.out.println("Enter " + NumberOfProcesses + " processes in correct format :");
        for(int i = 0 ;i < NumberOfProcesses ; i++)
        {
            scanner.nextLine() ;
            String Name = scanner.nextLine() ;
            //String Color = scanner.nextLine() ;
            int BurstTime = scanner.nextInt();
            int ArrivalTime = scanner.nextInt();
            int PriorityNumber = scanner.nextInt();
            Process p = new Process(Name , "Color" , ArrivalTime, BurstTime , PriorityNumber , QuantumTime);
            p.CalcAgFactorAndCompleteTime(-1,-1);
            processes.addLast(p);
        }
        while(true){
            System.out.println("""
                    Choose Scheduler Type:
                    1- Shortest Job First Scheduling
                    2- Shortest Remaining Job First Scheduling
                    3- Priority Scheduling
                    4- AG Scheduling
                    5- Exit""");
            int choice = scanner.nextInt();
            if(choice == 1){
                SJF_Scheduler sjfScheduler = new SJF_Scheduler();
                System.out.println("Enter Context Switching : ");
                contextSwitching = scanner.nextInt();
                sjfScheduler.contextSwitching = contextSwitching;
                sjfScheduler.schedule(processes);
                System.out.println("Name    WaitingTime    TurnAroundTime");
                for (Process process : sjfScheduler.complete_list) process.print();
            }else if(choice == 2){
                SRJF_Scheduler srjfScheduler = new SRJF_Scheduler();
                srjfScheduler.schedule(processes);
                System.out.println("Name    WaitingTime    TurnAroundTime");
                for (Process process : srjfScheduler.complete_list) process.print();
            }else if(choice == 3){
                Priority_Scheduler priorityScheduler = new Priority_Scheduler();
                priorityScheduler.schedule(processes);
                System.out.println("Name    WaitingTime    TurnAroundTime");
                for (Process process : priorityScheduler.complete_list) process.print();
            }else if(choice == 4){
                AG_Scheduler agScheduler = new AG_Scheduler();
                agScheduler.schedule(processes);
                System.out.println("Name    WaitingTime    TurnAroundTime");
                for (Process process : agScheduler.complete_list) process.print();
            }
            else break;
        }

    }
    public static Process copyProcess(Process p){
        return new Process(p.name, p.color, p.arrivalTime, p.burstTime, p.priorityNumber, p.quantumTime);
    }
}