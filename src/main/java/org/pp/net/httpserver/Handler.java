package org.pp.net.httpserver;

import java.io.*;
import java.nio.channels.*;

public interface Handler {
    public void handle(SelectionKey key) throws IOException;
}


/****************************************************
 * ���ߣ�������                                     *
 * ��Դ��<<Java�����̾���>>                       *
 * ����֧����ַ��www.javathinker.org                *
 ***************************************************/
