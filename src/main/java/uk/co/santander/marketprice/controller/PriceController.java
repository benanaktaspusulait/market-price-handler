package uk.co.santander.marketprice.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.payroll.RestResponse;
import uk.co.santander.marketprice.service.price.PriceService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    @NonNull private final PriceService priceService;

    @GetMapping("/getByInstrumentName")
    public ResponseEntity<RestResponse<PriceFeed>> getByInstrumentName(@RequestParam("instrumentName") String instrumentName){

        try{
            RestResponse<PriceFeed> response = new RestResponse<>();
            PriceFeed priceFeedPojo = priceService.findById(instrumentName);
            response.setResultObject(priceFeedPojo);
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResourceNotFoundException rnfe){
            log.error(rnfe.getLocalizedMessage());
            RestResponse<PriceFeed> response = new RestResponse<>();
            response.setExceptionMessage("Could not find price feed :" + instrumentName);
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


    }

}
