import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Scanner;

public class GanttChartSwing extends JFrame {
    Deque<Process> processes;
    int maxTime;
    private JPanel[][] gridPanels;

    public GanttChartSwing(Deque<Process> processes, int maxTime) {
        this.processes = processes;
        this.maxTime = maxTime;
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        int rows = processes.size() + 1, columns = maxTime + 2;
        gridPanels = new JPanel[rows][columns];

        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Allow the panels to grow in both dimensions

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(30, 20));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanels[i][j] = panel;

                gbc.gridx = j;
                gbc.gridy = i;

                gridPanel.add(panel, gbc);
            }
        }

        addHeaders();
        Iterator<Process> it = processes.iterator();
        int ind = 1;
        while (it.hasNext()) {
            Process p = it.next();
            for (int i = p.completeTime - p.burstTime + 1; i <= p.completeTime + 1; i++) {
                JPanel curr = gridPanels[ind][i];
                curr.setBackground(p.color);
            }
            ind++;
        }

        // Add the grid panel to the top part of the frame
        add(gridPanel, BorderLayout.CENTER);

        // Create a panel for text below the grid
        JPanel textPanel = new JPanel();
        JLabel textLabel = new JLabel("Your text goes here.");
        textPanel.add(textLabel);

        // Add the text panel to the bottom part of the frame
        add(textPanel, BorderLayout.SOUTH);

        pack(); // Pack the components so the layout takes effect
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addHeaders() {
        for (int i = 1; i <= processes.size(); i++) {
            JPanel curr = gridPanels[i][0];
            JLabel label = new JLabel("P" + i);
            curr.add(label);
        }
        for (int i = 1; i <= maxTime; i++) {
            JPanel curr = gridPanels[0][i];
            JLabel label = new JLabel(String.valueOf(i-1));
            curr.add(label);
        }
    }

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

        SwingUtilities.invokeLater(() -> {
            int maxTime = calculateMaxTime(sjfScheduler.complete_list);
            GanttChartSwing gui = new GanttChartSwing(sjfScheduler.complete_list, maxTime);
            gui.setVisible(true);
        });
    }

    public static void calcAverages(Deque<Process> completeList) {
        double averageWaiting = 0, averageTurnaround = 0;
        for (Process p : completeList) {
            averageWaiting += p.waitingTime;
            averageTurnaround += p.turnaroundTime;
        }
        System.out.println("Average Waiting: " + averageWaiting / completeList.size() + "\nAverage Turnaround: " + averageTurnaround / completeList.size());
    }

    private static int calculateMaxTime(Deque<Process> processes) {
        int maxTime = 0;
        for (Process process : processes) {
            maxTime = Math.max(maxTime, process.completeTime);
        }
        return maxTime;
    }
}
