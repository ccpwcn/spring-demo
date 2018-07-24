package com.sinoiov.lhjh.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet模板类
 * Created by lidawei on 2017/5/22.
 */
public abstract class ServletTemplate extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ServletTemplate.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("准备开始调用具体任务");
        doTask();
        logger.info("具体任务调用完成");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.print("SUCCEEDED");
        out.flush();
        out.close();
    }

    protected abstract void doTask();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
