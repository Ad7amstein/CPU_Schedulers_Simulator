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
    public int WaitingTime;
    public int TurnaroundTime;
    //    @Override
//    public String toString() {
//        return "Process{" +
//                "AgFactor="+AG_factor +
//                '}';
//    }
    Process(String Name , String Color , int ArrivalTime , int BurstTime , int PriorityNumber , int QuantumTime )
    {
        this.Name = Name;
        this.Color = Color;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime ;
        this.PriorityNumber = PriorityNumber ;
        this.QuantumTime=QuantumTime;

    }
    void CalcAgFactorAndCompleteTime  (int n , int m)
    {
        if(n == -1)
        {
            Random random = new Random();
            randomNumber = random.nextInt(21);
            if(randomNumber < 10)
                AG_factor = randomNumber + ArrivalTime + AG_factor;
            else if (randomNumber > 10)
                AG_factor = 10 + ArrivalTime + AG_factor;
            else
                AG_factor = PriorityNumber + ArrivalTime + AG_factor;
        }
        else
            AG_factor = n ;
        if(m == -1)
            CompleteTime = 0;
        else
            CompleteTime = m;
    }
    int GetAgFactor ()
    {
        return AG_factor;
    }
    public Process(String Name, int ArrivalTime, int BurstTime, int PriorityNumber){
        this.Name = Name;;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.PriorityNumber = PriorityNumber;
        this.WaitingTime=0;
    }
    public void aging() {
        if (WaitingTime >= 5) {
            this.PriorityNumber--;
            this.WaitingTime = 0;
        } else {
            this.WaitingTime++;
        }
    }
    public int getPriority() {
        return PriorityNumber;
    }
    public void print(){
        System.out.println("CompleteTime = " + CompleteTime + " " + "ArrivalTime = " + ArrivalTime +" " +  "BurstTime ="+BurstTime );
        int turnAroundTime = CompleteTime - ArrivalTime,
                WaitingTime = turnAroundTime - BurstTime;
        System.out.printf("%-12s %-13d %d%n", Name, WaitingTime, turnAroundTime);
    }
}