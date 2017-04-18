package com.kaola.edata.common.pageTool;


import java.io.Serializable;

/**
 * 页面范围，记录了对应页面的起始行号和结束行号。
 * @version 1.00 2010-4-25
 * @since 1.5
 * @author ZhangShixi
 */
public class PageScope implements Serializable {

    private static final long serialVersionUID = 7480833511913154644L;
    // 起始行号
    private int startLine;
    // 结束行号
    private int endLine;

    /**
     * 默认无参构造器。
     */
    public PageScope() {
    }

    /**
     * 以起始行号和结束行号构造一个页面范围对象。
     * @param startLine 起始行号。
     * @param endLine 结束行号。
     */
    public PageScope(int startLine, int endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    /**
     * 获取当前页面的开始行号。
     * @return 开始行号。
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * 设置当前页面的开始行号。
     * @param startLine 开始行号。
     */
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    /**
     * 获取当前页面的结束行号。
     * @return 结束行号。
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * 设置当前页面的结束行号。
     * @param endLine 结束行号。
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    /**
     * 重写父类的toString()方法，返回页面范围信息的字符串表示。
     * @return 页面范围信息的字符串表示。该字符串由当前页面的开始行号和结束行号信息组成。
     */
    @Override
    public String toString() {
        StringBuilder scopeInfo = new StringBuilder();
        scopeInfo.append("Scope information of the page is: ");
        scopeInfo.append(this.startLine);
        scopeInfo.append(" to ");
        scopeInfo.append(this.endLine);

        return scopeInfo.toString();
    }
}
