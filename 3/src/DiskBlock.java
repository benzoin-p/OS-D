public class DiskBlock {
    //假设磁盘中共有64个块
    private int num;

    private int free;

    //假设一个块有32字节（为方便分配空间）
    private char[] chars = new char[32];

    public static int[] DiskBlockList = new int[64];
    public DiskBlock(int num) {
        this.num = num;
    }

    public static int getFree()
    {
        for(int i=0;i<64;i++)
        {
            if(DiskBlockList[i] != 1)
            {
                return i;
            }
        }
        return -1;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public int getNum() {
        return num;
    }
}
