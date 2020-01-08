package com.github.TesraSupernet.sidechain;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.sidechain.smartcontract.governance.Governance;
import com.github.TesraSupernet.sidechain.smartcontract.tsgx.TsgX;

public class SidechainVm {
    private Governance governance;
    private TsgX tsgX;
    private TstSdk sdk;
    public SidechainVm(TstSdk sdk){
        this.sdk = sdk;
    }

    public Governance governance() {
        if (governance == null){
            governance = new Governance(sdk);
        }
        return governance;
    }

    public TsgX tsgX() {
        if (tsgX == null){
            tsgX = new TsgX(sdk);
        }
        return tsgX;
    }
}
