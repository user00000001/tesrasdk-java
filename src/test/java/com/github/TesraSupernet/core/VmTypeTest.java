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

package com.github.TesraSupernet.core;

import org.junit.Assert;
import org.junit.Test;

public class VmTypeTest {

    @Test
    public void valueOf() throws IllegalArgumentException {
        Assert.assertEquals(VmType.NEOVM, VmType.valueOf((byte) 0x01));
        Assert.assertEquals(VmType.WASMVM, VmType.valueOf((byte) 0x03));
    }

    @Test
    public void value() {
        Assert.assertEquals(1, VmType.NEOVM.value());
        Assert.assertEquals(3, VmType.WASMVM.value());
    }

}
