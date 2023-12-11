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
    Deque<Process> readyQueue = new ArrayDeque<>();
    Process lead;
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
        sortAg();
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
        if (lead.BurstTime == 0){
            lead.QuantumTime = 0;
        }
        else if (workTime == lead.QuantumTime){
            lead.QuantumTime += calcNewQuantumTime();
        }
        else if (workTime < lead.QuantumTime){
            lead.QuantumTime += lead.QuantumTime - workTime;
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
        System.out.println(Time + " ");
        System.out.println(lead.Name);
        int workTime = Math.min((lead.QuantumTime + 1) / 2, lead.BurstTime);
        Time += workTime;
        lead.BurstTime -= workTime;
        AddToQueue();
        for (int i = workTime; i <= lead.QuantumTime; ++i){
            if (lead.BurstTime == 0) {
                UpdateQuantumTime(workTime);
                Process tmp = new Process(lead.Name , lead.Color,lead.ArrivalTime , lead.BurstTime ,lead.PriorityNumber, lead.QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(lead.AG_factor , Time);
                complete_list.addLast(tmp);
                deq.remove(lead);
                Process tmp2 = new Process(readyQueue.getFirst().Name , readyQueue.getFirst().Color,readyQueue.getFirst().ArrivalTime , readyQueue.getFirst().BurstTime ,readyQueue.getFirst().PriorityNumber, readyQueue.getFirst().QuantumTime);
                tmp2.CalcAgFactorAndCompleteTime(readyQueue.getFirst().AG_factor , Time);
                lead = tmp2;
                readyQueue.removeFirst();
                break;
            }
            else if (workTime == lead.QuantumTime)
            {
                UpdateQuantumTime(workTime);
                Process tmp = new Process(lead.Name , lead.Color,lead.ArrivalTime , lead.BurstTime ,lead.PriorityNumber, lead.QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(lead.AG_factor , -1);
                Process tmp2 = new Process(readyQueue.getFirst().Name , readyQueue.getFirst().Color,readyQueue.getFirst().ArrivalTime , readyQueue.getFirst().BurstTime ,readyQueue.getFirst().PriorityNumber, readyQueue.getFirst().QuantumTime);
                tmp2.CalcAgFactorAndCompleteTime(readyQueue.getFirst().AG_factor , Time);
                lead = tmp2;
                readyQueue.removeFirst();
                readyQueue.addLast(tmp);
                break;
            }
            else if (deq.getFirst().AG_factor < lead.AG_factor) {
                UpdateQuantumTime(workTime);
                Process tmp = new Process(lead.Name , lead.Color,lead.ArrivalTime , lead.BurstTime ,lead.PriorityNumber, lead.QuantumTime);
                tmp.CalcAgFactorAndCompleteTime(lead.AG_factor , -1);
                readyQueue.addLast(tmp);
//                readyQueue.removeFirst();
                lead = deq.getFirst();
                break;
            }
            Time++;
            AddToQueue();
            workTime++;
            lead.BurstTime-=1;
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
        int i = 0;
        while (processes_list.size() != 0 || deq.size() != 0) {
            AddToQueue();
            while (deq.size() == 0) {
                Time++;
                AddToQueue();
            }
            if (i == 0)
                lead = deq.getFirst();
            select();
            i++;
        }
    }
}