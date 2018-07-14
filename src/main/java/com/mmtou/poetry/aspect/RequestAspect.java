package com.mmtou.poetry.aspect;

import com.mmtou.poetry.common.Request;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class RequestAspect {

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Pointcut("execution(* com.mmtou.poetry.web.*Controller.*(..))")
  public void excudeService() {
  }

  @Around("excudeService()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Object[] args = pjp.getArgs();
    if (pjp != null && args.length > 0) {
      Object arg = args[0];
      if (arg instanceof Request) {
        Request request = (Request) arg;
        request.setCurrentUserId(1l);
        request.setCurrentUserName("乖，摸摸头");
      }
    }

    return pjp.proceed();
  }

}
