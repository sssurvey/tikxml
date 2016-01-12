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

package com.tickaroo.tikxml.processor.xml

import com.tickaroo.tikxml.processor.field.AttributeField
import com.tickaroo.tikxml.processor.utils.getSurroundingClassQualifiedName
import java.util.*
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * This element represents a "placeholder" xml element. That means that we might have written a [com.tickaroo.tikxml.annotation.Path]
 * annotation that is pointing to a virtual element, then this Placeholder element will be instantiated and eventually be merged
 * with a real element afterwards.
 * @author Hannes Dorfmann
 */
class PlaceholderXmlElement(override val name: String, override val element: VariableElement?, val typeElement: TypeElement?) : XmlChildElement {


    override val attributes = HashMap<String, AttributeField>()
    override val childElements = HashMap<String, XmlChildElement>()

    override fun isXmlElementMergeable() = true

    override fun toString(): String =
            if (element != null) "field '${element.simpleName}' in class ${element.getSurroundingClassQualifiedName()}"
            else typeElement?.qualifiedName.toString()

}