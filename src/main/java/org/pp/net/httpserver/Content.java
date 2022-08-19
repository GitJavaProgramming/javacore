package org.pp.net.httpserver;

/**
 * ��ʾ���������͸��ͻ�����������
 */
public interface Content extends Sendable {
  //���ݵ�����
  String type();

  //�����ݻ�û��׼��֮ǰ������û�е���prepare()����֮ǰ��length()��������-1��
  long length();
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̾���>>                       *
 * ����֧����ַ��www.javathinker.org                *
 ***************************************************/
