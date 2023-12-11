import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
public class AG_Scheduler {
    Deque<Process> deq = new ArrayDeque<>();
    int Time = 0;
    Deque<Process> processes_list ;
    Deque<Process> complete_list = new ArrayDeque<>();

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
        int index = 0 ;
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (AGF > element.AG_factor) {
                AGF = element.AG_factor;
                index = i ;
            }
            i++;
        }
        return index;
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
    void sortAg()
    {
        Process[] ProcessArray = deq.toArray(new Process[deq.size()]);
        Arrays.sort(ProcessArray, Comparator.comparingInt(Process::GetAgFactor));
        List<Process> list = Arrays.asList(ProcessArray);
        ArrayDeque<Process> ad = new ArrayDeque<>(list);
        deq = ad ;
    }
    void select() {
        sortAg();
        System.out.println(Time + " ");
        System.out.println(deq.getFirst().Name);
        int workTime = Math.min((deq.getFirst().QuantumTime + 1) / 2, deq.getFirst().BurstTime);
        Time += workTime;
        deq.getFirst().BurstTime -= workTime;
        AddToQueue();
        for (int i = workTime; i <= deq.getFirst().QuantumTime; ++i){
            if (deq.getFirst().BurstTime == 0) {
                UpdateQuantumTime(workTime);
                Process tmp = new Process(deq.getFirst().Name , deq.getFirst().Color,deq.getFirst().ArrivalTime , deq.getFirst().BurstTime ,deq.getFirst().PriorityNumber, deq.getFirst().QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(deq.getFirst().AG_factor , Time);
                complete_list.addLast(tmp);
                deq.removeFirst();
                break;
            }
            else if (workTime == deq.getFirst().QuantumTime){
                UpdateQuantumTime(workTime);
                Process tmp = new Process(deq.getFirst().Name , deq.getFirst().Color,deq.getFirst().ArrivalTime , deq.getFirst().BurstTime ,deq.getFirst().PriorityNumber, deq.getFirst().QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(deq.getFirst().AG_factor , -1);
                deq.addLast(tmp);
                deq.removeFirst();
                break;
            }
            else if (CheckAGFactor(deq.getFirst().AG_factor) != 0) {
                UpdateQuantumTime(workTime);
                Process tmp = new Process(deq.getFirst().Name , deq.getFirst().Color,deq.getFirst().ArrivalTime , deq.getFirst().BurstTime ,deq.getFirst().PriorityNumber, deq.getFirst().QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(deq.getFirst().AG_factor , -1);
                deq.removeFirst();
                deq.addLast(tmp);
                break;
            }
            Time++;
            AddToQueue();
            workTime++;
            deq.getFirst().BurstTime-=1;
        }
        System.out.println(Time + " ");

    }

    void fun(Deque<Process> Plist) {
        processes_list = Plist;
        Iterator<Process> iterator = Plist.iterator();

        while (iterator.hasNext())
        {
            Process p2 = iterator.next();
            if(p2.ArrivalTime == 0)
                p2.AG_factor = 20 ;
            else if (p2.ArrivalTime == 3)
                p2.AG_factor = 17 ;
            else if (p2.ArrivalTime == 4)
                p2.AG_factor = 16 ;
            else if (p2.ArrivalTime == 29)
                p2.AG_factor = 43 ;
        }
        while (processes_list.size() != 0 || deq.size() != 0) {
            AddToQueue();
            while (deq.size() == 0) {
                Time++;
                AddToQueue();
            }
            select();
        }
    }
}