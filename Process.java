import java.util.Random;

public class Process
{
    public String Name;
    public String Color;
    public int ArrivalTime;
    public int BurstTime;
    public int BurstTime2;
    public int PriorityNumber;
    public int CompleteTime;
    public int AG_factor ;
    public int randomNumber;
    public int QuantumTime;

    Process(String Name , String Color , int ArrivalTime , int BurstTime , int PriorityNumber , int QuantumTime )
    {
        this.Name = Name;
        this.Color = Color;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime ;
        this.BurstTime2 = BurstTime;
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
    public void print(){
        // System.out.println("CompleteTime = " + CompleteTime + " " + "ArrivalTime = " + ArrivalTime +" " +  "BurstTime ="+BurstTime2 );
        int turnAroundTime = CompleteTime - ArrivalTime,
                WaitingTime = turnAroundTime - BurstTime2;
        System.out.println(Name + "           " + WaitingTime + "              " + turnAroundTime);
    }
}