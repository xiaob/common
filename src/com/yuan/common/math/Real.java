/*
 * ���ڴ���ֵ��ʵ����Real
 *
 * �ܳ�������
 */
package com.yuan.common.math;

/**
 * ���ڴ���ֵ��ʵ����Real

 * @author �ܳ���
 * @version 1.0
 */
public class Real 
{
	private Double value = null;
	
	/**
	 * ���캯��
	 */
	public Real()
	{
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v Real��ֵ
	 */
	public Real(Real v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v int��ֵ
	 */
	public Real(int v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v short��ֵ
	 */
	public Real(short v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v long��ֵ
	 */
	public Real(long v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v float��ֵ
	 */
	public Real(float v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v double��ֵ
	 */
	public Real(double v)
	{
		setValue(v);
	}

	/**
	 * ָ��ֵ���캯��
	 * 
	 * @param v String��ֵ
	 */
	public Real(String v)
	{
		setValue(v);
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v Real��ֵ
	 */
	public void setValue(Real v)
	{
		value = new Double(v.doubleValue());
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v int��ֵ
	 */
	public void setValue(int v)
	{
		value = new Double(v);
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v long��ֵ
	 */
	public void setValue(long v)
	{
		value = new Double(v);
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v short��ֵ
	 */
	public void setValue(short v)
	{
		value = new Double(v);
	}
	
	/**
	 * ��ֵ����
	 * 
	 * @param v float��ֵ
	 */
	public void setValue(float v)
	{
		value = new Double(v);
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v double��ֵ
	 */
	public void setValue(double v)
	{
		value = new Double(v);
	}

	/**
	 * ��ֵ����
	 * 
	 * @param v String��ֵ
	 */
	public void setValue(String v)
	{
		value = new Double(Double.parseDouble(v));
	}
	
	/**
	 * ת��Ϊ�ַ���
	 * 
	 * @return String��ֵ
	 */
	public String toString()
	{
		return new Float(value.floatValue()).toString();
	}

	/**
	 * ȡֵ����
	 * 
	 * @return int��ֵ
	 */
	public int intValue()
	{
		return value.intValue();
	}

	/**
	 * ȡֵ����
	 * 
	 * @return short��ֵ
	 */
	public short shortValue()
	{
		return value.shortValue();
	}

	/**
	 * ȡֵ����
	 * 
	 * @return long��ֵ
	 */
	public long longValue()
	{
		return value.longValue();
	}

	/**
	 * ȡֵ����
	 * 
	 * @return float��ֵ
	 */
	public float floatValue()
	{
		return value.floatValue();
	}

	/**
	 * ȡֵ����
	 * 
	 * @return double��ֵ
	 */
	public double doubleValue()
	{
		return value.doubleValue();
	}
}
