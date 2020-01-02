package com.github.TesraSupernet.sidechain.smartcontract.ongx;

import com.github.TesraSupernet.common.Address;

public class Swap {
    public Address address;
    public long value;
    public Swap(Address address, long value){
        this.address = address;
        this.value = value;
    }
}
