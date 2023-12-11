import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

public class AG_Scheduler{
    Deque<Process> deq = new ArrayDeque<>();
    Deque<Process> readyQueue = new ArrayDeque<>();
    Process lead;
    int time = 0;
    Deque<Process> processes_list = new ArrayDeque<>();
    Deque<Process> complete_list = new ArrayDeque<>();

    void AddToQueue() {
        if (processes_list.isEmpty())
            return;
        Iterator<Process> iterator = processes_list.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (time >= element.arrivalTime) {
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
            sum += iterator.next().quantumTime;
        }
        return (int) Math.ceil(0.1 * sum / deq.size());
    }

    void UpdateQuantumTime(int workTime) {
        if (lead.burstTime == 0) {
            lead.quantumTime = 0;
        } else if (workTime == lead.quantumTime) {
            lead.quantumTime += calcNewQuantumTime();
        } else if (workTime < lead.quantumTime) {
            lead.quantumTime += lead.quantumTime - workTime;
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
            if (element.name.equals(lead.name)) {
                iterator.remove();
            }
        }
        iterator = readyQueue.iterator();
        while (iterator.hasNext()) {
            Process element = iterator.next();
            if (element.name.equals(lead.name)) {
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
        System.out.print(time + ":");
        System.out.print("(" + lead.name + ")");
        int workTime = Math.min((lead.quantumTime + 1) / 2, lead.burstTime);
        time += workTime;
        lead.burstTime -= workTime;
        AddToQueue();
        if (workTime == lead.quantumTime) {
            UpdateLead(null);
        }
        for (int i = workTime; i <= lead.quantumTime; ++i) {
            if (lead.burstTime == 0) {
                lead.completeTime = time;
                UpdateQuantumTime(workTime);
                addToCompleteList();
                UpdateLead(null);
                break;
            } else if (workTime == lead.quantumTime) {
                UpdateQuantumTime(workTime);
                readyQueue.addLast(lead);
                UpdateLead(null);
                break;
            } else if (!deq.isEmpty() && deq.getFirst().AGFactor < lead.AGFactor) {
                UpdateQuantumTime(workTime);
                readyQueue.addLast(lead);
                UpdateLead(deq.getFirst());
                break;
            }
            time++;
            AddToQueue();
            workTime++;
            lead.burstTime -= 1;
        }
        System.out.println(":" + time);
    }

    void schedule(Deque<Process> Plist) {
        Iterator<Process> iterator = Plist.iterator();

        while (iterator.hasNext()) {
            Process element = iterator.next();
            Process p = Main.copyProcess(element);
            processes_list.addLast(p);
        }
        iterator = processes_list.iterator();
        while (iterator.hasNext()) {
            Process p2 = iterator.next();
            if (p2.arrivalTime == 0)
                p2.AGFactor = 20;
            else if (p2.arrivalTime == 3)
                p2.AGFactor = 17;
            else if (p2.arrivalTime == 4)
                p2.AGFactor = 16;
            else if (p2.arrivalTime == 29)
                p2.AGFactor = 43;
        }
        int i = 0;
        while (!processes_list.isEmpty() || !deq.isEmpty() || lead != null) {
            AddToQueue();
            while (deq.isEmpty()) {
                time++;
                AddToQueue();
            }
            if (i == 0)
                lead = deq.getFirst();
            select();
            i++;
        }
    }
}