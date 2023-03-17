package Service;

public class Signal {
    public static boolean P(Semaphore semaphore,PCB pcb)
    {
        semaphore.setCount(semaphore.getCount()-1);
        if(semaphore.getCount()<0)
        {
            semaphore.W(pcb);
            pcb.setWaitingReason("等待" + semaphore.getName());
            return false;
        }
        return true;
    }
    public static PCB V(Semaphore semaphore)
    {
        semaphore.setCount(semaphore.getCount()+1);
        if(semaphore.getCount()<=0)
        {
            return semaphore.R();
        }
        else
        {
            return null;
        }
    }
}
