//package maple.doljub.common.exception;
//
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    // CustomException에 대한 처리
//    @ExceptionHandler(CustomException.class)
//    protected ModelAndView handleCustomException(CustomException e) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("errorMessage", e.getMessage());
//        return modelAndView;
//    }
//
//    // MethodArgumentNotValidException에 대한 처리
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ModelAndView handleValidationException(MethodArgumentNotValidException e) {
//        ModelAndView modelAndView = new ModelAndView("validation_error");
//        modelAndView.addObject("errorMessage", "Validation failed");
//        return modelAndView;
//    }
//}