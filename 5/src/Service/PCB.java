package Service;

//用来表示进程
public class PCB {

    /*
    进程名
    状态（进程的状态有：运行态、就绪态、等待态和完成态。）
    等待原因（在模拟实验中进程等待原因是为等待信号量s1或s2）
    断点
    */

    private String processName;
    private String status;
    private String waitingReason;
    private int breakPoint;

    PCB(String processName,String status,int breakPoint)
    {
        this.processName = processName;
        this.status =status;
        this.breakPoint = breakPoint;
    }

    public void printPCB()
    {
        System.out.print("[OUT]进程名为:" + processName + " ");
        System.out.print("状态为:" + status+ " ");
        if(status.equals("block"))
        {
            System.out.print("等待原因为:" + waitingReason+ " ");
        }
        System.out.println();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaitingReason() {
        return waitingReason;
    }

    public void setWaitingReason(String waitingReason) {
        this.waitingReason = waitingReason;
    }

    public int getBreakPoint() {
        return breakPoint;
    }

    public void setBreakPoint(int breakPoint) {
        this.breakPoint = breakPoint;
    }
}
