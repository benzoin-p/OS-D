import java.util.List;

public class PCB {
    /*
    * 依次为进程名，
    * 指针，
    * 目前还需运行时间，
    * 优先数，
    * 状态,有创建(new),就绪（ready）,执行（run）,阻塞（block）和结束（ending）,
    * 阻塞开始时间
    * 阻塞持续时间周期数
    * 要求运行时间
    */
    public String proName;
    public PCB next;
    public int runTime;
    public int priority;
    public String status = "New";
    public int blockStartTime = -1;
    public int blockTime = -1;

    public int totalRunTime;

    PCB(int priority,int runTime,String proName,int blockStartTime,int blockTime)
    {
        setProName(proName);
        setPriority(priority);
        setRunTime(runTime);
        setTotalRunTime(runTime);
        setBlockTime(blockTime);
        setBlockStartTime(blockStartTime);
    }

    PCB(int priority,int runTime,String proName)
    {
        setProName(proName);
        setPriority(priority);
        setRunTime(runTime);
        setTotalRunTime(runTime);
    }

    public void enQueueR(List<PCB> Queue)
    {
        Queue.add(this);
        status="Ready";
    }

    public void enQueueE(List<PCB> Queue)
    {
        Queue.add(this);
        status="Ending";
    }

    public void enQueueB(List<PCB> Queue)
    {
        Queue.add(this);
        status="Block";
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setTotalRunTime(int totalRunTime) {
        this.totalRunTime = totalRunTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNext(PCB next) {
        this.next = next;
    }

    public void setBlockStartTime(int blockStartTime) {
        this.blockStartTime = blockStartTime;
    }

    public void setBlockTime(int blockTime) {
        this.blockTime = blockTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getTotalRunTime() {
        return totalRunTime;
    }
}
