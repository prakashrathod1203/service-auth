package in.om.component;

import in.om.response.ResponseBody;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

import java.lang.annotation.*;

/**
 * This is the custom annotation, will help us to document all API's endpoint responses.
 * When new error message handled throughout the project, must be configured over here also.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = ResponseBody.class),
        @ApiResponse(code = 400, message = "Bad request", response = ResponseBody.class,
                examples = @Example(value = @ExampleProperty(value = "{'success':false,'message':'BAD_REQUEST','result': 'null'}", mediaType = "application/json"))),
        @ApiResponse(code = 401, message = "Unauthorized", response = ResponseBody.class,
                examples = @Example(value = @ExampleProperty(value = "{'success':false,'message':'UNAUTHORIZED','result': 'null'}", mediaType = "application/json"))),
        @ApiResponse(code = 415, message = "Unsupported Media Type", response = ResponseBody.class,
                examples = @Example(value = @ExampleProperty(value = "{'success':false,'message':'UNSUPPORTED_MEDIA_TYPE','result': 'null'}", mediaType = "application/json"))),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseBody.class,
                examples = @Example(value = @ExampleProperty(value = "{'success':false,'message':'INTERNAL_SERVER_ERROR','result': 'null'}", mediaType = "application/json")))
})
public @interface ApiResponseDoc {
}

