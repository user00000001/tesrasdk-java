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

package demo;

import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.common.UInt256;
import com.github.TesraSupernet.merkle.MerkleVerifier;
import com.github.TesraSupernet.network.rpc.*;
import com.github.TesraSupernet.sdk.exception.SDKException;
import com.github.TesraSupernet.sdk.wallet.Identity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class ClaimDemo {

    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();
            String privatekey0 = "c19f16785b8f3543bbaf5e1dbb5d398dfa6c85aaad54fc9d71203ce83e505c07";
            com.github.TesraSupernet.account.Account acct0 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0), tstSdk.defaultSignScheme);
            List<Identity> dids = tstSdk.getWalletMgr().getWallet().getIdentities();
            if (dids.size() < 2) {
                Identity identity = tstSdk.getWalletMgr().createIdentity("passwordtest");
                tstSdk.nativevm().tstId().sendRegister(identity,"passwordtest",acct0,0,0);
                identity = tstSdk.getWalletMgr().createIdentity("passwordtest");
                tstSdk.nativevm().tstId().sendRegister(identity,"passwordtest",acct0,0,0);
                dids = tstSdk.getWalletMgr().getWallet().getIdentities();
                Thread.sleep(6000);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("Issuer", dids.get(0).tstid);
            map.put("Subject", dids.get(1).tstid);


            String claim = tstSdk.nativevm().tstId().createTstIdClaim(dids.get(0).tstid,"passwordtest",new byte[]{}, "claim:context", map, map,map,0);
            System.out.println(claim);
            boolean b = tstSdk.nativevm().tstId().verifyTstIdClaim(claim);
            System.out.println(b);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {

        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.46;
//        String ip = "http://52.229.166.6";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("ClaimDemo.json");

        return wm;
    }
}
