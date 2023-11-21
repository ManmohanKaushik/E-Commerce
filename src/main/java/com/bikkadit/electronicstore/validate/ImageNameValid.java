package com.bikkadit.electronicstore.validate;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageNameValidetor.class)
public @interface  ImageNameValid {
    String message()default "Invalid Image Name.....";
    Class<?>[] group() default{};
    Class<? extends Payload[]>[] payload() default {};
}
