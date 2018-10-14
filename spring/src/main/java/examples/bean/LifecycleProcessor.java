package examples.bean;

import org.springframework.context.Lifecycle;

/**
 * Lifecycle 接口定义了一个有他自己生命周期对象的主要方法，
 * 当 ApplicationContext 收到 开始 和 结束 信号时执行。
 */
public class LifecycleProcessor implements Lifecycle {

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public boolean isRunning() {
		return false;
	}
}
