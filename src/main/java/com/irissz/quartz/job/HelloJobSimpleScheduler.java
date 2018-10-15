package com.irissz.quartz.job;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 具体任务类
 * 
 * @author Administrator
 *
 */
@PersistJobDataAfterExecution // 多次调用Job时，会对Job进行持久化，保存一个数据的信息
public class HelloJobSimpleScheduler implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	}

	public static void main(String[] args) throws SchedulerException {
		// 1.调度器（Scheduler），从工厂中获取调度实例
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		// 2.任务实例（JobDetail）
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)// 加载任务类，与HelloJob(实现Job接口)完成绑定.
				.withIdentity("job1", "group1")// 参数1 任务的名称（唯一实例）；参数2 任务组的名称
				.usingJobData("day", 0).build();
		// 3.触发器(Trigger)
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")// 参数1触发器名称；参数2 触发器组的名称。
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.repeatSecondlyForever(5)
						.withRepeatCount(2))// 每5秒重复执行一次  重复3次
				.startAt(DateBuilder.futureDate(5, IntervalUnit.MINUTE))
				.build();
		
		Trigger trigger2 = TriggerBuilder.newTrigger()
				.withIdentity("trigger2", "group2")// 参数1触发器名称；参数2 触发器组的名称。
				.startAt(DateBuilder.futureDate(5, IntervalUnit.MINUTE))//这里使用DateBuilder来创建一个未来时间
				.build();
		
		//立即触发，每个5分钟执行一次，直到22:00：
		Trigger trigger3 = TriggerBuilder.newTrigger()
				.withIdentity("trigger3", "group3")// 参数1触发器名称；参数2 触发器组的名称。
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
				.endAt(DateBuilder.dateOf(22, 0, 0))
				.build();
		// 4.让调度器关联任务和触发器，这样就能按照触发器定义的条件执行任务
		scheduler.scheduleJob(jobDetail, trigger);
		// 5启动
		scheduler.start();
	}
}
