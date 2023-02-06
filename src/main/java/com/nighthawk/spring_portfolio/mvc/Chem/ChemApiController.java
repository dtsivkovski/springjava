package com.nighthawk.spring_portfolio.mvc.Chem;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Chem")
public class ChemApiController {
    @WebServlet("/density")
    public class DensityServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String mass = request.getParameter("mass");
            String volume = request.getParameter("volume");
            double density = Double.parseDouble(mass) / (Double.parseDouble(volume) / 1000);
            request.setAttribute("density", density);
            request.getRequestDispatcher("densityResult.jsp").forward(request, response);
        }
    }
}
