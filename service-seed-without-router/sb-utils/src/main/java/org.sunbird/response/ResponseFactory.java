package org.sunbird.response;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.BaseException;
import org.sunbird.message.IResponseMessage;
import org.sunbird.message.Localizer;
import org.sunbird.message.ResponseCode;
import org.sunbird.request.Request;

public class ResponseFactory {
    private static final String msgKeyStr = "message";
    protected static Localizer localizerObject = Localizer.getInstance();

    /**
     * this method will prepare the failure response of the API
     * @param exception
     * @return
     */
    public static Response getFailureMessage(Object exception, Request request) {
        Response response = new Response();
        if (request != null) {
            response.setId(request.getId());
            response.setVer(request.getVer());
            response.setTs(System.currentTimeMillis() + StringUtils.EMPTY);
        }
        if (exception instanceof BaseException) {
            BaseException ex = (BaseException) exception;
            response.put(msgKeyStr, ex.getMessage());
            response.setResponseCode(ResponseCode.CLIENT_ERROR.getCode());
            return response;
        }
        response.setResponseCode(ResponseCode.SERVER_ERROR.getCode());
        response.put(msgKeyStr, localizerObject.getMessage(IResponseMessage.INTERNAL_ERROR, null));
        return response;
    }

    public static Response getSuccessMessage(Request request) {
        Response response = new Response();
        response.setId(request.getId());
        response.setVer(request.getVer());
        response.setTs(System.currentTimeMillis() + StringUtils.EMPTY);
        return response;
    }
}