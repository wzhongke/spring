package com.wang.action.memento;

import com.wang.struct.adapter.Point;
import com.wang.struct.proxy.Graphic;

/**
 * execute 在移动图形前先获取一个 ConstraintSolverMemento 备忘录，Unexecute 先将图形移回，再将约束解释器设回原来的状态，并让约束解释器解释这些约束。
 */
public class MoveCommand {

	private ConstraintSolverMemento state;
	private Point delta;
	private Graphic target;
	public MoveCommand(Graphic t, Point delta) {
		this.delta = delta;
		this.target = t;
	}

	void execute () {
		ConstraintSolver solver = ConstraintSolver.getInstance();
		// 存储当前的状态
		state = solver.createMemento();
		target.move(delta);
		solver.solve();
	}
	void unexecute() {
		ConstraintSolver solver = ConstraintSolver.getInstance();
		target.move(delta);
		solver.setMemento(state);
		// 重新建立约束
		solver.solve();
	}
}
