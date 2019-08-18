package com.wang.action.memento;

import com.wang.struct.proxy.Graphic;

/**
 * ConstraintSolver 用操作将自身状态存储在外部的一个ConstraintSolverMemento实例中。
 * @author wangzhongke
 */
public class ConstraintSolver {

	private ConstraintSolver(){}
	public void solve() {}
	public void addConstraint (Graphic s, Graphic e) {}
	public void removeConstraint(Graphic s, Graphic e) {}
	public ConstraintSolverMemento createMemento() {
		return new ConstraintSolverMemento();
	}
	public void setMemento(ConstraintSolverMemento memento) {}

	public static ConstraintSolver getInstance() {
		return new ConstraintSolver();
	}
}
