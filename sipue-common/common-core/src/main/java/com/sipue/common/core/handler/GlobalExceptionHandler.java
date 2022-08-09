package com.sipue.common.core.handler;

//import com.alibaba.csp.sentinel.Tracer;

import com.sipue.common.core.exception.FeignServiceException;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 全局异常处理器结合sentinel 全局异常处理器不能作用在 oauth server
 * @Author wangjunyu
 * @date 2022-11-15
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnExpression("!'${security.oauth2.client.clientId}'.isEmpty()")
public class GlobalExceptionHandler {

	/**
	 * 全局异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result handleGlobalException(Exception e) {

		//这里对feign自定义异常做特殊处理
		if(e instanceof NestedServletException && Objects.nonNull(e.getCause())&&e.getCause() instanceof AssertionError){
			AssertionError exception=(AssertionError)e.getCause();
			if(Objects.nonNull(exception.getCause())&&exception.getCause() instanceof FeignServiceException) {
				FeignServiceException feignServiceException = (FeignServiceException) exception.getCause();
				StackTraceElement[] stackTraceElement = feignServiceException.getStackTrace();
				log.warn("全局异常-->接口服务调用异常信息 exception: {};位置: {}", feignServiceException.toString(), Objects.nonNull(stackTraceElement) ? stackTraceElement[0] : "N/A");
				return Result.fail(feignServiceException.getCode(), feignServiceException.getMsg(), feignServiceException.getInnerMsg());
			}else if(Objects.nonNull(exception.getCause())&&exception.getCause() instanceof ServiceException) {
				ServiceException serviceException = (ServiceException) exception.getCause();
				StackTraceElement[] stackTraceElement= serviceException.getStackTrace();
				log.warn("全局异常-->自定义服务异常信息 exception: {};位置: {}", serviceException.toString(), Objects.nonNull(stackTraceElement)?stackTraceElement[0]:"N/A");
				return Result.fail(serviceException.getCode(), serviceException.getMsg(), serviceException.getInnerMsg());
			}else{
				log.error("全局异常信息 exception= {}", e.toString());
			}
			return Result.fail(CommonErrorCode.ERROR);
		}else {
			log.error("全局异常信息 exception= {}", e.toString());
			e.printStackTrace();
			// 业务异常交由 sentinel 记录
			//Tracer.trace(e); todo 待处理
			return Result.fail(CommonErrorCode.ERROR);
		}
	}
	/**
	 * 全局自定义服务异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(ServiceException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result handleServiceGlobalException(ServiceException e) {
		//log.warn("全局自定义服务异常信息 exception= {}", e.toString());
		StackTraceElement[] stackTraceElement= e.getStackTrace();
		log.warn("全局自定义服务异常信息 exception: {};位置: {}", e.toString(), Objects.nonNull(stackTraceElement)?stackTraceElement[0]:"N/A");
		//e.printStackTrace();
		// 业务异常交由 sentinel 记录
		//Tracer.trace(e); todo 待处理
		return Result.fail(e);
	}
	/**
	 * 全局Feign服务调用异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(FeignServiceException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result handleFeignServiceGlobalException(FeignServiceException e) {
		//log.warn("全局自定义服务异常信息 exception= {}", e.toString());
		StackTraceElement[] stackTraceElement= e.getStackTrace();
		log.warn("接口服务调用异常信息 exception: {};位置: {}", e.toString(), Objects.nonNull(stackTraceElement)?stackTraceElement[0]:"N/A");
		//e.printStackTrace();
		// 业务异常交由 sentinel 记录
		//Tracer.trace(e); todo 待处理
		return Result.fail(e.getCode(),e.getMsg());
	}


	/**
	 * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link Assert}抛出
	 * @see Assert#hasLength(String, String)
	 * @see Assert#hasText(String, String)
	 * @see Assert#isTrue(boolean, String)
	 * @see Assert#isNull(Object, String)
	 * @see Assert#notNull(Object, String)
	 * @param exception 参数校验异常
	 * @return API返回结果对象包装后的错误输出结果
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,exception = {}", exception.getMessage(), exception);
		return Result.fail(exception.getMessage());
	}

	/**
	 * AccessDeniedException
	 * @param e the e
	 * @return R
	 */
//	@ExceptionHandler(AccessDeniedException.class)
//	@ResponseStatus(HttpStatus.FORBIDDEN)
//	public Result handleAccessDeniedException(AccessDeniedException e) {
//		String msg = SpringSecurityMessageSource.getAccessor().getMessage("AbstractAccessDecisionManager.accessDenied",
//				e.getMessage());
//		log.error("拒绝授权异常信息 exception ={}", msg, e);
//		return Result.fail(e.getLocalizedMessage());
//	}

	/**
	 * validation Exception
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	//@ResponseStatus(HttpStatus.OK)
	public Result handleBodyValidException(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,exception = {},{}", fieldErrors.get(0).getDefaultMessage(),exception.toString());
		return Result.fail(CommonErrorCode.REQUEST_INVALID_PARAM.getCode(),CommonErrorCode.REQUEST_INVALID_PARAM.getMsg()+fieldErrors.get(0).getDefaultMessage());
	}

	/**
	 * validation Exception (以form-data形式传参)
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ BindException.class })
	//@ResponseStatus(HttpStatus.OK)
	public Result bindExceptionHandler(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,exception = {},{}", fieldErrors.get(0).getDefaultMessage());
		return Result.fail(fieldErrors.get(0).getDefaultMessage(),exception.toString());
	}

	/**
	 * validation Exception (以form-data形式传参)
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	//@ResponseStatus(HttpStatus.OK)
	public Result argumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
		log.warn("参数类型转换异常,exception = {}", exception.getMessage(),exception);
		return Result.fail(CommonErrorCode.REQUEST_INVALID_PARAM);
	}

	/**
	 * 这个异常时由于前端请求的参数格式不正确，导致不能正确转换类型。
	 * 比如前端传递的参数不是json格式等错误。由于是前端的错误只记录警告日志
	 **/
	@ExceptionHandler(HttpMessageNotReadableException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
		log.warn("参数格式转换异常: {}", ex.getMessage());
		return new Result(CommonErrorCode.REQUEST_INVALID_PARAM);
	}

	/**
	 * 这个异常时由于前端请求的参数格式不正确，比如参数本不能为空,却没有传此参数
	 **/
	@ExceptionHandler(ConstraintViolationException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result constraintViolationExceptionHandler(ConstraintViolationException ex) {
		log.error("参数值异常: {}", ex.getMessage());
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		List<String> messages=null;
		//ValidateInfo包含当前出错的字段,错误信息,出错的字段所在的类型,等必要的信息
		if(constraintViolations!=null && !constraintViolations.isEmpty()) {
			messages=new ArrayList<>();
			for (ConstraintViolation<?> violation : constraintViolations) {
				Class<? extends Object> class1 = violation.getRootBean().getClass();
				String simpleName = class1.getName();
				log.error(simpleName + ":" + violation.getMessage());
				messages.add(violation.getMessage());
			}
		}
		if(messages==null){
			return Result.fail(CommonErrorCode.REQUEST_INVALID_PARAM);
		}else{
			return Result.fail(CommonErrorCode.REQUEST_INVALID_PARAM.getCode(), String.join(";",messages));
		}
	}
	/**
	 * 这个异常时由于后端返回的参数格式不正确，导致不能正确转换类型。
	 * 比如导出,错误信息是通过json格式传给前端的,正常情况是直接返回文件数据,所以会出现类型转换错误
	 **/
	@ExceptionHandler(HttpMessageNotWritableException.class)
	//@ResponseStatus(HttpStatus.OK)
	public Result httpMessageNotWritableExceptionHandler(HttpMessageNotWritableException ex) {
		if((ex.getMessage().contains("application/vnd.")||ex.getMessage().contains("application/octet"))&&ex.getMessage().contains("model.Result")){
			log.debug("返回的是文件类型,所以无需转换,此处忽略错误:"+ex.getMessage());
			return null;
		}else {
			log.warn("httpMessageNotWritableExceptionHandler: {}", ex.getMessage());
			return new Result(CommonErrorCode.REQUEST_INVALID_PARAM);
		}
	}
}
