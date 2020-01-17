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

import com.alibaba.fastjson.JSON;
import com.github.TesraSupernet.TstSdk;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.sdk.manager.ECIES;
import org.bouncycastle.crypto.digests.SHA256Digest;

/**
 *
 */
public class ECIESDemo {
    public static void main(String[] args) {

        try {
            TstSdk tstSdk = getTstSdk();

            com.github.TesraSupernet.account.Account account = new com.github.TesraSupernet.account.Account(Helper.hexToBytes("9a31d585431ce0aa0aab1f0a432142e98a92afccb7bcbcaff53f758df82acdb3"), tstSdk.defaultSignScheme);
            System.out.println("PrivateKey:"+Helper.toHexString(account.serializePrivateKey()));
            System.out.println("PublicKey:"+Helper.toHexString(account.serializePublicKey()));
//            System.out.println(Helper.toHexString(account.serializePrivateKey()));

            //setDigest
            ECIES.setDigest(new SHA256Digest());
            byte[] msg = new String("1234567890").getBytes();
            String[] ret = ECIES.Encrypt(Helper.toHexString(account.serializePublicKey()),msg);
            byte[] msg2 = ECIES.Decrypt(Helper.toHexString(account.serializePrivateKey()),ret);
//            byte[] msg3 = ECIES.Decrypt(account,ret);
            System.out.println("Msg:"+Helper.toHexString(msg));
            System.out.println("Encrypted:"+JSON.toJSONString(ret));
            System.out.println("Decrypt:"+Helper.toHexString(msg2));
//            System.out.println(Helper.toHexString(msg3));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static TstSdk getTstSdk() throws Exception {
        String ip = "http://127.0.0.1";
//        String ip = "http://dapp1.tesra.me;
//        String ip = "http://dapp2.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());

        wm.openWalletFile("Demo3.json");

        return wm;
    }
}
