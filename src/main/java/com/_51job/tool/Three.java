package com._51job.tool;

public class Three<A,B,C> {
	private A a;
	private B b;
	private C c;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public B getB() {
		return b;
	}

	@Override
	public String toString() {
		return "Three{" +
				"a=" + a +
				", b=" + b +
				", c=" + c +
				'}';
	}

	public void setB(B b) {
		this.b = b;
	}

	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

	public Three() {
	}

	public Three(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;

	}
}
