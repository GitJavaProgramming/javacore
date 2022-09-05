package org.pp.complier.simple;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/4 19:56
 * ************厚德载物************
 **/
public class Compiler {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        parser.statements();
//        lexer.runLexer();
    }
}
