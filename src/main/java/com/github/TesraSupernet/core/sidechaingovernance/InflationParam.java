package com.github.TesraSupernet.core.sidechaingovernance;


import com.github.TesraSupernet.common.Address;

public class InflationParam {
    public String sideChainId;
    public Address address;
    public long depositAdd;
    public long tsgPoolAdd;
    public InflationParam(String sideChainId, Address address, long depositAdd, long tsgPoolAdd){
        this.sideChainId = sideChainId;
        this.address = address;
        this.depositAdd = depositAdd;
        this.tsgPoolAdd = tsgPoolAdd;
    }
}
