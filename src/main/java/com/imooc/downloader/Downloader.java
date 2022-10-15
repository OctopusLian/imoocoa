package com.imooc.downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    private Integer threadNum = 10;
    /**
     * 下载单个文件保存到本地
     * @param source 原图片的网址
     * @param targerDit 目标目录
     */
    public void download(String source,String targerDit) {
        InputStream is = null;
        OutputStream os = null;
        try {
            String fileName = source.substring(source.lastIndexOf("/")+1);
            File targetFile = new File(targerDit + "/" + fileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            URL url = new URL(source);
            URLConnection connection = url.openConnection();
            is = connection.getInputStream();
            os = new FileOutputStream(targetFile);
            byte[] bs = new byte[1024];
            int len = 0;
            while ((len = is.read(bs)) != -1) {
                os.write(bs,0,len);
            }
            System.out.println("[INFO]图片下载完毕:" + source + "\n\t  ->" + targetFile.getPath() + "(" + Math.floor(targetFile.length() / 1024) + "kb)");
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从指定文件读取下载地址,批量下载网络资源
     * @param targetDir 下载文件的存储目录
     * @param downloadTxt download.txt完整路径
     */
    public void multiDownloadFromFile(String targetDir ,String downloadTxt){
        File dir = new File(targetDir);
        //新建目标目录
        if(!dir.exists()){
            dir.mkdirs();
            System.out.println("[INFO]发现下载目录[" + dir.getPath() + "]不存在,已自动创建");
        }
        //读取download.txt存入resources集合
        List<String> resources = new ArrayList<>();
        BufferedReader reader = null;
        ExecutorService threadPool = null;
        try {
            reader = new BufferedReader(new FileReader(downloadTxt));
            String line = null;
            //读取每一行网址，存入resources集合
            while((line = reader.readLine()) != null){
                resources.add(line);
                //System.out.println(line);
            }
            //利用线程池,同时开启{threadNum}个线程批量下载图片并保存到本地
            threadPool = Executors.newFixedThreadPool(this.threadNum);
            Downloader that = this;
            for(String res:resources){
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        that.download(res,targetDir);
                    }
                });
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //执行完毕,关闭线程池
            if(threadPool != null){
                threadPool.shutdown();
            }
            if(reader != null){
                try {
                    //关闭字节输入流
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 开始多线程下载
     * @param propDir config.properties所在目录
     */
    public void start(String propDir) {
        //指定config.properties完整路径
        File propFile = new File(propDir + "/config.properties");
        //实例化Properties属性类
        Properties properties = new Properties();
        Reader reader = null;
        try {
            //实例化FileReader对象
            reader = new FileReader(propFile);
            //properties对象读取配置文件
            properties.load(reader);
            //通过getProperty方法得到对应的选项值
            String threadNum = properties.getProperty("thread-num");
            this.threadNum = Integer.parseInt(threadNum);
            String targetDir = properties.getProperty("target-dir");
            //打印输出
            //System.out.println(threadNum);
            //System.out.println(targetDir);
            //读取download.txt进行多线程下载
            this.multiDownloadFromFile(targetDir,propDir+"/download.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    //关闭流
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Downloader downloader = new Downloader();
        File propFile = new File("/home/neo/IDEAProjects/imoocoa/src/main/java/com/imooc/downloader/config.properties");
        downloader.start("/home/neo/IDEAProjects/imoocoa/src/main/java/com/imooc/downloader");
    }
}
