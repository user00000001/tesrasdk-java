package com.github.TesraSupernet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TstSdkTest {
    private TstSdk tstSdk;
//    public static String URL = "http://dapp1.tesra.me:25770";
//    public static String URL = "http://dapp2.tesra.me:25770";
    public static String URL = "http://127.0.0.1:25770";

    public static String PRIVATEKEY3 = "c19f16785b8f3543bbaf5e1dbb5d398dfa6c85aaad54fc9d71203ce83e505c07";//有钱的账号的私钥
    public static String PRIVATEKEY2 = "f1442d5e7f4e2061ff9a6884d6d05212e2aa0f6a6284f0a28ae82a29cdb3d656";//有钱的账号的私钥
    public static String PRIVATEKEY = "75de8489fcb2dcaf2ef3cd607feffde18789de7da129b5e97c81e001793cb7cf";

    public static String PASSWORD = "111111";//有钱账号的密码

    @Before
    public void setUp() throws Exception {
        tstSdk = TstSdk.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        TstSdk tstSdk = TstSdk.getInstance();
        assertNotNull(tstSdk);
        assertSame(tstSdk,this.tstSdk);
    }
}