import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Iterator;

public class AG_Scheduler {
    Deque<Process> deq = new ArrayDeque<>();
    int Time = 0;
    Deque<Process> processes_list = new LinkedList<>();
    Deque<Process> complete_list;

    void AddToQueue() {
        Iterator<Process> iterator = processes_list.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (Time >= element.ArrivalTime) {
                deq.add(element);
                processes_list.remove(element);
            }
        }
    }

    int CheckAGFactor(int AGF){
        Iterator<Process> iterator = deq.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (AGF > element.AG_factor) {
                return i;
            }
            i++;
        }
        return -1;
    }

    int calcNewQuantumTime(){
        Iterator<Process> iterator = deq.iterator();
        int sum = 0;
        while (iterator.hasNext()){
            sum += iterator.next().QuantumTime;
        }
        return (int)Math.ceil(0.1 * sum / deq.size());
    }

    void UpdateQuantumTime(int workTime) {
        if (deq.getFirst().BurstTime == 0){
            deq.getFirst().QuantumTime = 0;
        }
        else if (workTime == deq.getFirst().QuantumTime){
            deq.getFirst().QuantumTime += calcNewQuantumTime();
        }
        else if (workTime < deq.getFirst().QuantumTime){
            deq.getFirst().QuantumTime += deq.getFirst().QuantumTime - workTime;
        }
    }

    void select() {
        int workTime = Math.min((deq.getFirst().QuantumTime + 1) / 2, deq.getFirst().BurstTime);
        Time += workTime;
        deq.getFirst().BurstTime -= workTime;
        AddToQueue();
        for (int i = workTime; i <= deq.getFirst().QuantumTime; ++i){
            if (deq.getFirst().BurstTime == 0) {
                UpdateQuantumTime(workTime);
            }
            else if (workTime == deq.getFirst().QuantumTime){
                UpdateQuantumTime(workTime);
            }
            else if (CheckAGFactor(deq.getFirst().AG_factor) != -1) {
                UpdateQuantumTime(workTime);
            }
            AddToQueue();
            Time++;
            workTime++;
            deq.getFirst().BurstTime--;
        }
    }

    void fun(Deque<Process> Plist) {
        processes_list = Plist;
        while (processes_list.size() != 0 || deq.size() != 0) {
            AddToQueue();
            while (deq.size() == 0) {
                Time++;
                AddToQueue();
            }
        }
    }
}
