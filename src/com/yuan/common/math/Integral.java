/*
 * ������ֵ��ֵ��� Integral
 * 
 * �ܳ�������
 */
package com.yuan.common.math;

/**
 * ������ֵ��ֵ��� Integral
 *
 * @author �ܳ���
 * @version 1.0
 */
public abstract class Integral 
{
	/**
	 * ����������ֺ���ֵ���������������и��Ǹú���
	 * 
	 * @param x - �������
	 * @return double�ͣ���Ӧ�ĺ���ֵ
	 */
	public abstract double func(double x);

	/**
	 * ���캯��
	 */
	public Integral()
	{
	}

	/**
	 * �䲽���������
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValueTrapezia(double a, double b, double eps)
	{
		int n,k;
	    double fa,fb,h,t1,p,s,x,t=0;
	    
		// ������˵�ĺ���ֵ
		fa=func(a); 
		fb=func(b);
	    
		// ����ֵ
		n=1; 
		h=b-a;
	    t1=h*(fa+fb)/2.0;
	    p=eps+1.0;

		// ������
	    while (p>=eps)
	    { 
			s=0.0;
	        for (k=0;k<=n-1;k++)
	        { 
				x=a+(k+0.5)*h;
	            s=s+func(x);
	        }
	        
			t=(t1+h*s)/2.0;
	        p=Math.abs(t1-t);
	        t1=t; 
			n=n+n; 
			h=h/2.0;
	    }
	    
		return(t);
	}

	/**
	 * �䲽�����������
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValueSimpson(double a, double b, double eps)
	{ 
	    int n,k;
	    double h,t1,t2,s1,s2=0,ep,p,x;

		// �����ֵ
	    n=1; 
		h=b-a;
	    t1=h*(func(a)+func(b))/2.0;
	    s1=t1;
	    ep=eps+1.0;
	    
		// ������
		while (ep>=eps)
	    { 
			p=0.0;
	        for (k=0;k<=n-1;k++)
	        { 
				x=a+(k+0.5)*h;
	            p=p+func(x);
	        }
	        
			t2=(t1+h*p)/2.0;
	        s2=(4.0*t2-t1)/3.0;
	        ep=Math.abs(s2-s1);
	        t1=t2; s1=s2; n=n+n; h=h/2.0;
	    }
	    
		return(s2);
	}

	/**
	 * ����Ӧ�������
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param d - �Ի�������зָ����С�������������Ŀ��
	 *            С��dʱ����ʹû�����㾫��Ҫ��Ҳ�������½��зָ�
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValueATrapezia(double a, double b, double d, double eps)
	{ 
	    double h,f0,f1,t0,z;
	    double[] t = new double[2];

		// ����ֵ
	    h=b-a; 
		t[0]=0.0;
	    f0=func(a); 
		f1=func(b);
	    t0=h*(f0+f1)/2.0;

		// �ݹ����
	    ppp(a,b,h,f0,f1,t0,eps,d,t);

	    z=t[0]; 
		
		return(z);
	}

	/**
	 * �ڲ�����
	 */
	private void ppp(double x0, double x1, double h, double f0, double f1, double t0, double eps, double d, double[] t)
	{ 
	    double x,f,t1,t2,p,g,eps1;

	    x=x0+h/2.0; 
		f=func(x);
	    t1=h*(f0+f)/4.0; 
		t2=h*(f+f1)/4.0;
	    p=Math.abs(t0-(t1+t2));
	    
		if ((p<eps)||(h/2.0<d))
	    { 
			t[0]=t[0]+(t1+t2); 
			return;
		}
	    else
	    { 
			g=h/2.0; 
			eps1=eps/1.4;
			// �ݹ�
	        ppp(x0,x,g,f0,f,t1,eps1,d,t);
	        ppp(x,x1,g,f,f1,t2,eps1,d,t);
	        return;
	    }
	}

	/**
	 * ������
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValueRomberg(double a, double b, double eps)
	{ 
	    int m,n,i,k;
	    double h,ep,p,x,s,q=0;
	    double[] y = new double[10];

		// ����ֵ
	    h=b-a;
	    y[0]=h*(func(a)+func(b))/2.0;
	    m=1; 
		n=1; 
		ep=eps+1.0;
	    
		// ������
		while ((ep>=eps)&&(m<=9))
	    { 
			p=0.0;
	        for (i=0;i<=n-1;i++)
	        { 
				x=a+(i+0.5)*h;
	            p=p+func(x);
	        }
	        
			p=(y[0]+h*p)/2.0;
	        s=1.0;
	        for (k=1;k<=m;k++)
	        { 
				s=4.0*s;
	            q=(s*p-y[k-1])/(s-1.0);
	            y[k-1]=p; p=q;
	        }

	        ep=Math.abs(q-y[m-1]);
	        m=m+1; 
			y[m-1]=q; 
			n=n+n; 
			h=h/2.0;
	    }
	    
		return(q);
	}

	/**
	 * ����һά��ֵ�����ʽ��
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValuePq(double a, double b, double eps)
	{ 
	    int m,n,k,l,j;
	    double hh,t1,s1,ep,s,x,t2,g=0;
	    double[] h = new double[10];
	    double[] bb = new double[10];

		// ����ֵ
	    m=1; 
		n=1;
	    hh=b-a; 
		h[0]=hh;
	    t1=hh*(func(a)+func(b))/2.0;
	    s1=t1; 
		bb[0]=s1; 
		ep=1.0+eps;
	    
		// ������
		while ((ep>=eps)&&(m<=9))
	    { 
			s=0.0;
	        for (k=0;k<=n-1;k++)
	        { 
				x=a+(k+0.5)*hh;
	            s=s+func(x);
	        }
	        
			t2=(t1+hh*s)/2.0;
	        m=m+1;
	        h[m-1]=h[m-2]/2.0;
	        g=t2;
	        l=0; 
			j=2;
	        
			while ((l==0)&&(j<=m))
	        { 
				s=g-bb[j-2];
	            if (Math.abs(s)+1.0==1.0) 
					l=1;
	            else 
					g=(h[m-1]-h[j-2])/s;
	            
				j=j+1;
	        }
	        
			bb[m-1]=g;
	        if (l!=0) 
				bb[m-1]=1.0e+35;
	        
			g=bb[m-1];
	        for (j=m;j>=2;j--)
	           g=bb[j-2]-h[j-2]/g;
	        
			ep=Math.abs(g-s1);
	        s1=g; 
			t1=t2; 
			hh=hh/2.0; 
			n=n+n;
	    }
	    
		return(g);
	}

	/**
	 * ���񵴺������
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param m - ���������񵴺���Ľ�Ƶ��
	 * @param n - ����������˵��ϵĵ�����߽���1
	 * @param fa - һά���飬����Ϊn�����f(x)�ڻ�����˵�x=a���ĸ��׵���ֵ
	 * @param fb - һά���飬����Ϊn�����f(x)�ڻ�����˵�x=b���ĸ��׵���ֵ
	 * @param s - һά���飬����Ϊ2������s(1)����f(x)cos(mx)�ڻ�����Ļ��ֵ��
	 *            s(2) ����f(x)sin(mx)�ڻ�����Ļ��ֵ
	 * @return double �ͣ����ֵ
	 */
	public double getValuePart(double a, double b, int m, int n, double[] fa, double[] fb, double[] s)
	{ 
		int mm,k,j;
	    double sma,smb,cma,cmb;
	    double[] sa = new double[4];
	    double[] sb = new double[4];
	    double[] ca = new double[4];
	    double[] cb = new double[4];
	    
		// ��Ǻ���ֵ
		sma=Math.sin(m*a); 
		smb=Math.sin(m*b);
	    cma=Math.cos(m*a); 
		cmb=Math.cos(m*b);
	    
		// ����ֵ
		sa[0]=sma; 
		sa[1]=cma; 
		sa[2]=-sma; 
		sa[3]=-cma;
	    sb[0]=smb; 
		sb[1]=cmb; 
		sb[2]=-smb; 
		sb[3]=-cmb;
	    ca[0]=cma; 
		ca[1]=-sma; 
		ca[2]=-cma; 
		ca[3]=sma;
	    cb[0]=cmb; 
		cb[1]=-smb; 
		cb[2]=-cmb; 
		cb[3]=smb;
	    s[0]=0.0; 
		s[1]=0.0;
	    
		mm=1;
	    
		// ѭ�����
		for (k=0;k<=n-1;k++)
	    { 
			j=k;
	        while (j>=4) 
				j=j-4;
	        
			mm=mm*m;
	        s[0]=s[0]+(fb[k]*sb[j]-fa[k]*sa[j])/(1.0*mm);
	        s[1]=s[1]+(fb[k]*cb[j]-fa[k]*ca[j])/(1.0*mm);
	    }
	    
		s[1]=-s[1];

		return s[0];
	}

	/**
	 * ���õ£���˹���
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @param a - �������
	 * @param b - ������ޣ�Ҫ��b>a
	 * @param eps - ��־���Ҫ��
	 * @return double �ͣ����ֵ
	 */
	public double getValueLegdGauss(double a, double b, double eps)
	{ 
	    int m,i,j;
	    double s,p,ep,h,aa,bb,w,x,g=0;

		// ���õ£���˹���ϵ��
	    double[] t={-0.9061798459,-0.5384693101,0.0,
	                         0.5384693101,0.9061798459};
	    double[] c={0.2369268851,0.4786286705,0.5688888889,
	                        0.4786286705,0.2369268851};

		// ����ֵ
	    m=1;
	    h=b-a; 
		s=Math.abs(0.001*h);
	    p=1.0e+35; 
		ep=eps+1.0;
	    
		// ������
		while ((ep>=eps)&&(Math.abs(h)>s))
	    { 
			g=0.0;
	        for (i=1;i<=m;i++)
	        { 
				aa=a+(i-1.0)*h; 
				bb=a+i*h;
	            w=0.0;

	            for (j=0;j<=4;j++)
	            { 
					x=((bb-aa)*t[j]+(bb+aa))/2.0;
	                w=w+func(x)*c[j];
	            }
	            
				g=g+w;
	        }
	        
			g=g*h/2.0;
	        ep=Math.abs(g-p)/(1.0+Math.abs(g));
	        p=g; 
			m=m+1; 
			h=(b-a)/m;
	    }
	    
		return(g);
	}

	/**
	 * ���Ƕ��˹���
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @return double �ͣ����ֵ
	 */
	public double getValueLgreGauss()
	{ 
		int i;
	    double x,g;

		// ���Ƕ��˹���ϵ��
	    double[] t={0.26355990, 1.41340290, 3.59642600, 7.08580990, 12.64080000};
	    double[] c={0.6790941054, 1.638487956, 2.769426772, 4.315944000, 7.104896230};

		// ѭ������
	    g=0.0;
	    for (i=0; i<=4; i++)
	    { 
			x=t[i]; 
			g=g+c[i]*func(x); 
		}
	    
		return(g);
	}

	/**
	 * �������أ���˹���
	 * 
	 * ����ʱ���븲�Ǽ��㺯��f(x)ֵ���麯��double func(double x)
	 * 
	 * @return double �ͣ����ֵ
	 */
	public double getValueHermiteGauss()
	{ 
		int i;
	    double x,g;
		
		// �������أ���˹���ϵ��
	    double[] t={-2.02018200, -0.95857190, 0.0,0.95857190, 2.02018200};
	    double[] c={1.181469599, 0.9865791417, 0.9453089237, 0.9865791417, 1.181469599};

		// ѭ������
	    g=0.0;
	    for (i=0; i<=4; i++)
	    { 
			x=t[i]; 
			g=g+c[i]*func(x); 
		}
	    
		return(g);
	}
}
