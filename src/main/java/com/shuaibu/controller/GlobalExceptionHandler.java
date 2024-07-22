package com.shuaibu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(Exception ex) {
//
//        logger.error("Generic Exception", ex);
//
//        ModelAndView modelAndView = new ModelAndView("errors/error");
//        modelAndView.addObject("message", ex.getMessage());
//        return modelAndView;
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {

        logger.error("Generic Exception", ex);

        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}
