package com.xtons.samples.iText7;

public class MatrixUtil {
	public static float[] getScale(float w, float h) {
		return new float[] { w, 0, 0, 0, h, 0 };
	}

	public static float[] getShift(float x, float y) {
		return new float[] { 1, 0, x, 0, 1, y };
	}

	public static float[] getRotate(double angle) {
		return new float[] { (float) Math.cos(angle), (float) -Math.sin(angle), 0, (float) Math.sin(angle), (float) Math.cos(angle), 0 };
	}
	
	public static float[] getMirror_H() {
		return new float[] {-1,0,0,0,1,0};
	}
	
	public static float[] getMirror_V() {
		return new float[] {1,0,0,0,-1,0};
	}
	
	public static float[] multiply(float[] m1,float[] m2) {
		return new float[] {m1[0]*m2[0]+m1[1]*m2[3],m1[0]*m2[1]+m1[1]*m2[4],m1[0]*m2[2]+m1[1]*m2[5]+m1[2],
				m1[3]*m2[0]+m1[4]*m2[3],m1[3]*m2[1]+m1[4]*m2[4]+m1[3]*m2[2],m1[4]*m2[5]+m1[5]};
	}
}
