package com.wang.action.strategy;

/**
 * Compositor 接口需要经过仔细设计，以支持子类可能实现的各种算法，不希望在生成一个新的子类就不得不修改这个接口
 */
public interface Compositor {
	int Compose();
}
