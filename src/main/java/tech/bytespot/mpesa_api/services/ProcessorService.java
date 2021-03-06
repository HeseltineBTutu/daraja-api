package tech.bytespot.mpesa_api.services;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import tech.bytespot.mpesa_api.configurations.HttpConfiguration;
import tech.bytespot.mpesa_api.configurations.MpesaConfiguration;
import tech.bytespot.mpesa_api.configurations.MpesaUtils;
import tech.bytespot.mpesa_api.templates.ProcessorsTemplate;
import tech.bytespot.mpesa_api.utils.MpesaConstants;
import tech.bytespot.mpesa_api.utils.MpesaException;

/**
 * @author eli_muraya on 15/10/2019 .
 */
public class ProcessorService implements ProcessorsTemplate {

  private final static Logger LOGGER = Logger.getLogger(ProcessorService.class.getName());


  private MpesaHelpers mpesaHelpers;

  public ProcessorService(MpesaHelpers mpesaHelpers) {
    this.mpesaHelpers = mpesaHelpers;
  }


  @Override
  public Map<String, Object> processFields(MpesaConfiguration mpesaConfiguration)
      throws MpesaException {
    Map<String, Object> fieldsMap = new HashMap<>();

    var app_key_secret = mpesaConfiguration.getAppKey() + ":" + mpesaConfiguration.getAppSecret();
    fieldsMap.put(MpesaUtils.App_Key_Secret, app_key_secret);

    if ((mpesaConfiguration.getAppMode()).equals(MpesaUtils.Test_Mode)) {
      LOGGER.info("App in test mode ...");
      fieldsMap.put(MpesaUtils.URL_for_access_token, MpesaConstants.test_acces_token_url);
      fieldsMap.put(MpesaUtils.URL_for_access_token, MpesaConstants.test_acces_token_url);
      fieldsMap.put(MpesaUtils.URL_for_stkpush_request, MpesaConstants.test_stkpush_url);
      fieldsMap
          .put(MpesaUtils.URL_for_stkpush_query_request, MpesaConstants.test_stkpush_query_url);
      fieldsMap.put(MpesaUtils.URL_for_b2c_request, MpesaConstants.test_b2c_url);
      fieldsMap.put(MpesaUtils.URL_for_b2b_request, MpesaConstants.test_b2b_url);
      fieldsMap.put(MpesaUtils.URL_for_reversal_request, MpesaConstants.test_reversal_url);
      fieldsMap.put(MpesaUtils.URL_for_transaction_status_request,
          MpesaConstants.test_transaction_status_url);
      fieldsMap.put(MpesaUtils.URL_for_balance_request, MpesaConstants.test_balance_url);
      fieldsMap.put(MpesaUtils.URL_for_c2b_register_request, MpesaConstants.test_c2b_register_url);
      fieldsMap.put(MpesaUtils.URL_for_c2b_simulate_request, MpesaConstants.test_c2b_simulate_url);

    } else {
      LOGGER.info("App in prod mode ...");
      fieldsMap.put(MpesaUtils.URL_for_access_token, MpesaConstants.prod_acces_token_url);
      fieldsMap.put(MpesaUtils.URL_for_access_token, MpesaConstants.prod_acces_token_url);
      fieldsMap.put(MpesaUtils.URL_for_stkpush_request, MpesaConstants.prod_stkpush_url);
      fieldsMap
          .put(MpesaUtils.URL_for_stkpush_query_request, MpesaConstants.prod_stkpush_query_url);
      fieldsMap.put(MpesaUtils.URL_for_b2c_request, MpesaConstants.prod_b2c_url);
      fieldsMap.put(MpesaUtils.URL_for_b2b_request, MpesaConstants.prod_b2b_url);
      fieldsMap.put(MpesaUtils.URL_for_reversal_request, MpesaConstants.prod_reversal_url);
      fieldsMap.put(MpesaUtils.URL_for_transaction_status_request,
          MpesaConstants.prod_transaction_status_url);
      fieldsMap.put(MpesaUtils.URL_for_balance_request, MpesaConstants.prod_balance_url);
      fieldsMap.put(MpesaUtils.URL_for_c2b_register_request, MpesaConstants.prod_c2b_register_url);
      fieldsMap.put(MpesaUtils.URL_for_c2b_simulate_request, MpesaConstants.prod_c2b_simulate_url);

    }
    var tokenUrl = (String) fieldsMap.get(MpesaUtils.URL_for_access_token);
    var appKeySecret = (String) fieldsMap.get(MpesaUtils.App_Key_Secret);
    var token = "";

    var http = mpesaConfiguration.getHttpConfiguration() == null ? new HttpConfiguration() :
        mpesaConfiguration.getHttpConfiguration();
    if (mpesaConfiguration.getAppAccessToken() == null) {
      LOGGER.info("App token is not set. Generating new token");
      token = mpesaHelpers.generateAccessToken(appKeySecret, tokenUrl, http).getAccess_token();
    } else {
      LOGGER.info("App token is set. Skipping token generation. ...");
      token = mpesaConfiguration.getAppAccessToken();
    }
    fieldsMap.put(MpesaUtils.Access_Token, token);
    return fieldsMap;
  }

}
