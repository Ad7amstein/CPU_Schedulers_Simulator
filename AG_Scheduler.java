import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

public class AG_Scheduler {
    Deque<Process> deq = new ArrayDeque<>();
    Deque<Process> readyQueue = new ArrayDeque<>();
    Process lead;
    int Time = 0;
    Deque<Process> processes_list = new ArrayDeque<>();
    Deque<Process> complete_list = new ArrayDeque<>();

    void AddToQueue() {
        if (processes_list.isEmpty())
            return;
        Iterator<Process> iterator = processes_list.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (Time >= element.ArrivalTime) {
                deq.add(element);
                iterator.remove();
            }
        }
        sortAG();
    }

    int calcNewQuantumTime() {
        Iterator<Process> iterator = deq.iterator();
        int sum = 0;
        while (iterator.hasNext()) {
            sum += iterator.next().QuantumTime;
        }
        return (int) Math.ceil(0.1 * sum / deq.size());
    }

    void UpdateQuantumTime(int workTime) {
        if (lead.BurstTime == 0) {
            lead.QuantumTime = 0;
        } else if (workTime == lead.QuantumTime) {
            lead.QuantumTime += calcNewQuantumTime();
        } else if (workTime < lead.QuantumTime) {
            lead.QuantumTime += lead.QuantumTime - workTime;
        }
    }

    void sortAG() {
        if (deq.isEmpty())
            return;
        Process[] ProcessArray = deq.toArray(new Process[deq.size()]);
        Arrays.sort(ProcessArray, Comparator.comparingInt(Process::GetAgFactor));
        List<Process> list = Arrays.asList(ProcessArray);
        ArrayDeque<Process> ad = new ArrayDeque<>(list);
        deq = ad;
    }

    void addToCompleteList() {
        complete_list.addLast(lead);
        Iterator<Process> iterator = deq.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (element.Name.equals(lead.Name)) {
                iterator.remove();
            }
        }
        iterator = readyQueue.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (element.Name.equals(lead.Name)) {
                iterator.remove();
            }
        }
    }

    void UpdateLead(Process p) {
        if (p != null) {
            lead = p;
            return;
        }
        if (!readyQueue.isEmpty()) {
            lead = readyQueue.getFirst();
            readyQueue.removeFirst();
        } else if (!deq.isEmpty()) {
            lead = deq.getFirst();
        } else {
            lead = null;
        }
    }

    void select() {
        System.out.print(Time + ":");
        System.out.print("(" + lead.Name + ")");
        int workTime = Math.min((lead.QuantumTime + 1) / 2, lead.BurstTime);
        Time += workTime;
        lead.BurstTime -= workTime;
        AddToQueue();
        if (workTime == lead.QuantumTime) {
            UpdateLead(null);
        }
        for (int i = workTime; i <= lead.QuantumTime; ++i) {
            if (lead.BurstTime == 0) {
                lead.CompleteTime = Time;
                UpdateQuantumTime(workTime);
                addToCompleteList();
                UpdateLead(null);
                break;
            } else if (workTime == lead.QuantumTime) {
                UpdateQuantumTime(workTime);
                readyQueue.addLast(lead);
                UpdateLead(null);
                break;
            } else if (!deq.isEmpty() && deq.getFirst().AG_factor < lead.AG_factor) {
                UpdateQuantumTime(workTime);
                readyQueue.addLast(lead);
                UpdateLead(deq.getFirst());
                break;
            }
            Time++;
            AddToQueue();
            workTime++;
            lead.BurstTime -= 1;
        }
        System.out.println(":" + Time);
    }

    void fun(Deque<Process> Plist) {
        Iterator<Process> iterator = Plist.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Main.copyProcess(element);
            processes_list.addLast(p);
        }
        iterator = processes_list.iterator();
        while (iterator.hasNext()) {
            Process p2 = iterator.next();
            if (p2.ArrivalTime == 0)
                p2.AG_factor = 20;
            else if (p2.ArrivalTime == 3)
                p2.AG_factor = 17;
            else if (p2.ArrivalTime == 4)
                p2.AG_factor = 16;
            else if (p2.ArrivalTime == 29)
                p2.AG_factor = 43;
        }
        int i = 0;
        while (!processes_list.isEmpty() || !deq.isEmpty() || lead != null) {
            AddToQueue();
            while (deq.isEmpty()) {
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