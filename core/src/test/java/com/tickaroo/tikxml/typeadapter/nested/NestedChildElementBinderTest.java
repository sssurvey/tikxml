/*
 * Copyright (C) 2015 Hannes Dorfmann
 * Copyright (C) 2015 Tickaroo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tickaroo.tikxml.typeadapter.nested;

import com.tickaroo.tikxml.TestUtils;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.TypeConverter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import okio.BufferedSource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Hannes Dorfmann
 */
public class NestedChildElementBinderTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  private TypeConverter<Date> dateTypeConverter = new TypeConverter<Date>() {

    @Override
    public synchronized Date read(String value) throws Exception {
      return dateFormatter.parse(value);
    }

    @Override
    public synchronized String write(Date value) throws Exception {
      return dateFormatter.format(value);
    }
  };


  @Test
  public void readAll() throws IOException, ParseException {

    TikXml tikXml = new TikXml.Builder()
        .throwExceptionOnMissingMapping(false)
        .addTypeConverter(Date.class, dateTypeConverter)
        .addTypeAdapter(Company.class, new CompanyNestedTypeAdapter())
        .build();

    BufferedSource source = TestUtils.sourceForFile("company_nested_childbinder.xml");

    Company company = tikXml.read(source, Company.class);

    Assert.assertEquals(123, company.id);
    Assert.assertEquals("Foo Inc.", company.name);
    Assert.assertEquals(dateFormatter.parse("1999-12-31"), company.founded);
    Assert.assertEquals("Inc.", company.legalForm);
    Assert.assertEquals("This is the text content\n    ", company.description);
    Assert.assertTrue(company.legalFormPartOfTheName);
    Assert.assertTrue(company.shortInfo);
    Assert.assertEquals("Others Content", company.otherText);

  }

}
