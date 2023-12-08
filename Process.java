public class Process {
    private String Name;
    private String Color;
    private int ArrivalTime;
    private int BurstTime;
    private int PriorityNumber;
    private int CompleteTime;

    public void print(){
        int turnAroundTime = CompleteTime - ArrivalTime,
            WaitingTime = turnAroundTime - BurstTime;
        System.out.printf("%-12s %-13d %d%n", Name, WaitingTime, turnAroundTime);
    }
}