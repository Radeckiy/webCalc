package com.radeckiy.calc.server.models.rpc;

import com.google.gson.annotations.SerializedName;

public class RPCrequest {
    @SerializedName("jsonrpc")
    private String jsonRPCversion;
    @SerializedName("method")
    private String methodName;
    // todo: Заменить String на что-то более универсальное
    private String params;

    /** По спецификации JSON-RPC здесь должен быть еще ID,
    но в силу того, что запросы не сохраняются в хранилище,
    ID - опущен. **/

    public RPCrequest(String methodName, String params) {
        this.jsonRPCversion = "2.0";
        this.methodName = methodName;
        this.params = params;
    }

    public RPCrequest(String jsonRPCversion, String methodName, String params) {
        this.jsonRPCversion = jsonRPCversion;
        this.methodName = methodName;
        this.params = params;
    }

    public String getJsonRPCversion() {
        return jsonRPCversion;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParams() {
        return params;
    }
}
