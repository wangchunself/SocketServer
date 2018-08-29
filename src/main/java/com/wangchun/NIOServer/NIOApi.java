package com.wangchun.NIOServer;

import java.nio.IntBuffer;

/**
 * Created by Administrator on 2018/8/29.
 */
public class NIOApi {
    public static void main(String[] args) {
        /*//创建指定长度的缓冲区
        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(11);
        intBuffer.put(5);
        intBuffer.put(32);
        System.out.println("未调用flip()复位方法前的buffer:" + intBuffer);
        intBuffer.flip();

        *//* 未调用flip()方法之前position是3,limit是10,capacity是10
        *
        * 调用之后position是0,limit是3,capacity是10
        *//*
        System.out.println("调用了flip()复位方法之后的buffer:" + intBuffer);
        System.out.println("buffer容量为：" + intBuffer.capacity());
        System.out.println("buffer限制为：" + intBuffer.limit());

        //调用get(index)方法，不会改变position的值
        System.out.println("获取下标为1的元素：" + intBuffer.get(1));
        System.out.println("调用get(index)方法后的buffer：" + intBuffer);
        //将buffer位置为1的值替换为4，调用put(index，value)不会改变position的值
        intBuffer.put(1, 4);
        System.out.println("调用put(index, value)方法后的buffer：" + intBuffer);

        for(int i=0; i<intBuffer.limit(); i++) {
            //调用get方法会使缓冲区的位置(position)向后递增一位
            System.out.print(intBuffer.get() + "\t");
        }
        System.out.println("\nbuffer对象遍历之后buffer为：" + intBuffer);*/



      /*  int[] arr = new int[]{1, 2, 3};
        IntBuffer buffer = IntBuffer.wrap(arr);
        System.out.println("wrap(arr)方法：" + buffer);
        //IntBuffer.wrap(array, postion, length)表示容量为array的长度，但是可操作的元素为位置postion到length的数组元素
        buffer = IntBuffer.wrap(arr, 0, 2);
        System.out.println("wrap(arr, 0, 2)：" + buffer);*/

        IntBuffer buffer = IntBuffer.allocate(10);
        int[] arr = new int[]{1, 2, 3};
        buffer.put(arr);
        System.out.println("调用put(arr)方法后的buffer：" + buffer);
        //一种复制方法，buffer1的pos、lim、cap与buffer的一样
        IntBuffer buffer1 = buffer.duplicate();
        System.out.println("buffer1：" + buffer1);


        //将buffer的position设置为1，不建议使用。功能相当于flip()方法，但是从运行结果可以看出，lim依然等于10
        buffer.position(1);
        System.out.println("调用position()方法后的buffer：" + buffer);
        System.out.println("buffer的可读数据量：" + buffer.remaining()); //计算出从pos到lim的长度
        int[] arr1 = new int[buffer.remaining()];
        //将缓冲区的数据放入arr1中
        buffer.get(arr1);
        for(Integer i : arr1) {
            System.out.print(Integer.toString(i) + ",");
        }
        System.out.println();


        //比较flip()方法和position(index)方法的区别
        buffer1.flip();
        System.out.println("buffer1的可读数量：" + buffer1.remaining());
        arr1 = new int[buffer1.remaining()];
        buffer1.get(arr1);
        for(Integer i : arr1) {
            System.out.print(Integer.toString(i) + ",");
        }


    }
}
