package Service;

import java.util.List;

public class CPUInstruction {

    //java没有函数指针故通过该方法模拟
    //param表中对象依次为PCB,char
    public Object process(String args,List<Object> param)
    {
        switch (args)
        {
            case "produce":
                return produce();
            case "p(s2)":
                P(CPU.s2,(PCB)param.get(0));
                return null;
            case "p(s1)":
                P(CPU.s1,(PCB)param.get(0));
                return null;
            case "PUT":
                put((char)param.get(1));
                return null;
            case "v(s1)":
                V(CPU.s1,(PCB)param.get(0));
                return null;
            case "v(s2)":
                V(CPU.s2,(PCB)param.get(0));
                return null;
            case "goto 0":
                goTo(0);
                return null;
            case "GET":
                return get();
            case "consume":
                consume((char)param.get(1));
                return null;
            default:
                System.out.println("输入错误");
                return null;
        }
    }




    //p(s)
    public void P(Semaphore semaphore,PCB pcb)
    {
        //s>0,将调用p过程的进程设置为就绪态
        if(Signal.P(semaphore,pcb))
        {
            pcb.setStatus("ready");
            CPU.readyList.add(pcb);
        }
        //s<=0,将调用p过程的进程设置为阻塞态
        else
        {
            pcb.setStatus("block");
            CPU.blockList.add(pcb);
            pcb.setBreakPoint(CPU.PC);
        }
    }

    //v(s)
    public void V(Semaphore semaphore,PCB pcb)
    {
        PCB chosenPCB = Signal.V(semaphore);
        //s<=0,将等待s信号量的进程置为就绪态
        if(chosenPCB != null)
        {
            CPU.blockList.remove(chosenPCB);
            chosenPCB.setStatus("ready");
            CPU.readyList.add(chosenPCB);
            pcb.setStatus("ready");
            CPU.readyList.add(pcb);
        }
        //s>0，将调用v过程的进程置位就绪态
        else
        {
            pcb.setStatus("ready");
            CPU.readyList.add(pcb);
        }
    }

    //PUT
    public void put(char product)
    {
        CPU.products[CPU.in] = product;
        CPU.in = (CPU.in+1)%10;
    }

    //GET
    public char get()
    {
        char product = CPU.products[CPU.out];
        CPU.products[CPU.out] = null;
        CPU.out = (CPU.out+1)%10;
        return product;
    }

    //produce
    public char produce()
    {
        return 'p';
    }

    //consume
    public void consume(char p)
    {
        System.out.println("[OUT]消费者消费:"+p);
    }

    //GOTO L
    public void goTo(int l)
    {
        CPU.PC = l;
    }
}
