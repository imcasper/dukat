package org.jetbrains.dukat.tsmodel.types

import org.jetbrains.dukat.tsmodel.ParameterOwnerDeclaration

data class IntersectionTypeDeclaration(
        val params: List<ParameterValueDeclaration>,

        override var nullable: Boolean = false,
        override var meta: ParameterValueDeclaration? = null
) : ParameterValueDeclaration, ParameterOwnerDeclaration