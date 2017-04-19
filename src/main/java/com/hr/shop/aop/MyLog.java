package com.hr.shop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
@Aspect
public class MyLog {  
	
    final Logger logger = LoggerFactory.getLogger(MyLog.class);  
//    String logStr = null ;  
    /**  
     * 必须为final String类型的,注解里要使用的变量只能是静态常量类型的  
     */
    public static final String EDP="execution(* com.hr.shop.action.*.*(..))";
    
//     /**
//       * 切面的前置方法 即方法执行前拦截到的方法
//       * 在目标方法执行之前的通知
//       * @param jp
//       */
//     @Before(EDP)
//     public void doBefore(JoinPoint jp){
//       logger.debug("开始执行"+ jp.getTarget().getClass().getName() + "."  
//               + jp.getSignature().getName() + "()");
//     }
//     
//     
//     /**
//      * 在方法正常执行通过之后执行的通知叫做返回通知
//       * 可以返回到方法的返回值 在注解后加入returning 
//      * @param jp
//      * @param result
//      */
//     @AfterReturning(value=EDP,returning="result")
//     public void doAfterReturning(JoinPoint jp,String result){
//         System.out.println("===========执行后置通知============");
//     }
//     
//     /**
//      * 最终通知：目标方法调用之后执行的通知（无论目标方法是否出现异常均执行）
//       * @param jp
//      */
//     @After(value=EDP)
//     public void doAfter(JoinPoint jp){
//         System.out.println("===========执行最终通知============");
//     }
     
     /**
      * 环绕通知：目标方法调用前后执行的通知，可以在方法调用前后完成自定义的行为。
       * @param pjp
      * @return
      * @throws Throwable
      */
     @Around(EDP)
     public Object doAround(ProceedingJoinPoint pjp) throws Throwable{

        logger.debug("=======Entering "+ pjp.getTarget().getClass().getName()+ "."  
               + pjp.getSignature().getName() + "()=======");
         // 调用方法的参数
         Object[] args = pjp.getArgs();
        
         //获取用户请求方法的参数并序列化为JSON格式字符串  
         String params = "";  
         if (pjp.getArgs() != null && pjp.getArgs().length > 0) {  
             for (int i = 0; i < pjp.getArgs().length; i++) {  
                 params += pjp.getArgs()[i] + ";";  
             }  
         }  
         logger.debug("param:{}" , params);
         // 执行完方法的返回值
         // 调用proceed()方法，就会触发切入点方法执行
         Object result=pjp.proceed();
         logger.debug("result:{}" , result);
         logger.debug("======Ending "+ pjp.getTarget().getClass().getName()+ "."  
                 + pjp.getSignature().getName() + "()" + "=========");
         return result;
     }
     
     /**
      * 在目标方法非正常执行完成, 抛出异常的时候会走此方法
      * @param jp
      * @param ex
      */
     @AfterThrowing(value=EDP,throwing="ex")
     public void doAfterThrowing(JoinPoint jp,Exception ex) {
         logger.error("error："+ jp.getTarget().getClass().getName() + "."  
               + jp.getSignature().getName() + "()"+ "\n" +"error message："+ex);
     }
 }
