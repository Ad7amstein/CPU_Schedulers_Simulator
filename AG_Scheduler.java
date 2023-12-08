import java.util.Deque;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Iterator;
public class AG_Scheduler
{
    Deque<Process> deq = new ArrayDeque<>() ;
    int Time = 0 ;
    Deque<Process> processes_list=  new LinkedList<>();
    Deque<Process> complete_list;

    void AddToQueue ()
    {
        Iterator<Process> iterator = processes_list.iterator();
        while (iterator.hasNext())
        {
            Process element = iterator.next();
            if(Time>= element.ArrivalTime)
            {
                deq.add(element);
                processes_list.remove(element);
            }
        }
    }
    void select()
    {

    }
    void fun (Deque<Process> Plist)
    {
        processes_list = Plist;
        while (processes_list.size() != 0 || deq.size() != 0)
        {
            AddToQueue();
            while (deq.size()==0)
            {
                Time++;
                AddToQueue();
            }

        }
    }
}
