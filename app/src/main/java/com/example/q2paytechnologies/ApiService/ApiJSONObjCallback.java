package com.example.q2paytechnologies.ApiService;

import org.json.JSONObject;

public interface ApiJSONObjCallback {
    void onResponse(JSONObject var1, int var2);

    void onFailure(Throwable var1, int var2);
}
