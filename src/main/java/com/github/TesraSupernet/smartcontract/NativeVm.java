/*
 * Copyright (C) 2019-2020 The TesraSupernet Authors
 * This file is part of The TesraSupernet library.
 *
 *  The TesraSupernet is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The TesraSupernet is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with The TesraSupernet.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.TesraSupernet.smartcontract;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.sdk.manager.ConnectMgr;
import com.github.TesraSupernet.smartcontract.nativevm.*;

public class NativeVm {
    private Tst tst = null;
    private Tsg tsg = null;
    private TstId tstId = null;
    private GlobalParams globalParams = null;
    private Auth auth = null;
    private Governance governance = null;
    private SideChainGovernance sideChainGovernance = null;
    private TstSdk sdk;
    public NativeVm(TstSdk sdk){
        this.sdk = sdk;
    }
    /**
     *  get TstAsset Tx
     * @return instance
     */
    public Tst tst() {
        if(tst == null){
            tst = new Tst(sdk);
        }
        return tst;
    }
    public Tsg tsg() {
        if(tsg == null){
            tsg = new Tsg(sdk);
        }
        return tsg;
    }
    public TstId tstId(){
        if (tstId == null){
            tstId = new TstId(sdk);
        }
        return tstId;
    }
    public GlobalParams gParams(){
        if (globalParams == null){
            globalParams = new GlobalParams(sdk);
        }
        return globalParams;
    }
    public Auth auth(){
        if (auth == null){
            auth = new Auth(sdk);
        }
        return auth;
    }
    public Governance governance(){
        if (governance == null){
            governance = new Governance(sdk);
        }
        return governance;
    }
    public SideChainGovernance sideChainGovernance(){
        if(sideChainGovernance == null){
            sideChainGovernance = new SideChainGovernance(sdk);
        }
        return sideChainGovernance;
    }
}
