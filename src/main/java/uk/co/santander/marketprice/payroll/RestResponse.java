package uk.co.santander.marketprice.payroll;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResponse<T> {

    private String exceptionMessage;
    private Boolean status;
    private String message = "Success";
    private T resultObject;
}