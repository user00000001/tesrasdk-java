package com.github.TesraSupernet.sidechain;

import com.github.TesraSupernet.OntSdk;
import com.github.TesraSupernet.sidechain.smartcontract.governance.Governance;
import com.github.TesraSupernet.sidechain.smartcontract.ongx.OngX;

public class SidechainVm {
    private Governance governance;
    private OngX ongX;
    private OntSdk sdk;
    public SidechainVm(OntSdk sdk){
        this.sdk = sdk;
    }

    public Governance governance() {
        if (governance == null){
            governance = new Governance(sdk);
        }
        return governance;
    }

    public OngX ongX() {
        if (ongX == null){
            ongX = new OngX(sdk);
        }
        return ongX;
    }
}
