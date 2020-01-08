/*
 * Copyright (C) 2019-2020-2019 The TesraSupernet Authors
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

package com.github.TesraSupernet.crypto.bip32;

import com.github.TesraSupernet.sdk.exception.SDKException;

public interface Deserializer<T> {

    /**
     * Deserializes the data into a {@link T}.
     *
     * @param extendedBase58Key Base58 CharSequence containing the serialized extended key.
     * @return The {@link T}
     */
    T deserialize(CharSequence extendedBase58Key) throws SDKException;

    /**
     * Deserializes the data into a {@link T}.
     *
     * @param extendedKeyData Byte array containing the serialized extended key.
     * @return The {@link T}
     */
    T deserialize(byte[] extendedKeyData) throws SDKException;
}