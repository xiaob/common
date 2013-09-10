/*
 * �����������Complex
 *
 * �ܳ�������
 */
package com.yuan.common.math;

/**
 * �����������Complex

 * @author �ܳ���
 * @version 1.0
 */
public class Complex 
{
	private double real = 0.0;			// �����ʵ��
	private double imaginary = 0.0;		// ������鲿
	private double eps = 0.0;           // ȱʡ����

	/**
	 * ���캯��
	 */
	public Complex() 
	{
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param dblX - ָ����ʵ��
	 * @param dblY - ָ�����鲿
	 */
	public Complex(double dblX, double dblY)
	{
		real = dblX;
		imaginary = dblY;
	}

	/**
	 * �������캯��
	 * 
	 * @param other - Դ����
	 */
	public Complex(Complex other)
	{
		real = other.real;
		imaginary = other.imaginary;
	}

	/**
	 * ���"a,b"��ʽ���ַ������츴����aΪ�����ʵ����bΪ������鲿
	 * 
	 * @param s - "a,b"��ʽ���ַ�aΪ�����ʵ����bΪ������鲿
	 * @param sDelim - a, b֮��ķָ���
	 */
	public Complex(String s, String sDelim)
	{
		setValue(s, sDelim);
	}

	/**
	 * ���ø�������ľ���
	 * 
	 * @param newEps - �µľ���ֵ
	 */
	public void setEps(double newEps)
	{
		eps = newEps;
	}
	
	/**
	 * ȡ����ľ���ֵ
	 * 
	 * @return double�ͣ�����ľ���ֵ
	 */
	public double getEps()
	{
		return eps;
	}

	/**
	 * ָ�������ʵ��
	 * 
	 * @param dblX - �����ʵ��
	 */
	public void setReal(double dblX)
	{
		real = dblX;
	}

	/**
	 * ָ��������鲿
	 * 
	 * @param dblY - ������鲿
	 */
	public void setImag(double dblY)
	{
		imaginary = dblY;
	}

	/**
	 * ȡ�����ʵ��
	 * 
	 * @return double �ͣ������ʵ��
	 */
	public double getReal()
	{
		return real;
	}

	/**
	 * ȡ������鲿
	 * 
	 * @return double �ͣ�������鲿
	 */
	public double getImag()
	{
		return imaginary;
	}

	/**
	 * ָ�������ʵ�����鲿ֵ
	 * 
	 * @param real - ָ����ʵ��
	 * @param imag - ָ�����鲿
	 */
	public void setValue(double real, double imag)
	{
		setReal(real);
		setImag(imag);
	}
	
	/**
	 * ��"a,b"��ʽ���ַ�ת��Ϊ������aΪ�����ʵ����bΪ������鲿
	 * 
	 * @param s - "a,b"��ʽ���ַ�aΪ�����ʵ����bΪ������鲿
	 * @param sDelim - a, b֮��ķָ���
	 */
	public void setValue(String s, String sDelim)
	{
		int nPos = s.indexOf(sDelim);
		if (nPos == -1)
		{
			s = s.trim();
			real = Double.parseDouble(s);
			imaginary = 0;
		}
		else
		{
			int nLen = s.length();
			String sLeft = s.substring(0, nPos);
			String sRight = s.substring(nPos+1, nLen);
			sLeft = sLeft.trim();
			sRight = sRight.trim();
			real = Double.parseDouble(sLeft);
			imaginary = Double.parseDouble(sRight);
		}
	}

	/**
	 * ������ת��Ϊ"a+bj"��ʽ���ַ�
	 * 
	 * @return String �ͣ�"a+bj"��ʽ���ַ�
	 */
	public String toString()
	{
		String s;
		if (real != 0.0)
		{
			if (imaginary > 0)
				s = new Float(real).toString() + "+" + new Float(imaginary).toString() + "j";
			else if (imaginary < 0)
				s = new Float(real).toString() + "-" + new Float(-1*imaginary).toString() + "j";
			else
				s = new Float(real).toString();
		}
		else
		{
			if (imaginary > 0)
				s = new Float(imaginary).toString() + "j";
			else if (imaginary < 0)
				s = new Float(-1*imaginary).toString() + "j";
			else
				s = new Float(real).toString();
		}

		return s;
	}

	/**
	 * �Ƚ����������Ƿ����
	 * 
	 * @param cpxX - ���ڱȽϵĸ���
	 * @return boolean�ͣ������Ϊtrue������Ϊfalse
	 */
	public boolean equal(Complex cpxX)
	{
		return Math.abs(real - cpxX.real) <= eps && 
			Math.abs(imaginary - cpxX.imaginary) <= eps; 
	}

	/**
	 * ����ֵ
	 * 
	 * @param cpxX - ���ڸ���ֵ��Դ����
	 * @return Complex�ͣ���cpxX��ȵĸ���
	 */
	public Complex setValue(Complex cpxX)
	{
		real = cpxX.real;
		imaginary = cpxX.imaginary;

		return this;
	}

	/**
	 * ʵ�ָ���ļӷ�
	 * 
	 * @param cpxX - ��ָ��������ӵĸ���
	 * @return Complex�ͣ�ָ��������cpxX���֮��
	 */
	public Complex add(Complex cpxX)
	{
		double x = real + cpxX.real;
		double y = imaginary + cpxX.imaginary;

		return new Complex(x, y);
	}

	/**
	 * ʵ�ָ���ļ���
	 * 
	 * @param cpxX - ��ָ����������ĸ���
	 * @return Complex�ͣ�ָ�������ȥcpxX֮��
	 */
	public Complex subtract(Complex cpxX)
	{
		double x = real - cpxX.real;
		double y = imaginary - cpxX.imaginary;

		return new Complex(x, y);
	}

	/**
	 * ʵ�ָ���ĳ˷�
	 * 
	 * @param cpxX - ��ָ��������˵ĸ���
	 * @return Complex�ͣ�ָ��������cpxX���֮��
	 */
	public Complex multiply(Complex cpxX)
	{
	    double x = real * cpxX.real - imaginary * cpxX.imaginary;
	    double y = real * cpxX.imaginary + imaginary * cpxX.real;

		return new Complex(x, y);
	}

	/**
	 * ʵ�ָ���ĳ�
	 * 
	 * @param cpxX - ��ָ���������ĸ���
	 * @return Complex�ͣ�ָ���������cpxX֮��
	 */
	public Complex divide(Complex cpxX)
	{
	    double e, f, x, y;
	    
	    if (Math.abs(cpxX.real) >= Math.abs(cpxX.imaginary))
		{
	        e = cpxX.imaginary / cpxX.real;
	        f = cpxX.real + e * cpxX.imaginary;
	        
	        x = (real + imaginary * e) / f;
	        y = (imaginary - real * e) / f;
		}
	    else
	    {
			e = cpxX.real / cpxX.imaginary;
	        f = cpxX.imaginary + e * cpxX.real;
	        
	        x = (real * e + imaginary) / f;
	        y = (imaginary * e - real) / f;
	    }

		return new Complex(x, y);
	}

	/**
	 * ���㸴���ģ
	 * 
	 * @return double�ͣ�ָ�������ģ
	 */
	public double abs()
	{
	    // ��ȡʵ�����鲿�ľ��ֵ
	    double x = Math.abs(real);
	    double y = Math.abs(imaginary);

	    if (real == 0)
			return y;
	    if (imaginary == 0)
			return x;
	    
	    
	    // ����ģ
	    if (x > y)
	        return (x * Math.sqrt(1 + (y / x) * (y / x)));
	    
	    return (y * Math.sqrt(1 + (x / y) * (x / y)));
	}

	/**
	 * ���㸴��ĸ�
	 * 
	 * @param n - �����ĸ��
	 * @param cpxR - Complex�����飬����Ϊn�����ظ�������и�
	 */
	public void root(int n, Complex[] cpxR)
	{
		if (n<1) 
			return;
	    
		double q = Math.atan2(imaginary, real);
	    double r = Math.sqrt(real*real + imaginary*imaginary);
	    if (r != 0)
	    { 
			r = (1.0/n)*Math.log(r);
			r = Math.exp(r);
		}

	    for (int k=0; k<=n-1; k++)
	    { 
			double t = (2.0*k*3.1415926+q)/n;
	        cpxR[k] = new Complex(r*Math.cos(t), r*Math.sin(t));
	    }
	}

	/**
	 * ���㸴���ʵ��ָ��
	 * 
	 * @param dblW - ����ʵ��ָ����ݴ�
	 * @return Complex�ͣ������ʵ��ָ��ֵ
	 */
	public Complex pow(double dblW)
	{
		// ����
		final double PI = 3.14159265358979;

		// �ֲ�����
		double r, t;
	    
	    // ����ֵ����
	    if ((real == 0) && (imaginary == 0))
			return new Complex(0, 0);
	    
	    // �����㹫ʽ�е���Ǻ�������
	    if (real == 0)
		{
	        if (imaginary > 0)
	            t = 1.5707963268;
	        else
	            t = -1.5707963268;
		}
	    else
		{
	        if (real > 0)
	            t = Math.atan2(imaginary, real);
	        else
	        {
				if (imaginary >= 0)
	                t = Math.atan2(imaginary, real) + PI;
	            else
	                t = Math.atan2(imaginary, real) - PI;
			}
	    }
	    
	    // ģ����
	    r = Math.exp(dblW * Math.log(Math.sqrt(real * real + imaginary * imaginary)));
	    
	    // �����ʵ��ָ��
	    return new Complex(r * Math.cos(dblW * t), r * Math.sin(dblW * t));
	}

	/**
	 * ���㸴��ĸ���ָ��
	 * 
	 * @param cpxW - ������ָ����ݴ�
	 * @param n - ���Ʋ���Ĭ��ֵΪ0����n=0ʱ����õĽ��Ϊ����ָ�����ֵ
	 * @return Complex�ͣ�����ĸ���ָ��ֵ
	 */
	public Complex pow(Complex cpxW, int n)
	{
		// ����
		final double PI = 3.14159265358979;
		// �ֲ�����
	    double r, s, u, v;
	    
	    // ����ֵ����
	    if (real == 0)
		{
	        if (imaginary == 0)
				return new Complex(0, 0);
	            
	        s = 1.5707963268 * (Math.abs(imaginary) / imaginary + 4 * n);
		}
	    else
		{
	        s = 2 * PI * n + Math.atan2(imaginary, real);
	        
	        if (real < 0)
			{
	            if (imaginary > 0)
	                s = s + PI;
	            else
	                s = s - PI;
	        }
	    }
	    
	    // �������㹫ʽ
	    r = 0.5 * Math.log(real * real + imaginary * imaginary);
	    v = cpxW.real * r + cpxW.imaginary * s;
	    u = Math.exp(cpxW.real * r - cpxW.imaginary * s);

	    return new Complex(u * Math.cos(v), u * Math.sin(v));
	}

	/**
	 * ���㸴�����Ȼ����
	 * 
	 * @return Complex�ͣ��������Ȼ����ֵ
	 */
	public Complex log()
	{
		double p = Math.log(Math.sqrt(real*real + imaginary*imaginary));
	    return new Complex(p, Math.atan2(imaginary, real));
	}

	/**
	 * ���㸴�������
	 * 
	 * @return Complex�ͣ����������ֵ
	 */
	public Complex sin()
	{
	    int i;
	    double x, y, y1, br, b1, b2;
	    double[] c = new double[6];
	    
	    // �б�ѩ��ʽ�ĳ���ϵ��
	    c[0] = 1.13031820798497;
	    c[1] = 0.04433684984866;
	    c[2] = 0.00054292631191;
	    c[3] = 0.00000319843646;
	    c[4] = 0.00000001103607;
	    c[5] = 0.00000000002498;
	    
	    y1 = Math.exp(imaginary);
	    x = 0.5 * (y1 + 1 / y1);
	    br = 0;
	    if (Math.abs(imaginary) >= 1)
	        y = 0.5 * (y1 - 1 / y1);
	    else
	    {
			b1 = 0;
	        b2 = 0;
	        y1 = 2 * (2 * imaginary * imaginary - 1);
	        for (i = 5; i >=0; --i)
			{
	            br = y1 * b1 - b2 - c[i];
	            if (i != 0)
				{
	                b2 = b1;
	                b1 = br;
	            }
	        }
	        
	        y = imaginary * (br - b1);
	    }
	    
	    // ��ϼ�����
	    x = x * Math.sin(real);
	    y = y * Math.cos(real);

		return new Complex(x, y);
	}

	/**
	 * ���㸴�������
	 * 
	 * @return Complex�ͣ����������ֵ
	 */
	public Complex cos()
	{
	    int i;
	    double x, y, y1, br, b1, b2;
	    double[] c = new double[6];
	    
	    // �б�ѩ��ʽ�ĳ���ϵ��
	    c[0] = 1.13031820798497;
	    c[1] = 0.04433684984866;
	    c[2] = 0.00054292631191;
	    c[3] = 0.00000319843646;
	    c[4] = 0.00000001103607;
	    c[5] = 0.00000000002498;
	    
	    y1 = Math.exp(imaginary);
	    x = 0.5 * (y1 + 1 / y1);
	    br = 0;
	    if (Math.abs(imaginary) >= 1)
	        y = 0.5 * (y1 - 1 / y1);
	    else
	    {
			b1 = 0;
	        b2 = 0;
	        y1 = 2 * (2 * imaginary * imaginary - 1);
	        for (i=5 ; i>=0; --i)
			{
	            br = y1 * b1 - b2 - c[i];
	            if (i != 0)
	            {
					b2 = b1;
	                b1 = br;
	            }
	        }
	        
	        y = imaginary * (br - b1);
	    }
	    
	    // ��ϼ�����
	    x = x * Math.cos(real);
		y = -y * Math.sin(real);

		return new Complex(x, y);
	}

	/**
	 * ���㸴�������
	 * 
	 * @return Complex�ͣ����������ֵ
	 */
	public Complex tan()
	{
		return sin().divide(cos());
	}
}
