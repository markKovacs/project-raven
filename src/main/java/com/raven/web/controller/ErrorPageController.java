package com.raven.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Default error page handler. Used if exceptions not being handled locally in controller.
 */
@Controller
public class ErrorPageController implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "/error")
    public String error(Model model, HttpServletRequest request) {

        // Take request, get all attributes and then filter to error attributes, also include stack trace
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> error = this.errorAttributes.getErrorAttributes(requestAttributes, true);

        // Logic could be added to return different views based on error attributes

        model.addAttribute("timestamp", error.get("timestamp"));
        model.addAttribute("path", error.get("path"));
        model.addAttribute("status", error.get("status"));
        model.addAttribute("error", error.get("error"));
        model.addAttribute("message", error.get("message"));

        return "errors/detailed_error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
