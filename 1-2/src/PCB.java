import java.util.List;

public class PCB {
    /*
     * 依次为进程名，
     * 目前还需运行时间，
     * 状态,有创建(new),就绪（ready）,执行（run）,阻塞（block）和结束（ending）,
     * 要求运行时间
     */
    public String proName;
    public int runTime;
    public String status = "Ready";

    public int totalRunTime;

    PCB(int runTime,String proName)
    {
        setProName(proName);
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

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalRunTime() {
        return totalRunTime;
    }

    public void setTotalRunTime(int totalRunTime) {
        this.totalRunTime = totalRunTime;
    }
}
