package com.taskplannersql.servlets;

import com.taskplannersql.service.GoalService;
import com.taskplannersql.service.SubGoalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/subgoaledit")
public class EditSubGoalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String subGoalName = req.getParameter("editsubgoal");
        String newSubGoalName = req.getParameter("neweditsubgoal");
        SubGoalService.editSubGoal(subGoalName, newSubGoalName);
        resp.sendRedirect("/taskplannersql/task");
    }
}
