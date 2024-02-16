package com.example.q2paytechnologies.ApiService;

import org.json.JSONObject;

public interface LoginCallBackCode {

    void onResponse(JSONObject jsonObject, int request_code, int status_code);

    void onFailure(Object object, String error_msg);
}
