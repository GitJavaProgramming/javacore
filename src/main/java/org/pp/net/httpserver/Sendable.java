package org.pp.net.httpserver;

import java.io.IOException;

/*
 *��ʾ���������Է��͸��ͻ��˵Ķ���
 */
public interface Sendable {
    // ׼�����͵�����
    void prepare() throws IOException;

    // ����ͨ�����Ͳ������ݣ�����������ݷ�����ϣ��ͷ���false
    // �����������δ���ͣ��ͷ���true
    // ������ݻ�û��׼���ã����׳�IllegalStateException
    boolean send(ChannelIO cio) throws IOException;

    //������������������ϣ��͵��ô˷������ͷ�����ռ�õ���Դ
    void release() throws IOException;
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̾���>>                       *
 * ����֧����ַ��www.javathinker.org                *
 ***************************************************/
