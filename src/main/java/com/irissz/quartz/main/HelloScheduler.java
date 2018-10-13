package com.irissz.quartz.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.irissz.quartz.job.HelloJob;

/**
 * @author Administrator
 *
 */
public class HelloScheduler {

	/**
	 * 执行任务main方法
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 1.调度器（Scheduler），从工厂中获取调度实例
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		// 2.任务实例（JobDetail）
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)// 加载任务类，与HelloJob(实现Job接口)完成绑定.
				.withIdentity("job1", "group1")// 参数1 任务的名称（唯一实例）；参数2 任务组的名称
				.usingJobData("day", 0)
				.build();
		// 3.触发器(Trigger)
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")// 参数1触发器名称；参数2 触发器组的名称。
				.startNow()// 马上启动触发器
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))// 每5秒重复执行一次
				.build();
		// 4.让调度器关联任务和触发器，这样就能按照触发器定义的条件执行任务
		scheduler.scheduleJob(jobDetail, trigger);
		// 5启动
		scheduler.start();
	}
}
