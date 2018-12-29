package com.irissz.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class MyTriggerListener implements TriggerListener{

	@Override
	public String getName() {
		String simpleName = this.getClass().getSimpleName();
		System.out.println("触发器监听器的名称为："+simpleName);
		return simpleName;
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		System.out.println("TriggerComplete方法：Trigger 被触发并且完成了 Job 的执行时，Scheduler 调用这个方法。");
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		System.out.println("triggerFired方法：当与监听器相关联的Trigger被触发，Job上的execute()方法将被执行时，Scheduler就调用该方法。");
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		System.out.println(" triggerMisfired方法：Scheduler 调用这个方法是在 Trigger 错过触发时。你应该关注此方法中持续时间长的逻辑：在出现许多错过触发的 Trigger 时，长逻辑会导致骨牌效应。你应当保持这上方法尽量的小。");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		System.out.println(" vetoJobExecution方法：在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法。TriggerListener 给了一个选择去否决 Job 的执行。假如这个方法返回 true，这个 Job 将不会为此次 Trigger 触发而得到执行。");
		return false;
	}

}
