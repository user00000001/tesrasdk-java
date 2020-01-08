//package com.github.TesraSupernet;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.TesraSupernet.account.Account;
//import com.github.TesraSupernet.common.Address;
//import com.github.TesraSupernet.network.exception.RestfulException;
//import com.github.TesraSupernet.sdk.exception.SDKException;
//
//import com.github.TesraSupernet.sdk.manager.ConnectMgr;
//import com.github.TesraSupernet.smartcontract.nativevm.TstAssetTx;
//import com.github.TesraSupernet.smartcontract.neovm.TstIdTx;
//import com.github.TesraSupernet.sdk.manager.WalletMgr;
//import com.github.TesraSupernet.sdk.wallet.Identity;
//import com.github.TesraSupernet.sdk.wallet.Wallet;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class SmokeTest {
//    private TstSdk tstSdk;
//    private WalletMgr walletMgr;
//    private Wallet wallet;
//    private TstIdTx tstIdTx;
//    private ConnectMgr connectMgr;
//    private TstAssetTx tstAssetTx;
//
//    @Before
//    public void setUp() throws Exception {
//        tstSdk = TstSdk.getInstance();
//        tstSdk.setRestful("http://polaris1.ont.io:20334");
//        tstSdk.openWalletFile("wallet.json");
//        tstSdk.neovm().tstId().setCodeAddress("80b0cc71bda8653599c5666cae084bff587e2de1");
//        walletMgr = tstSdk.getWalletMgr();
//        wallet = walletMgr.getWallet();
//        tstIdTx = tstSdk.neovm().tstId();
//        connectMgr = tstSdk.getConnectMgr();
//        tstAssetTx = tstSdk.nativevm().tst();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void sendUpdateAttribute() throws Exception {
//        Identity identity = tstIdTx.sendRegister("123456","payer",0);
//        Account account = walletMgr.getAccount(identity.tstid,"123456");
//        String prikey = account.exportCtrEncryptedPrikey("123456", 16384);
//        Thread.sleep(6000);
//        String string = tstIdTx.sendGetDDO(identity.tstid);
//        assertTrue(string.contains(identity.tstid));
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Context", "claimlalala");
//        jsonObject.put("Issuer", "issuerlalala");
//        String txnId = tstIdTx.sendUpdateAttribute(identity.tstid,"123456",prikey.getBytes(),"Json".getBytes(), jsonObject.toJSONString().getBytes(),0);
//        assertNotNull(txnId);
//        assertNotEquals(txnId,"");
//        Thread.sleep(6000);
//        string = tstIdTx.sendGetDDO(identity.tstid);
//        assertTrue(string.contains("claimlalala"));
//        assertTrue(string.contains("issuerlalala"));
//    }
//
//    @Test
//    public void getBalance() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor1
////        TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC---OGaD13Sn/q9gIZ8fmOtclMi4yy34qq963wzpidYDX5k= poor2
//
//
//        JSONObject balanceObj = (JSONObject) connectMgr.getBalance("TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW");
//        assertNotNull(balanceObj);
//        int tstBalance = balanceObj.getIntValue("tst");
//        assertTrue(tstBalance >= 0);
//        String richHexAddr = Address.decodeBase58("TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW").toHexString();
//
//    }
//
//    @Test
//    public void sendTransfer() throws Exception {
//        final int amount = 1;
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        final String poorAddr = "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String poorKey = "Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE=";
//        JSONObject richBalanceObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBalanceObj = (JSONObject) connectMgr.getBalance(poorAddr);
//        int richBalance = richBalanceObj.getIntValue("tst");
//        int poorBalance = poorBalanceObj.getIntValue("tst");
//        assertTrue(richBalance > 0);
//        assertTrue(poorBalance >= 0);
//
//        com.github.TesraSupernet.sdk.wallet.Account accountRich = walletMgr.importAccount(richKey,"123456",richAddr);
//        com.github.TesraSupernet.sdk.wallet.Account accountPoor = walletMgr.importAccount(poorKey,"123456",poorAddr);
//
//        String txnId = tstAssetTx.sendTransfer("tst",richAddr,"123456",poorAddr,amount,0);
//        assertNotNull(txnId);
//        assertNotEquals(txnId,"");
//
//        Thread.sleep(6000);
//
//        JSONObject richBalanceObjAfter = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBalanceObjAfter = (JSONObject) connectMgr.getBalance(poorAddr);
//        int richBalanceAfter = richBalanceObjAfter.getIntValue("tst");
//        int poorBalanceAfter = poorBalanceObjAfter.getIntValue("tst");
//
//        assertTrue(richBalanceAfter == richBalance -amount);
//        assertTrue(poorBalanceAfter == poorBalance +amount);
//
//        String txnIdback = tstAssetTx.sendTransfer("tst",poorAddr,"123456",richAddr,amount,0);
//        assertNotNull(txnIdback);
//        assertNotEquals(txnIdback,"");
//
//        Thread.sleep(6000);
//        JSONObject richBalanceObjBack = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBalanceObjBack = (JSONObject) connectMgr.getBalance(poorAddr);
//        int richBalanceBack = richBalanceObjBack.getIntValue("tst");
//        int poorBalanceBack = poorBalanceObjBack.getIntValue("tst");
//        assertEquals(richBalanceBack,richBalance);
//        assertEquals(poorBalanceBack,poorBalance);
//
//    }
//
//    @Test
//    public void sendTransferFromManyAndBack() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor1
////        TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC---OGaD13Sn/q9gIZ8fmOtclMi4yy34qq963wzpidYDX5k= poor2
//        final int amount1 = 2;
//        final int amount2 = 1;
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr1 = "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String poorAddr2 = "TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        final String poorKey1 = "Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE=";
//        final String poorKey2 = "OGaD13Sn/q9gIZ8fmOtclMi4yy34qq963wzpidYDX5k=";
//        JSONObject richOrigObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorOrigObj1 = (JSONObject) connectMgr.getBalance(poorAddr1);
//        JSONObject poorOrigObj2 = (JSONObject) connectMgr.getBalance(poorAddr2);
//        int richOrig = richOrigObj.getIntValue("tst");
//        int poorOrig1 = poorOrigObj1.getIntValue("tst");
//        int poorOrig2 = poorOrigObj2.getIntValue("tst");
//        assertTrue(richOrig > 0);
//        assertTrue(poorOrig1 > 0);
//        assertTrue(poorOrig2 >= 0);
//
//        com.github.TesraSupernet.sdk.wallet.Account accountRich = walletMgr.importAccount(richKey,"123456",richAddr);
//        com.github.TesraSupernet.sdk.wallet.Account accountPoor1 = walletMgr.importAccount(poorKey1,"123456",poorAddr1);
//        com.github.TesraSupernet.sdk.wallet.Account accountPoor2 = walletMgr.importAccount(poorKey2,"123456",poorAddr2);
//
//        String txnId =tstAssetTx.sendTransferFromMany("tst",new String[]{richAddr,poorAddr1},new String[]{"123456","123456"},poorAddr2,new long[]{amount1,amount2},0);
//        assertNotNull(txnId);
//        assertNotEquals(txnId,"");
//
//        Thread.sleep(6000);
//
//        JSONObject richAfterObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorAfterObj1 = (JSONObject) connectMgr.getBalance(poorAddr1);
//        JSONObject poorAfterObj2 = (JSONObject) connectMgr.getBalance(poorAddr2);
//        int richAfter = richAfterObj.getIntValue("tst");
//        int poorAfter1 = poorAfterObj1.getIntValue("tst");
//        int poorAfter2 = poorAfterObj2.getIntValue("tst");
//        assertTrue(richAfter == richOrig - amount1);
//        assertTrue(poorAfter1 == poorOrig1 - amount2);
//        assertTrue(poorAfter2 == poorOrig2 + amount1 + amount2);
//
//        String txnIdback = tstAssetTx.sendTransferToMany("tst",poorAddr2,"123456",new String[]{richAddr,poorAddr1},new long[]{amount1,amount2},0);
//        assertNotNull(txnIdback);
//        assertNotEquals(txnIdback,"");
//
//        Thread.sleep(6000);
//
//        JSONObject richBackObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBackObj1 = (JSONObject) connectMgr.getBalance(poorAddr1);
//        JSONObject poorBackObj2 = (JSONObject) connectMgr.getBalance(poorAddr2);
//        int richBack = richBackObj.getIntValue("tst");
//        int poorBack1 = poorBackObj1.getIntValue("tst");
//        int poorBack2 = poorBackObj2.getIntValue("tst");
//        assertTrue(richBack == richOrig);
//        assertTrue(poorBack1 == poorOrig1);
//        assertTrue(poorBack2 == poorOrig2);
//
//
//    }
//
//    @Test
//    public void sendTsgTransferFromToSelf() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
//        final int amount = 1;
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        JSONObject richBalanceObj = (JSONObject) connectMgr.getBalance(richAddr);
//        int richTsgApprove = richBalanceObj.getIntValue("tsg_appove");
//        int richTsg = richBalanceObj.getIntValue("tsg");
//        assertTrue(richTsgApprove > 0);
//        assertTrue(richTsg >= 0);
//
//        com.github.TesraSupernet.sdk.wallet.Account accountRich = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        String txnId = tstAssetTx.sendTsgTransferFrom(richAddr,"123456",richAddr,amount,0);
//        assertNotNull(txnId);
//        assertNotEquals(txnId,"");
//
//        Thread.sleep(6000);
//
//        JSONObject richBalanceAfterObj = (JSONObject) connectMgr.getBalance(richAddr);
//        int richTsgApproveAfter = richBalanceAfterObj.getIntValue("tsg_appove");
//        int richTsgAfter = richBalanceAfterObj.getIntValue("tsg");
//        assertTrue(richTsgApproveAfter == richTsgApprove - amount);
//        assertTrue(richTsgAfter == richTsg + amount);
//
//    }
//
//    @Test
//    public void sendTsgTransferFromToOther() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor1
//        final int amount = 1;
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        final String poorAddr = "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//
//        JSONObject richBalanceObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBalanceObj = (JSONObject) connectMgr.getBalance(poorAddr);
//        long richTsgApprove = richBalanceObj.getLongValue("tsg_appove");
//        int poorTsg = poorBalanceObj.getIntValue("tsg");
//        assertTrue(richTsgApprove > 0);
//        assertTrue(poorTsg >= 0);
//
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        String txnId = tstAssetTx.sendTsgTransferFrom(richAddr,"123456",poorAddr,amount,0);
//        assertNotNull(txnId);
//        assertNotEquals(txnId,"");
//
//        Thread.sleep(6000);
//
//        JSONObject richBalanceAfterObj = (JSONObject) connectMgr.getBalance(richAddr);
//        JSONObject poorBalanceAfterObj = (JSONObject) connectMgr.getBalance(poorAddr);
//        long richTsgApproveAfter = richBalanceAfterObj.getLongValue("tsg_appove");
//        int poorTsgAfter = poorBalanceAfterObj.getIntValue("tsg");
//        assertTrue(richTsgApproveAfter == richTsgApprove - amount);
//        assertTrue(poorTsgAfter == poorTsg + amount);
//
//    }
//
//    @Test
//    public void sendTransfer58012AssertNameError() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor1
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransfer("aaa",richAddr,"123456",poorAddr,1,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("58012"));
//        }
//    }
//
//    @Test
//    public void sendTransfer58016TstassetError() throws Exception {
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"123456",poorAddr,0,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("58016"));
//        }
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"123456",poorAddr,1,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("58016"));
//        }
//    }
//
//    @Test
//    public void sendTransferTooAmount() throws Exception {
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"123456",poorAddr,1234567890123456789L,0);
//        } catch (SDKException e) {
//            //todo
//        }
//    }
//
//    @Test
//    public void sendTransfer58004ParamError() throws Exception {
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransfer("tst","","123456",poorAddr,1,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("58004"));
//        }
//
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"123456","",1,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("58004"));
//        }
//    }
//
//    @Test
//    public void sendTransferWhenPasswordError() throws Exception {
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        com.github.TesraSupernet.sdk.wallet.Account account = walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"",poorAddr,1,0);
//        } catch (SDKException e) {
//            assertTrue(e.getMessage().contains("59000"));
//        }
//    }
//
//    @Test
//    public void sendTransfer58023InvalidUrl() throws Exception {
//
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poorAddr= "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String richKey = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        walletMgr.importAccount(richKey,"123456",richAddr);
//        tstSdk.setRestful("");
//
//        try {
//            tstAssetTx.sendTransfer("tst",richAddr,"123456",poorAddr,1,0);
//        } catch (RestfulException e) {
//            assertTrue(e.getMessage().contains("58023"));
//        }
//
//    }
//
//    @Test
//    public void sendTransferFromToManyError() throws Exception {
////        TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW---1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM= rich
////        TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC---Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE= poor1
////        TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC---OGaD13Sn/q9gIZ8fmOtclMi4yy34qq963wzpidYDX5k= poor2
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poor1Addr = "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String poor2Addr = "TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC";
//        final String richKey  = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        final String poor1Key = "Vz0CevSaI9/VNLx03XNEQ4Lrnnkkjo5aM5hdCuicsOE=";
//        walletMgr.importAccount(richKey,"123456",richAddr);
//        walletMgr.importAccount(poor1Key,"123456",poor1Addr);
//
//        try {
//            tstAssetTx.sendTransferFromMany("aaa",new String[]{richAddr,poor1Addr},new String[]{"123456","123456"},poor2Addr,new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58012"));
//        }
//
//        try {
//            tstAssetTx.sendTransferFromMany("aaa",new String[]{richAddr,poor1Addr},new String[]{"123456","123456"},poor2Addr,new long[]{-1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58016"));
//        }
//
//        try {
//            tstAssetTx.sendTransferFromMany("tst",new String[]{"",poor1Addr},new String[]{"123456","123456"},poor2Addr,new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58004"));
//        }
//
//        try {
//            tstAssetTx.sendTransferFromMany("tst",new String[]{richAddr,poor1Addr},new String[]{"","123456"},poor2Addr,new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("59000"));
//        }
//
//        try {
//            tstSdk.setRestful("");
//            tstAssetTx.sendTransferFromMany("tst",new String[]{richAddr,poor1Addr},new String[]{"123456","123456"},poor2Addr,new long[]{1,1},0);
//        }catch (RestfulException e){
//            assertTrue(e.getMessage().contains("58023"));
//
//        }
//
//    }
//
//    @Test
//    public void sendTransferToManyError() throws Exception {
//        final String richAddr = "TA6qWdLo14aEve5azrYWWvMoGPrpczFfeW";
//        final String poor1Addr = "TA4pSdTKm4hHtQJ8FbrCk9LZn7Uo96wrPC";
//        final String poor2Addr = "TA5F9QefsyKvn5cH37VnP5snSru5ZCYHHC";
//        final String richKey  = "1/gEPy/Uz3Eyl/sjoZ8JDymGX6hU/gi1ufUIg6vDURM=";
//        walletMgr.importAccount(richKey,"123456",richAddr);
//
//        try {
//            tstAssetTx.sendTransferToMany("aaa",richAddr,"123456",new String[]{poor1Addr,poor2Addr},new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58012"));
//        }
//
//        try {
//            tstAssetTx.sendTransferToMany("tst",richAddr,"123456",new String[]{poor1Addr,poor2Addr},new long[]{-1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58016"));
//        }
//
//        try {
//            tstAssetTx.sendTransferToMany("tst","","123456",new String[]{poor1Addr,poor2Addr},new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58004"));
//        }
//
//        try {
//            tstAssetTx.sendTransferToMany("tst",richAddr,"123456",new String[]{"",poor2Addr},new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("58004"));
//        }
//
//        try {
//            tstAssetTx.sendTransferToMany("tst",richAddr,"",new String[]{poor1Addr,poor2Addr},new long[]{1,1},0);
//        }catch (SDKException e){
//            assertTrue(e.getMessage().contains("59000"));
//        }
//
//        try {
//            tstSdk.setRestful("");
//            tstAssetTx.sendTransferToMany("tst",richAddr,"123456",new String[]{poor1Addr,poor2Addr},new long[]{1,1},0);
//        }catch (RestfulException e){
//            assertTrue(e.getMessage().contains("58023"));
//        }
//
//
//
//
//    }
//}