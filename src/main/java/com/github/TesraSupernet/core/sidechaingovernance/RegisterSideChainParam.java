package com.github.TesraSupernet.core.sidechaingovernance;


import com.github.TesraSupernet.common.Address;

public class RegisterSideChainParam {
    public String sideChainID;
    public Address address;
    public int ratio;
    public long deposit;
    public long tsgPool;
    public byte[] caller;
    public int keyNo;
    public RegisterSideChainParam(String sideChainID, Address address, int ratio, long deposit, long tsgPool, byte[] caller, int keyNo){
        this.sideChainID = sideChainID;
        this.address = address;
        this.ratio = ratio;
        this.deposit = deposit;
        this.tsgPool = tsgPool;
        this.caller = caller;
        this.keyNo = keyNo;
    }
}
