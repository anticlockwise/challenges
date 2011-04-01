package org.jaden.challenges;

class FlowEdge {
	int next;

	private int capacity;

	int residual;

	int flow;

	public FlowEdge(int next, int capacity, int residual) {
		this.next = next;
		this.capacity = capacity;
		this.residual = residual;
	}
}