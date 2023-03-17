import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("[Information] 模拟开始!");
        FileSystem fileSystem = new FileSystem();
        // 文件描述符（String）与文件的键值对
        Map<String,File> fileMap = new HashMap<>();
        // 存放数据的缓冲区
        List<Character> charBuffer = new ArrayList<>();
        while(true)
        {
            System.out.println("请输入指令，输入help查看指令表，输入file查看文件句柄,输入r读缓冲区，输入w写缓冲区，输入exit退出系统");
            Scanner s = new Scanner(System.in);
            String instruction = s.nextLine();
            if(instruction.equals("help"))
            {
                System.out.println("dir 用户名 :列出文件目录");
                System.out.println("open 用户名/文件名 :打开文件");
                System.out.println("close 用户文件描述符 :关闭文件");
                System.out.println("create 用户名/文件名/保护字段 :创建文件");
                System.out.println("delete 用户名/文件名 :删除文件");
                System.out.println("read 文件描述符/存放读数据的缓冲区/本次读字符数 :读文件，缓冲区默认");
                System.out.println("write 文件描述符/存放写数据的缓冲区/本次写字符数 :写文件，缓冲区默认");
                continue;
            }
            else if(instruction.equals("file"))
            {
                for(Map.Entry<String,File> entry:fileMap.entrySet())
                {
                    System.out.println(entry.getKey());
                }

            }
            else if(instruction.equals("r"))
            {
                StringBuilder t= new StringBuilder();
                for(int i=0;i<charBuffer.size();i++)
                {
                    char c = charBuffer.get(i);
                    t.append(c);
                }
                System.out.println(t);
            }
            else if(instruction.equals("w"))
            {
                System.out.println("请输入要写入的内容:");
                String t = s.nextLine();
                for(int i=0;i<t.length();i++)
                {
                    char c = t.charAt(i);
                    charBuffer.add(c);
                }
            }
            else if(instruction.equals("exit"))
            {
                break;
            }
            String[] strings = instruction.split(" ");
            if(strings.length>2)
            {
                System.out.println("请保证指令与参数间有且仅有一个空格");
                continue;
            }
            String[] arguments;
            File file;
            switch (strings[0]){

                case "dir":
                    fileSystem.dir(strings[1]);
                    break;

                case "open":
                    arguments = strings[1].split("/");
                    file = fileSystem.open(arguments[0],arguments[1]);
                    if(file != null)
                    {
                        fileMap.put(arguments[1],file);
                    }
                    break;

                case "close":
                    file = fileMap.get(strings[1]);
                    fileSystem.close(file);
                    break;

                case "create":
                    arguments = strings[1].split("/");
                    file = fileSystem.create(arguments[0],arguments[1],arguments[2].charAt(0));
                    if(file != null)
                    {
                        fileMap.put(arguments[1],file);
                    }
                    break;

                case "delete":
                    arguments = strings[1].split("/");
                    fileSystem.delete(arguments[0],arguments[1]);
                    break;

                case "read":
                    arguments = strings[1].split("/");
                    file = fileMap.get(arguments[0]);
                    fileSystem.read(file,charBuffer,Integer.parseInt(arguments[2]));
                    break;

                case "write":
                    arguments = strings[1].split("/");
                    file = fileMap.get(arguments[0]);
                    fileSystem.write(file,charBuffer,Integer.parseInt(arguments[2]));
                    break;

                default:
                    break;

            }
        }
    }
}

/*
* 测试数据
* WHAT THE HELL'S GOING ON?! CAN SOMEONE TELL ME PLEASE,WHY I'M SWITCHING FASTER THAN THE CHANNELS ON TV!!
* 读入26个字符可读完第一句话
* 再通过写26个字符观察缓冲区*/
