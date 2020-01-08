/*
 * Copyright (C) 2018 The TesraSupernet Authors
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

package com.github.TesraSupernet.core.asset;

import com.github.TesraSupernet.common.Address;
import com.github.TesraSupernet.common.Helper;
import com.github.TesraSupernet.crypto.Digest;
import com.github.TesraSupernet.io.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class State implements Serializable {
    public Address from;
    public Address to;
    public long value;
    public State(){}
    public State(Address from, Address to, long amount){
        this.from = from;
        this.to = to;
        this.value = amount;
    }
    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        from = new Address(reader.readVarBytes());
        to = new Address(reader.readVarBytes());
        value = Helper.BigIntFromNeoBytes(reader.readVarBytes()).longValue();
    }

    @Override
    public void serialize(BinaryWriter writer) throws IOException {
        writer.writeVarBytes(from.toArray());
        writer.writeVarBytes(to.toArray());
        writer.writeVarBytes(Helper.BigIntToNeoBytes(BigInteger.valueOf(value)));
    }

    public static State deserializeFrom(byte[] value) throws IOException {
        try {
            int offset = 0;
            ByteArrayInputStream ms = new ByteArrayInputStream(value, offset, value.length - offset);
            BinaryReader reader = new BinaryReader(ms);
            State state = new State();
            state.deserialize(reader);
            return state;
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    public Object json() {
        Map json = new HashMap<>();
        json.put("from", from.toHexString());
        json.put("to", to.toHexString());
        json.put("value", value);
        return json;
    }

}
