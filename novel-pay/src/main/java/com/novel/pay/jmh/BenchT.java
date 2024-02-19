package com.novel.pay.jmh;


import com.novel.pay.PayApplication;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sun.plugin2.main.server.JVMHealthData;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)//统计平均响应时间
@State(Scope.Benchmark) //多线程共享一个示例，这里因为Bean是单例 | Scope.Thread每个进行基准测试的线程都会独享一个对象示例 | 线程组共享一个示例，在测试方法上使用 @Group 设置线程组
@Fork(1) //表示开启一个线程进行测试。
@OutputTimeUnit(TimeUnit.MILLISECONDS) //ms为输出时间单位
/*@Warmup(iterations = 3) //微基准测试前进行三次预热执行，也可用在测试方法上
@Measurement(iterations = 1) //进行 1 次微基准测试，也可用在测试方法上*/
public class BenchT {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchT.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

    //@Benchmark
    public String strBuilder(){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<1000;i++) stringBuffer.append("Hi");
        return stringBuffer.toString();
    }

    //@Benchmark
    public String str(){
        String str = "";
        for(int i=0;i<1000;i++) str += "Hi";
        return str;
    }

    @Benchmark
    public void doubleNum(){
        double[][] doubles = new double[3][];
        doubles[0] = new double[]{0,1,2,3,4};
        doubles[1] = new double[]{1,2};
        doubles[2]=new double[]{3,4,5};
        for(int i=0;i<3;i++){
            for(int j=0;j<doubles[i].length;j++)
                System.out.print(doubles[i][j]+" ");
            System.out.println();
        }
    }
   /* @Setup
    public void init(){
        ConfigurableApplicationContext context = SpringApplication.run(PayApplication.class);
    }*/
}
