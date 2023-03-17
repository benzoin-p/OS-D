public class Table {
    //假设主存中也有64个块
    private int num;

    public static int[] TableList = new int[64];
    public Table(int num) {
        this.num = num;
    }
}
