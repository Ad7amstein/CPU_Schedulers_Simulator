import java.util.Random;

public class Process
{
    public String Name;
    public String Color;
    public int ArrivalTime;
    public int BurstTime;
    public int PriorityNumber;
    public int CompleteTime;
    public int AG_factor ;
    public int randomNumber;
    public int QuantumTime;
    Process(String Name , String Color , int ArrivalTime , int BurstTime , int PriorityNumber , int QuantumTime)
    {
        CompleteTime = 0;
        this.Name = Name;
        this.Color = Color;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime ;
        this.PriorityNumber = PriorityNumber ;
        this.QuantumTime=QuantumTime;
        Random random = new Random();
        randomNumber = random.nextInt(21);
        if(randomNumber < 10)
            AG_factor = randomNumber + ArrivalTime + AG_factor;
        else if (randomNumber > 10)
            AG_factor = 10 + ArrivalTime + AG_factor;
        else
            AG_factor = PriorityNumber + ArrivalTime + AG_factor;
    }
    public void print(){
        int turnAroundTime = CompleteTime - ArrivalTime,
                WaitingTime = turnAroundTime - BurstTime;
        System.out.printf("%-12s %-13d %d%n", Name, WaitingTime, turnAroundTime);
    }
}