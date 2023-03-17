package CPUInstruction;

import CPUInstruction.CallBackInterFace;
import Service.PCB;
import Service.Semaphore;
import Service.Signal;
import Service.CPU;

import java.util.ArrayList;
import java.util.List;


public class P implements CallBackInterFace {

    List<Object> argsList = new ArrayList<>();
    public void P(Semaphore semaphore, PCB pcb)
    {
        argsList.add(semaphore);
        argsList.add(pcb);
    }
    @Override
    public Object process(List param) {
        //s>0,将调用p过程的进程设置为就绪态
        if(Signal.P((Semaphore) param.get(0),(PCB) param.get(1)))
        {
            CPU.readyList.add((PCB) param.get(1));
        }
        //s<=0,将调用p过程的进程设置为阻塞态
        else
        {
            CPU.blockList.add((PCB) param.get(1));
        }
        return null;
    }
}
