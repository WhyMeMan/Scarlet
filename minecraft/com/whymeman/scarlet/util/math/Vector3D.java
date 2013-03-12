package com.whymeman.scarlet.util.math;

public class Vector3D 
{
	private double x,y,z;
	public Vector3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public boolean compare(Vector3D v)
	{
		return v.getX() == x && v.getY() == y && v.getZ() == z;
	}
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
}
