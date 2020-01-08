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

package com.github.TesraSupernet.common;

import com.github.TesraSupernet.core.program.Program;
import com.github.TesraSupernet.core.scripts.ScriptBuilder;
import com.github.TesraSupernet.core.scripts.ScriptOp;
import com.github.TesraSupernet.crypto.Base58;
import com.github.TesraSupernet.crypto.Digest;
import com.github.TesraSupernet.crypto.ECC;
import com.github.TesraSupernet.crypto.KeyType;
import com.github.TesraSupernet.io.BinaryWriter;
import com.github.TesraSupernet.sdk.exception.SDKException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import org.bouncycastle.math.ec.ECPoint;

import static com.github.TesraSupernet.crypto.KeyType.ECDSA;
import static com.github.TesraSupernet.crypto.KeyType.SM2;

/**
 * Custom type which inherits base class defines 20-bit data,
 * it mostly used to defined contract address
 */
public class Address extends UIntBase implements Comparable<Address> {
    public static final Address ZERO = new Address();
    public static final byte COIN_VERSION = 0x17;

    public Address() {
        this(null);
    }

    public Address(byte[] value) {
        super(20, value);
    }


    public static Address parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (value.startsWith("0x")) {
            value = value.substring(2);
        }
        if (value.length() != 40) {
            throw new IllegalArgumentException();
        }
        byte[] v = Helper.hexToBytes(value);
        return new Address(v);
//        return new UInt160(Helper.reverse(v));
    }

    public static boolean tryParse(String s, Address result) {
        try {
            Address v = parse(s);
            result.data_bytes = v.data_bytes;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Address AddressFromVmCode(String codeHexStr) {
        Address code = Address.toScriptHash(Helper.hexToBytes(codeHexStr));
        return code;
    }

    public static Address addressFromPubKey(String publicKey) {
        return addressFromPubKey(Helper.hexToBytes(publicKey));
    }

    public static Address addressFromPubKey(byte[] publicKey) {
        ScriptBuilder sb = new ScriptBuilder();
        sb.emitPushByteArray(publicKey);
        sb.add(ScriptOp.OP_CHECKSIG);
        return Address.toScriptHash(sb.toArray());
    }

    public static Address addressFromMultiPubKeys(int m, byte[]... publicKeys) throws Exception {
        return Address.toScriptHash(Program.ProgramFromMultiPubKey(m,publicKeys));
    }

    public static Address decodeBase58(String address) throws SDKException {
        byte[] data = new byte[]{};
        try{
            data = Base58.decode(address);
        }catch (Exception e){
            throw new SDKException(ErrorCode.ParamError);
        }
        if (data.length != 25) {
            throw new SDKException(ErrorCode.ParamError);
        }
        if (data[0] != COIN_VERSION) {
            throw new SDKException(ErrorCode.ParamError);
        }
        byte[] checksum = Digest.sha256(Digest.sha256(data, 0, 21));
        for (int i = 0; i < 4; i++) {
            if (data[data.length - 4 + i] != checksum[i]) {
                throw new SDKException(ErrorCode.ParamError);
            }
        }
        byte[] buffer = new byte[20];
        System.arraycopy(data, 1, buffer, 0, 20);
        return new Address(buffer);
    }

    public static Address toScriptHash(byte[] script) {
        return new Address(Digest.hash160(script));
    }

    @Override
    public int compareTo(Address other) {
        byte[] x = this.data_bytes;
        byte[] y = other.data_bytes;
        for (int i = x.length - 1; i >= 0; i--) {
            int r = Byte.toUnsignedInt(x[i]) - Byte.toUnsignedInt(y[i]);
            if (r != 0) {
                return r;
            }
        }
        return 0;
    }

    public String toBase58() {
        byte[] data = new byte[25];
        data[0] = COIN_VERSION;
        System.arraycopy(toArray(), 0, data, 1, 20);
        byte[] checksum = Digest.sha256(Digest.sha256(data, 0, 21));
        System.arraycopy(checksum, 0, data, 21, 4);
        return Base58.encode(data);
    }

}