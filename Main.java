import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int NumberOfProcesses, QuantumTime, contextSwitching;
        Deque<Process> processes = new ArrayDeque<>();
        System.out.println("Enter Number of processes : ");
        NumberOfProcesses = scanner.nextInt();
        System.out.println("Enter Context Switching : ");
        contextSwitching = scanner.nextInt();
        System.out.println("Enter Number of QuantumTime : ");
        QuantumTime = scanner.nextInt();
        System.out.println("Enter " + NumberOfProcesses + " processes in correct format :");
        for (int i = 0; i < NumberOfProcesses; i++) {
            scanner.nextLine();
            System.out.print("Name: ");
            String Name = scanner.nextLine();
            System.out.print("Pick a Color: ");
            Color Color = JColorChooser.showDialog(null, "Choose color for " + Name, java.awt.Color.RED);
            if(Color != null){
                System.out.println(Color);
            }
            System.out.print("Burst Time: ");
            int BurstTime = scanner.nextInt();
            System.out.print("Arrival Time: ");
            int ArrivalTime = scanner.nextInt();
            System.out.print("Priority Number: ");
            int PriorityNumber = scanner.nextInt();
            Process p = new Process(Name, Color, ArrivalTime, BurstTime, PriorityNumber, QuantumTime);
            p.CalcAgFactorAndCompleteTime(-1, -1);
            processes.addLast(p);
        }

        SJF_Scheduler sjfScheduler = new SJF_Scheduler();
        sjfScheduler.contextSwitching = contextSwitching;
        sjfScheduler.schedule(processes);
        System.out.println("Shortest Job First Scheduling");
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : sjfScheduler.complete_list) process.print();
        calcAverages(sjfScheduler.complete_list);

        SRTF_Scheduler srtfScheduler = new SRTF_Scheduler();
        srtfScheduler.schedule(processes);
        System.out.println("\n\nShortest Remaining Job First Scheduling");
        srtfScheduler.printExecutionOrder();
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : srtfScheduler.complete_list) process.print();
        calcAverages(srtfScheduler.complete_list);

        Priority_Scheduler priorityScheduler = new Priority_Scheduler();
        priorityScheduler.schedule(processes);
        System.out.println("\n\nPriority Scheduling");
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : priorityScheduler.complete_list) process.print();
        calcAverages(priorityScheduler.complete_list);

        AG_Scheduler agScheduler = new AG_Scheduler();
        agScheduler.contextSwitching = contextSwitching;
        System.out.println("\n\nAG Scheduling");
        agScheduler.schedule(processes);
        System.out.println("Name    WaitingTime    TurnAroundTime");
        for (Process process : agScheduler.complete_list) process.print();
        calcAverages(agScheduler.complete_list);

        GanttChartSwing gui = new GanttChartSwing(sjfScheduler, "SJF Scheduler");
        GanttChartSwing gui2 = new GanttChartSwing(srtfScheduler, "SRTF Scheduler");
        GanttChartSwing gui3 = new GanttChartSwing(priorityScheduler, "Priority Scheduler");
        GanttChartSwing gui4 = new GanttChartSwing(agScheduler, "AG Scheduler");
    }
    public static void calcAverages(Deque<Process> completeList) {
        double averageWaiting = 0, averageTurnaround = 0;
        for (Process p : completeList) {
            averageWaiting += p.waitingTime;
            averageTurnaround += p.turnaroundTime;
        }
        System.out.println("Average Waiting: " + averageWaiting / completeList.size() + "\nAverage Turnaround: " + averageTurnaround / completeList.size());
    }
}
