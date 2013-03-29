package SoundLogic.SoulCraft;

import SoundLogic.codechicken.*;

public class LocationField {
	//This is my very fancy location field class. It is an elipsoid.
	//Which is unnatural in the world of minecraft. Same as ghosts.
	public float[] a=new float[3];
	public float[] b=new float[3];
	public float r;
	public int dim;
	public LocationField(float[] a, float[] b, float r, int dim)
	{
		this.a=a;
		this.b=b;
		this.r=r;
		this.dim=dim;
	}
	public float getLength()
	{
		return r*2-magDiffVector();
	}
	public float getDiamiter()
	{
		return (float) Math.sqrt(r*r-magDiffVector()*magDiffVector());
	}
	public float[] getMidpoint()
	{
		return new float[]{(a[0]+b[0])/2,
				   (a[1]+b[1])/2,
				   (a[2]+b[2])/2};
	}
	public float[] getPointOnSurface(double n,double m)
	{
		float theta=(float) (n*2*Math.PI);
		float phi=(float) (m*2*Math.PI);
		float a=r/2;
		float e=magDiffVector()/r;
		float len=(float) -Math.abs(a*(1-e*e)/(1+e*Math.cos(theta)));
		float[] v=normDiffVector();
		Vector3 vec=new Vector3(v[0]*len,v[1]*len,v[2]*len);
		Vector3 backup=vec.copy();
		vec.rotate(-theta,vec.copy().perpendicular());
		vec.rotate(phi, backup);
		float[] point=new float[]{(float) (vec.x+this.a[0]),(float) (vec.y+this.a[1]),(float) (vec.z+this.a[2])};
		return point;
	}
	public float[] getPoint(double n,double m,double j)
	{
		float theta=(float) (n*2*Math.PI);
		float phi=(float) (m*2*Math.PI);
		float a=r/2;
		float e=magDiffVector()/r;
		float len=(float) -Math.abs(a*(1-e*e)/(1+e*Math.cos(theta)));
		float[] v=normDiffVector();
		Vector3 vec=new Vector3(v[0]*len*j,v[1]*len*j,v[2]*len*j);
		Vector3 backup=vec.copy();
		vec.rotate(-theta,vec.copy().perpendicular());
		vec.rotate(phi, backup);
		float[] point=new float[]{(float) (vec.x+this.a[0]),(float) (vec.y+this.a[1]),(float) (vec.z+this.a[2])};
		return point;
	}
	public boolean inRange(float x,float y,float z)
	{
		return rangeRatio(x,y,z)<=1;
	}
	public float rangeRatio(float x,float y,float z)
	{
		return (float) Math.sqrt(sqrRangeRatio(x,y,z));
	}
	public float sqrRangeRatio(float x,float y,float z)
	{
		return ((a[0]-x)*(a[0]-x)+
				(a[1]-y)*(a[1]-y)+
				(a[2]-z)*(a[2]-z)+
				(b[0]-x)*(b[0]-x)+
				(b[1]-y)*(b[1]-y)+
				(b[2]-z)*(b[2]-z))/(r*r);
	}
	public float[] diffVector()
	{
		return new float[]{b[0]-a[0],
						   b[1]-a[1],
						   b[2]-a[2]};
	}
	public float sqrDiffVector()
	{
		float[] diff=diffVector();
		return diff[0]*diff[0]+diff[1]*diff[1]+diff[2]*diff[2];
	}
	public float magDiffVector()
	{
		return (float) Math.sqrt(sqrDiffVector());
	}
	public float[] normDiffVector()
	{
		float[] diff=diffVector();
		float mag=magDiffVector();
		if(mag==0)
			return new float[]{1,0,0};
		return new float[]{diff[0]/mag,
						   diff[1]/mag,
						   diff[2]/mag};
	}
}
