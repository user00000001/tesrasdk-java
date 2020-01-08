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
import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.core.transaction.Transaction;
import com.github.TesraSupernet.account.Account;
import com.github.TesraSupernet.sdk.wallet.Identity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class Nep5Demo {
    public static String privatekey0 = "523c5fcf74823831756f0bcb3634234f10b3beb1c05595058534577752ad2d9f";
    public static String privatekey1 = "49855b16636e70f100cc5f4f42bc20a6535d7414fb8845e7310f8dd065a97221";
    public static String privatekey2 = "1094e90dd7c4fdfd849c14798d725ac351ae0d924b29a279a9ffa77d5737bd96";
    public static String privatekey3 = "bc254cf8d3910bc615ba6bf09d4553846533ce4403bc24f58660ae150a6d64cf";
    public static String privatekey4 = "06bda156eda61222693cc6f8488557550735c329bc7ca91bd2994c894cd3cbc8";
    public static String privatekey5 = "f07d5a2be17bde8632ec08083af8c760b41b5e8e0b5de3703683c3bdcfb91549";
    public static void main(String[] args) {
        try {
            TstSdk tstSdk = getTstSdk();
            com.github.TesraSupernet.account.Account acct1 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey1), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct2 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey2), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct3 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey3), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct4 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey4), tstSdk.defaultSignScheme);
            com.github.TesraSupernet.account.Account acct5 = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey5), tstSdk.defaultSignScheme);

            Account acct = new com.github.TesraSupernet.account.Account(Helper.hexToBytes(privatekey0), tstSdk.defaultSignScheme);
            System.out.println("recv:"+acct.getAddressU160().toBase58());
            System.out.println("acct1:"+acct1.getAddressU160().toBase58());
            if(false) {
                long gasLimit = tstSdk.neovm().nep5().sendInitPreExec(acct,acct,30000,0);
                System.out.println(gasLimit);
                String result = tstSdk.neovm().nep5().sendInit(acct,acct,30000,0);
                System.out.println(result);
                System.exit(0);
            }
            String multiAddr = Address.addressFromMultiPubKeys(2,acct.serializePublicKey(),acct2.serializePublicKey()).toBase58();
            System.out.println("multiAddr:"+multiAddr);
            if(false) {
                long gasLimit = tstSdk.neovm().nep5().sendTransferPreExec(acct, acct1.getAddressU160().toBase58(), 9000000000L);
                System.out.println(gasLimit);
                tstSdk.neovm().nep5().sendTransfer(acct, acct1.getAddressU160().toBase58(), 1000000000L, acct, gasLimit, 0);
                tstSdk.neovm().nep5().sendTransfer(acct, multiAddr, 1000000000L, acct, gasLimit, 0);
                System.exit(0);
            }

            if(true){ // sender is multi sign addr
                String balance = tstSdk.neovm().nep5().queryBalanceOf(multiAddr);
                System.out.println(new BigInteger(Helper.reverse(Helper.hexToBytes(balance))).longValue());

                Transaction tx = tstSdk.neovm().nep5().makeTransfer(multiAddr,acct1.getAddressU160().toBase58(),10000000L,acct,50000,0);
                tstSdk.addSign(tx,acct);
                tstSdk.addMultiSign(tx,2,new byte[][]{acct.serializePublicKey(),acct2.serializePublicKey()},acct);
                tstSdk.addMultiSign(tx,2,new byte[][]{acct.serializePublicKey(),acct2.serializePublicKey()},acct2);
                Object obj = tstSdk.getConnect().sendRawTransactionPreExec(tx.toHexString());
                System.out.println(obj);
             //   tstSdk.getConnect().sendRawTransaction(tx.toHexString());
                System.out.println(tx.hash().toString());
                System.exit(0);
            }

            String balance = tstSdk.neovm().nep5().queryBalanceOf(acct.getAddressU160().toBase58());
            System.out.println(new BigInteger(Helper.reverse(Helper.hexToBytes(balance))).longValue());
            balance = tstSdk.neovm().nep5().queryBalanceOf(multiAddr);
            System.out.println(new BigInteger(Helper.reverse(Helper.hexToBytes(balance))).longValue());
            System.exit(0);

            String totalSupply = tstSdk.neovm().nep5().queryTotalSupply();
            System.out.println(new BigInteger(Helper.reverse(Helper.hexToBytes(totalSupply))).longValue());
            System.exit(0);

            String decimals = tstSdk.neovm().nep5().queryDecimals();
            System.out.println(decimals);

            String name = tstSdk.neovm().nep5().queryName();
            System.out.println(new String(Helper.hexToBytes(name)));
            String symbol = tstSdk.neovm().nep5().querySymbol();
            System.out.println(new String(Helper.hexToBytes(symbol)));

            System.out.println(Address.decodeBase58(acct.getAddressU160().toBase58()).toHexString());
            System.out.println(acct1.getAddressU160().toHexString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TstSdk getTstSdk() throws Exception {
//        String ip = "http://52.184.29.110";
        String ip = "http://127.0.0.1";
//        String ip = "http://52.229.166.6";
//        String ip = "http://dapp2.tesra.me";
        String restUrl = ip + ":" + "25770";
        String rpcUrl = ip + ":" + "25768";
        String wsUrl = ip + ":" + "25771";

        TstSdk wm = TstSdk.getInstance();
        wm.setRpc(rpcUrl);
        wm.setRestful(restUrl);
        wm.setDefaultConnect(wm.getRestful());
        wm.neovm().nep5().setContractAddress(Helper.reverse("fc68268daa780992c3e91997325bbfe1f1992ccf"));
        wm.openWalletFile("nep5.json");


        return wm;
    }
}
