import java.awt.*;
import java.util.Random;

public class Process {
    public String name;
    public Color color;
    public int arrivalTime;
    public int burstTime;
    public int burstTime2;
    public int priorityNumber;
    public int completeTime;
    public int AGFactor;
    public int randomNumber;
    public int quantumTime;
    public int waitingTime;
    public int turnaroundTime;
    public int starvation;

    Process(String Name, Color Color, int ArrivalTime, int BurstTime, int PriorityNumber, int QuantumTime) {
        this.name = Name;
        this.color = Color;
        this.arrivalTime = ArrivalTime;
        this.burstTime = BurstTime;
        this.burstTime2 = BurstTime;
        this.priorityNumber = PriorityNumber;
        this.quantumTime = QuantumTime;
        waitingTime = 0;
        completeTime = 0;
        starvation = 0;
    }

    void CalcAgFactorAndCompleteTime(int n, int m) {
        if (n == -1) {
            Random random = new Random();
            randomNumber = random.nextInt(21);
            if (randomNumber < 10)
                AGFactor = randomNumber + arrivalTime + AGFactor;
            else if (randomNumber > 10)
                AGFactor = 10 + arrivalTime + AGFactor;
            else
                AGFactor = priorityNumber + arrivalTime + AGFactor;
        } else
            AGFactor = n;
        if (m == -1)
            completeTime = 0;
        else
            completeTime = m;
    }

    int GetAgFactor() {
        return AGFactor;
    }
    public void aging(int currentTime) {
        if (currentTime - arrivalTime >= 5) {
            this.priorityNumber--;
            this.waitingTime = 0;
        } else {
            this.waitingTime = currentTime - arrivalTime;
        }
    }
    public void print() {
        int turnAroundTime = completeTime - arrivalTime,  waitingTime = turnAroundTime - burstTime2;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnAroundTime;
        System.out.println(name + "           " + waitingTime + "              " + turnAroundTime);
    }
}



