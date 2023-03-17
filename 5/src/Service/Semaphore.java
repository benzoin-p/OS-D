package Service;

import java.util.ArrayList;
import java.util.List;

public class Semaphore {
    private int count;
    private List<PCB> pcbQueue = new ArrayList<>();

    private String name;

    Semaphore(int count,String name)
    {
        this.count = count;
        this.name = name;
    }

    //W操作，使调用该操作的进程进入等待队列
    public void W(PCB pcb)
    {
        pcbQueue.add(pcb);
    }

    //R操作，释放一个处于等待队列中的进程
    public PCB R()
    {
        PCB pcb = pcbQueue.get(pcbQueue.size()-1);
        pcbQueue.remove(pcbQueue.size()-1);
        return pcb;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPcbQueue(List<PCB> pcbQueue) {
        this.pcbQueue = pcbQueue;
    }
}
