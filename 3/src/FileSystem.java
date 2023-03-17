import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final List<User> userList = new ArrayList<>();
    private MainCatalogue mainCatalogue = new MainCatalogue();
    private OpenCatalogue openCatalogue = new OpenCatalogue();

    FileSystem()
    {
        //初始化用户(此处初始4个用户)
        userList.add(new User("Tiamat",new UserCatalogue(),mainCatalogue));
        userList.add(new User("Titania",new UserCatalogue(),mainCatalogue));
        userList.add(new User("Titans",new UserCatalogue(),mainCatalogue));
        userList.add(new User("Terra",new UserCatalogue(),mainCatalogue));
        mainCatalogue.num = 4;

        //为其中两个用户初始化文件
        userList.get(0).userCatalogue
                .addFile(new File("TiamatFile1",'R',5,0,false));
        userList.get(0).userCatalogue
                .addFile(new File("TiamatFile2",'W',6,6,false));
        userList.get(1).userCatalogue
                .addFile(new File("TitaniaFile1",'X',1,13,false));

        //

    }
    public void dir(String userName)
    {
        for(User user:userList)
        {
            if(userName.equals(user.getUserName()))
            {
                 UserCatalogue t = mainCatalogue.userCatalogueMap.get(userName);
                 for(File file: t.fileList)
                 {
                     //② 显示该用户文件目录的内容，包含文件名、文件长度，保护字段。
                     System.out.println
                             ("文件名:"+file.getFileName()+" 文件长度:"+file.getFileSize()+" 保护字段"+file.getProtectChar());
                 }
                 return;
            }
        }
        //① 判断用户是否存在，若不存在显示错误信息并返回；
        System.out.println("不存在该用户");
        return;
    }

    public File open(String userName,String fileName)
    {
        for(User user:userList)
        {
            if(userName.equals(user.getUserName()))
            {
                UserCatalogue t = mainCatalogue.userCatalogueMap.get(userName);
                for(File file: t.fileList)
                {
                    if(fileName.equals(file.getFileName()))
                    {
                        //③申请一个空闲的系统打开文件表项，若没有空闲表项则显示打开文件过多并返回；
                        if(!openCatalogue.openFile(file))
                        {
                            System.out.println("打开文件过多");
                            return null;
                        }
                        //④将文件目录复制到系统打开文件表项中并初始化文件读写指针，
                        // 返回打开文件在系统打开文件表的表项下标（后面称为文件描述符）供读写操作使用，
                        // 系统打开文件表设计为结构数组（也可以设计为其他形式，此时返回的是能找到打开文件属性信息数据结构的指针，也称为文件句柄）。
                        // 此处返回的文件句柄设计为文件目录项该类本身
                        else
                        {
                            file.initRW();
                            System.out.println("成功打开文件");
                            return file;
                        }
                    }
                }
                //②系统查找相应用户的用户文件目录，若没找到指定文件则显示文件不存在并返回；
                System.out.println("文件不存在");
                return null;
            }
        }
        //①判断用户是否存在，若不存在显示错误信息并返回；
        System.out.println("不存在该用户");
        return null;
    }

    public void close(File file)
    {
        for(File openFile:openCatalogue.fileList)
        {
            //②将文件描述符所指系统打开文件表项释放，使其成为空闲表项。
            if(openFile.equals(file))
            {
                openCatalogue.closeFile(file);
                System.out.println("成功关闭文件");
                return;
            }
        }
        //①判断用户文件描述符是否存在，若不存在显示错误信息并返回；
        System.out.println("该文件不存在或该文件并未打开");
        return;
    }

    public File create(String userName,String fileName,Character protectChar)
    {
        for(User user:userList)
        {
            if(user.getUserName().equals(userName))
            {
                UserCatalogue userCatalogue = mainCatalogue
                        .userCatalogueMap.get(userName);
                boolean flag = false;
                if(userCatalogue.num >= 10)
                {
                    flag = true;
                }
                for(File file: userCatalogue.fileList)
                {
                    if(file.getFileName().equals(fileName))
                    {
                        //③在用户文件目录表中查找指定文件，若找到指定文件则将该文件长度清零，申请一个空闲的系统打开文件表项，
                        // 将文件目录复制到系统打开文件表项中并初始化文件读写指针，返回该文件的文件描述符。
                        file.setFileSize(0);
                        if(!openCatalogue.openFile(file))
                        {
                            System.out.println("打开文件过多");
                            return null;
                        }
                        else
                        {
                            file.initRW();
                            System.out.println("成功创建文件");
                            return file;
                        }
                    }
                }
                if(flag)
                {
                    //②系统查找相应用户的用户文件目录，检查用户已有文件数是否小于10，
                    // 若有10个文件且新建文件名不存在于用户文件目录中则显示创建文件已达最大数目并返回；
                    System.out.println("创建文件已达最大数目");
                    return null;
                }
                //④若没有找到指定文件，则申请一个空闲文件目录项，填写文件名及保护字段信息，初始化其他字段；
                // 再申请一个空闲的系统打开文件表项，将文件目录复制到系统打开文件表项中并初始化文件读写指针，返回文件描述符。
                File newFile = new File(fileName,protectChar,0,DiskBlock.getFree(),false);
                userCatalogue.addFile(newFile);
                if(!openCatalogue.openFile(newFile))
                {
                    System.out.println("打开文件过多");
                    return null;
                }
                else
                {
                    newFile.initRW();
                    System.out.println("成功创建文件");
                    return newFile;
                }
            }
        }
        //①判断用户是否存在，若不存在显示错误信息并返回；
        System.out.println("不存在该用户");
        return null;
    }

    public void delete(String userName,String fileName)
    {
        for(User user:userList)
        {
            if(userName.equals(user.getUserName()))
            {
                UserCatalogue userCatalogue = mainCatalogue.userCatalogueMap.get(userName);
                for(File file: userCatalogue.fileList)
                {
                    if(fileName.equals(file.getFileName()))
                    {
                        //③在用户文件目录表中查找指定文件，释放文件占用的空间，释放目录项。
                        //此处设计若删除文件为已开启文件会先关闭
                        for(File openFile:openCatalogue.fileList)
                        {
                            if(openFile.equals(file))
                            {
                                openCatalogue.closeFile(file);
                                break;
                            }
                        }
                        userCatalogue.removeFile(file);
                        System.out.println("成功删除文件");
                        return;
                    }
                }
                //②系统查找相应用户的用户文件目录，若没找到指定文件则显示文件不存在并返回；
                System.out.println("文件不存在");
                return;
            }
        }
        //① 判断用户是否存在，若不存在显示错误信息并返回；
        System.out.println("不存在该用户");
        return;
    }

    public void read(File file,List<Character> charBuffer,int readNum)
    {
        for(File openFile:openCatalogue.fileList)
        {
            //②根据文件描述符找到相应的系统打开文件表项，判断该文件是否允许读操作，
            // 若不允许则显示错误信息并返回；
            if(openFile.equals(file))
            {
                if(file.getProtectChar().equals('R')||file.getProtectChar().equals('B'))
                {
                    FileBlock read = file.getRead();
                    while(readNum>0)
                    {
                        //③按照本次读字符数从当前文件指针开始读数据到缓冲区中。
                        char[] chars = file.getRead().getDiskBlock().getChars();
                        for(int i=0;i<64;i++)
                        {
                            charBuffer.add(chars[i]);
                            readNum--;
                            if(readNum <= 0)
                            {
                                System.out.println("读取完毕");
                                return;
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("无读权限");
                    return;
                }
            }
        }
        //①判断用户文件描述符是否存在，若不存在显示错误信息并返回；
        System.out.println("该文件不存在或该文件并未打开");
        return;
    }

    public void write(File file,List<Character> charBuffer,int writeNum)
    {
        for(File openFile:openCatalogue.fileList)
        {
            //②根据文件描述符找到相应的系统打开文件表项，判断该文件是否允许读操作，
            // 若不允许则显示错误信息并返回；
            if(openFile.equals(file))
            {
                if(file.getProtectChar().equals('W')||file.getProtectChar().equals('B'))
                {
                    FileBlock write = file.getWrite();
                    while(writeNum>0)
                    {
                        //③按照本次读字符数从当前文件指针开始读数据到缓冲区中。
                        char[] chars = file.getWrite().getDiskBlock().getChars();
                        for(int i=0;i<64;i++)
                        {
                            chars[i] = charBuffer.remove(0);
                            writeNum--;
                            if(writeNum <= 0)
                            {
                                System.out.println("写入完毕");
                                return;
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("无写权限");
                    return;
                }
            }
        }
        //①判断用户文件描述符是否存在，若不存在显示错误信息并返回；
        System.out.println("该文件不存在或该文件并未打开");
        return;
    }
}
