package skAndroid.errors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by khangtnse60992 on 1/3/2015.
 */
@Controller(value = "/")
class CustomErrorController {

    /**
     * Display an error page, as defined in web.xml <code>custom-error</code> element.
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String generalError(HttpServletRequest request, HttpServletResponse response, Model model) {
        // retrieve some useful information from the request

        String message = "404 Not found";

        model.addAttribute("errorMessage", message);
        return "error/general";
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return throwable.getMessage();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}
