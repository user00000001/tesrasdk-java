package com.github.TesraSupernet.core.sidechaingovernance;


import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.common.Address;

import java.util.HashMap;
import java.util.Map;

public class SwapParam {
    public String sideChainId;
    public Address address;
    public long tsgXAccount;
    public SwapParam(String sideChainId, Address address, long tsgXAccount){
        this.sideChainId = sideChainId;
        this.address = address;
        this.tsgXAccount = tsgXAccount;
    }
    public String toJson(){
        Map map = new HashMap<>();
        map.put("sideChainId", sideChainId);
        map.put("address", address.toBase58());
        map.put("tsgXAccount",tsgXAccount);
        return JSON.toJSONString(map);
    }
}
