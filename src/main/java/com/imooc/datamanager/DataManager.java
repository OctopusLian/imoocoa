package com.imooc.datamanager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 从键盘接收整型数据存放到数组中，并对数组中的数据进行管理
 */
public class DataManager {
    /**
     * 从键盘接收数据
     * @return
     */
    public int[] insertData() {
        int[] a = new int[10];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < a.length; i++) {
            System.out.println("请输入第" + (i + 1) + "个数据：");
            try {
                a[i] = sc.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("error");
                sc.next();
                i--;
            }
        }
        return a;
    }

    /**
     *
     * @param a
     * @param length
     */
    public void showData(int[] a,int length) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]+" ");
        }
        System.out.println();
    }

    /**
     *
     * @param a
     * @param n
     * @param k
     */
    public void insertAtArray(int[] a,int n,int k) {
        for(int i = a.length - 1; i > k; i--) {
            a[i] = a[i - 1];
        }
        a[k] = n;
    }

    public void divThree(int[] a) {
        String str = "";
        int count = 0;
        for (int n : a) {
            if (n % 3 == 0) {
                str = str + n + "   ";
                count++;
            }
        }
        if (count == 0) {
            System.out.println("数组中没有能被3整除的元素！");
        } else {
            System.out.println("数组中能被3整除的元素为：" + str);
        }
    }

    public void notice() {
        System.out.println("********************************************");
        System.out.println("                1--插入数据");
        System.out.println("                2--显示所有数据");
        System.out.println("                3--在指定位置处插入数据");
        System.out.println("                4--查询能被3整除的数据");
        System.out.println("                0--退出");
        System.out.println("********************************************");
    }

    public static void main(String[] args) {
        DataManager dm = new DataManager();
        Scanner sc = new Scanner(System.in);
        int input = 0;
        int[] a=null;
        int n=0,k=0;//n表示要插入的数据，k表示插入位置
        while (true) {
            dm.notice();
            System.out.println("请输入对应的数字进行操作：");
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("输入的数据格式有误，不能有非数字！");
                sc.next();
                continue;
            }
            if (input == 0) {
                System.out.println("退出程序！");
                break;
            }
            switch (input) {
                case 1:
                    //插入数据
                    a=dm.insertData();
                    //显示数据
                    System.out.println("数组元素为：");
                    dm.showData(a, a.length-1);
                    break;
                case 2:
                    if(a!=null){
                        System.out.println("数组元素为：");
                        if(a[a.length-1]==0){
                            //如果数组的最后一个元素为0，说明还没有拆入数据，因此不显示最后一个元素
                            dm.showData(a, a.length-1);
                        }else{
                            dm.showData(a, a.length);
                        }
                    }else{
                        System.out.println("还未在数组中插入数据，请重新选择操作！");
                    }
                    break;
                case 3:
                    //在指定位置处插入数据
                    if(a!=null){
                        System.out.println("请输入要插入的数据：");
                        try{
                            n=sc.nextInt();
                            System.out.println("请输入要插入数据的位置：");
                            k=sc.nextInt();
                        }catch(InputMismatchException e){
                            System.out.println("输入的数据格式有误，不能有非数字！");
                            sc.next();
                            break;
                        }
                        dm.insertAtArray(a, n, k);
                        dm.showData(a, a.length);
                    }else{
                        System.out.println("还未在数组中插入数据，请重新选择操作！");
                    }
                    break;
                case 4:
                    //查询数组中能被3整除的元素
                    if(a!=null){
                        dm.divThree(a);
                    }else{
                        System.out.println("还未在数组中插入数据，请重新选择操作！");
                    }
                    break;
            }

        }

    }
}
